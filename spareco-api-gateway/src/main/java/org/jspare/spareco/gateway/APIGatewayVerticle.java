/*
 *
 */
package org.jspare.spareco.gateway;

import static org.jspare.core.container.Environment.my;
import static org.jspare.core.container.Environment.registryComponent;
import static org.jspare.core.container.Environment.registryResource;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.jspare.spareco.common.MicroserviceVerticle;
import org.jspare.spareco.common.discovery.ConfigurationProvider;
import org.jspare.spareco.common.discovery.Heartbeat;
import org.jspare.spareco.common.discovery.RecordMetadata;
import org.jspare.spareco.gateway.common.GatewayOptions;
import org.jspare.spareco.gateway.common.GatewayOptionsHolder;
import org.jspare.spareco.gateway.library.LibrarySupport;
import org.jspare.spareco.gateway.manager.GatewayManager;
import org.jspare.spareco.gateway.model.Gateway;
import org.jspare.spareco.gateway.persistance.GatewayPersistance;
import org.jspare.spareco.gateway.utils.Stopwatch;
import org.jspare.spareco.gateway.web.ProxyAPIVerticle;
import org.jspare.spareco.gateway.web.ServicesAPIVerticle;
import org.jspare.vertx.concurrent.FutureSupplier;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.http.HttpClient;
import io.vertx.servicediscovery.Record;
import io.vertx.servicediscovery.Status;
import io.vertx.servicediscovery.types.HttpEndpoint;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class APIGatewayVerticle extends MicroserviceVerticle {

	protected GatewayOptions options;

	@Override
	public void start() throws Exception {

		super.start();
	}

	@Override
	protected void initialize() {

		my(GatewayManager.class).setup().setHandler(this::onPrepare);
	}

	protected void onPrepare(AsyncResult<Gateway> resultHandler) {

		if (resultHandler.succeeded()) {

			log.debug("API Gateway Core initialized with success");
			log.debug(ReflectionToStringBuilder.toString(resultHandler.result(), ToStringStyle.MULTI_LINE_STYLE));

			// Registry API Gateway verticles
			deployVerticle(ProxyAPIVerticle.class);
			deployVerticle(ServicesAPIVerticle.class);

			// Registry Proxy Services
			addProxyService(ConfigurationProvider.class);

			startHealthCheck();

		} else {

			log.error("Fail to load API Gateway", resultHandler.cause());
			System.exit(1);
		}
	}

	@Override
	protected void registryResources() {

		super.registryResources();

		registryResource(new GatewayOptionsHolder(options));
	}

	@Override
	protected void setOptions() {

		super.setOptions();

		options = new GatewayOptions(config());
	}

	@Override
	@SneakyThrows({ ClassNotFoundException.class })
	protected void setProvidedConfiguration() {

		my(LibrarySupport.class).load();

		String persistanceClass = options.getGatewayDatabaseOptions().getDatabaseClass();
		log.debug("Initialize GatewayPersistance with: ", persistanceClass);

		registryComponent(Class.forName(persistanceClass));

		Stopwatch stopwatch = Stopwatch.create().start("Initialized Database");
		my(GatewayPersistance.class);
		stopwatch.stop("Database initialized").print();

		initialize();
	}

	protected void startHealthCheck() {

		long period = options.getPeriodicHealthCheck();
		vertx.setPeriodic(period, t -> {

			circuitBreaker.execute(future -> {
				// behind the circuit breaker
				sendHeartBeatRequest().setHandler(future.completer());
			});
		});
	}

	protected Future<Object> sendHeartBeatRequest() {
		
		return getAllEndpoints().compose(records -> {
			
			List<Future<Heartbeat>> statusFutureList = records.stream()
					.filter(record -> RecordMetadata.of(record.getMetadata()).isHealthCheck())
					.map(record -> {
						
						Future<Heartbeat> future = Future.future();
						
						RecordMetadata metadata = RecordMetadata.of(record.getMetadata());
						HttpClient client = discovery.getReference(record).get();

						client.get(metadata.getHealthPathCheck(), response -> future.complete(
								new Heartbeat()
									.setRecord(record)
									.setHealthy(healthStatus(response.statusCode())
								)
						)).exceptionHandler(future::fail)
						.end();
						
						return future;
					}).collect(Collectors.toList());
			
			return FutureSupplier.sequenceFuture(statusFutureList).compose(statusList -> {

				statusFutureList.stream()
						.filter(h -> h.result().isHealthy())
						.map(h -> h.result().getRecord())
						.collect(Collectors.toList())
							.forEach(this::downHttpEndpoint);
		    	 
		    	 return Future.succeededFuture("COMPLETE");
		     });
		});
	}
	
	private void downHttpEndpoint(Record record){

		record.setStatus(Status.DOWN);
		discovery.publish(record, ar -> {
				
				log.debug("Publish to discovery to DOWN record: {} ", record);
		});
	}

	/**
	 * Get all REST endpoints from the service discovery infrastructure.
	 *
	 * @return async result
	 */
	private Future<List<Record>> getAllEndpoints() {
		Future<List<Record>> future = Future.future();
		discovery.getRecords(record -> record.getType().equals(HttpEndpoint.TYPE), future.completer());
		return future;
	}
	
	private boolean healthStatus(int code) {
		return code == 200;
	}
}