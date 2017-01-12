/*
 *
 */
package org.jspare.spareco.common;

import static org.jspare.core.container.Environment.registryResource;

import org.jspare.spareco.common.circuitbreaker.CircuitBreakerHolder;
import org.jspare.spareco.common.discovery.ConfigurationProvider;
import org.jspare.spareco.common.discovery.RecordMetadata;
import org.jspare.spareco.common.discovery.ServiceDiscoveryHolder;
import org.jspare.vertx.builder.ProxyServiceBuilder;
import org.jspare.vertx.utils.VerticleInitializer;

import io.vertx.circuitbreaker.CircuitBreaker;
import io.vertx.circuitbreaker.CircuitBreakerOptions;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.DeploymentOptions;
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

	protected void addProxyService(Class<?> proxyServiceClass) {
		ProxyServiceBuilder.create(vertx).addProxyService(proxyServiceClass).build();
	}

	protected CircuitBreaker createCircuitBreaker() {

		return CircuitBreaker.create("circuit-breaker", vertx, new CircuitBreakerOptions(options.getCircuitBreakerOptions()));
	}

	protected Record createDBCDataSource(String name, JsonObject location) {
		return JDBCDataSource.createRecord(name, location, new JsonObject());
	}

	protected Record createEventBusServiceEndpoint(String name, String address, Class<?> serviceClass) {
		return EventBusService.createRecord(name, address, serviceClass);
	}

	protected Record createHttpEndpoint(Integer port, boolean ssl) {

		String address = getAddress();
		
		RecordMetadata metadata = new RecordMetadata()
				.setName(getAPIName())
				.setHealthPathCheck(options.getHealthPathCheck())
				.setHealthCheck(options.isHealthCheck());

		return HttpEndpoint.createRecord(getAPIName(), ssl, address, port, ROOT, metadata.toJson());
	}

	protected Record createMessageSourceEndpoint(String name, String address) {

		return MessageSource.createRecord(name, address);
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

	protected void registryResources() {
		
		// Create resources
		discovery = createServiceDiscovery();
		circuitBreaker = createCircuitBreaker();

		// Registry resource on container
		registryResource(new MicroserviceOptionsHolder(options));
		registryResource(ServiceDiscoveryHolder.create(discovery));
		registryResource(CircuitBreakerHolder.create(circuitBreaker));
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

}
