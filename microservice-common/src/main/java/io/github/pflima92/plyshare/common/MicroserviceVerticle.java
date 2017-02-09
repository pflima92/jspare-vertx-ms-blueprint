/*
 *
 */
package io.github.pflima92.plyshare.common;

import static org.jspare.core.container.Environment.registryResource;

import org.jspare.vertx.bootstrap.VerticleInitializer;
import org.jspare.vertx.builder.ProxyServiceBuilder;

import io.github.pflima92.plyshare.common.circuitbreaker.CircuitBreakerHolder;
import io.github.pflima92.plyshare.common.configuration.ConfigurationProvider;
import io.github.pflima92.plyshare.common.discovery.RecordMetadata;
import io.github.pflima92.plyshare.common.discovery.ServiceDiscoveryHolder;
import io.vertx.circuitbreaker.CircuitBreaker;
import io.vertx.circuitbreaker.CircuitBreakerOptions;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.Verticle;
import io.vertx.core.json.JsonObject;
import io.vertx.servicediscovery.Record;
import io.vertx.servicediscovery.ServiceDiscovery;
import io.vertx.servicediscovery.ServiceDiscoveryOptions;
import io.vertx.servicediscovery.types.EventBusService;
import io.vertx.servicediscovery.types.HttpEndpoint;
import io.vertx.servicediscovery.types.JDBCDataSource;
import io.vertx.servicediscovery.types.MessageSource;
import io.vertx.serviceproxy.ProxyHelper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class MicroserviceVerticle extends AbstractVerticle {

	private static final String ROOT = "/";
	protected MicroserviceOptions options;
	protected ServiceDiscovery discovery;
	protected CircuitBreaker circuitBreaker;

	protected void addProxyService(Class<?> proxyServiceClass) {
		ProxyServiceBuilder.create(vertx).addProxyService(proxyServiceClass).build();
	}

	protected CircuitBreaker createCircuitBreaker() {

		return CircuitBreaker.create("circuit-breaker", vertx, new CircuitBreakerOptions(options.getCircuitBreakerOptions()));
	}

	protected ServiceDiscovery createServiceDiscovery() {

		return ServiceDiscovery.create(vertx, new ServiceDiscoveryOptions(options.getServiceDiscoveryOptions()));
	}

	protected DeploymentOptions defaultDeploymentOptions() {

		return new DeploymentOptions().setConfig(config());
	}

	protected void deployVerticle(Class<? extends Verticle> verticleClass) {

		deployVerticle(verticleClass, defaultDeploymentOptions());
	}

	protected void deployVerticle(Class<? extends Verticle> verticleClass, DeploymentOptions deploymentOptions) {
		vertx.deployVerticle(VerticleInitializer.initialize(verticleClass), deploymentOptions);
	}

	protected String getAddress() {

		return options.getAddress();
	}
	
	protected String getAPIName() {

		return options.getName();
	}

	protected Integer getApiPort() {
		return options.getPort();
	}

	protected String getHealthCheckPath() {
		return options.getHealthPathCheck();
	}

	protected String getName() {

		return options.getName();
	}

	protected abstract void initialize();

	protected Future<Record> publish(Record record) {

		Future<Record> future = Future.future();
		// publish the service
		discovery.publish(record, ar -> {
			if (ar.succeeded()) {
				future.complete(ar.result());
			} else {
				future.fail(ar.cause());
			}
		});

		return future;
	}

	protected Future<Record> publishEventBusServiceEndpoint(String name, String address, Class<?> serviceClass) {
		Record record = EventBusService.createRecord(name, address, serviceClass);
		return publish(record);
	}

	protected Future<Record> publishHttpEndpoint(Integer port, boolean ssl) {

		String address = getAddress();

		RecordMetadata metadata = new RecordMetadata().setName(getAPIName()).setHealthPathCheck(options.getHealthPathCheck())
				.setHealthCheck(options.isHealthCheck());

		Record record = HttpEndpoint.createRecord(getAPIName(), ssl, address, port, ROOT, metadata.toJson());
		return publish(record);
	}

	protected Future<Record> publishJDBCDataSource(String name, JsonObject location) {

		Record record = JDBCDataSource.createRecord(name, location, new JsonObject());
		return publish(record); 
	}

	protected Future<Record> publishMessageSourceEndpoint(String name, String address) {

		Record record = MessageSource.createRecord(name, address);
		return publish(record);
	}

	protected void registryResources() {

		// Registry resource on container
		registryResource(new MicroserviceOptionsHolder(options));

		// Create resources
		if(discovery == null){
		
			discovery = createServiceDiscovery();
			registryResource(ServiceDiscoveryHolder.create(discovery));
		}
		
		if(circuitBreaker == null){
			
			circuitBreaker = createCircuitBreaker();
			registryResource(CircuitBreakerHolder.create(circuitBreaker));
		}
	}

	protected void setConfiguration(AsyncResult<JsonObject> resHandler) {

		if (resHandler.succeeded()) {
			log.debug("Received configurations {}", resHandler.result());
			context.config().mergeIn(resHandler.result());
		}

		log.debug("Using follow configurations {}", context.config().encodePrettily());
		initialize();
	}

	protected void setOptions() {

		// Hold MicroserviceOptions
		options = new MicroserviceOptions(config());

		if (!config().isEmpty()) {

			log.debug("Using follow custom configurations: {}", options);
		} else {

			log.debug("Using default configurations: {}", options);
		}
	}

	protected void setProvidedConfiguration() {

		String name = getName();
		ProxyHelper.createProxy(ConfigurationProvider.class, vertx, ConfigurationProvider.SERVICE_NAME).getConfiguration(name,
				this::setConfiguration);
	}

	@Override
	public void start() throws Exception {

		log.debug(
				"We are using sl4j with logger of this application, for change any configuration use file: simplelogger.properties instead of http://www.slf4j.org/api/org/slf4j/impl/SimpleLogger.html");

		// Set and hold options for use on this instance
		setOptions();

		// Registry resources on Container
		registryResources();

		// Set provided configuration from ConfigurationProvider
		setProvidedConfiguration();
	}

}
