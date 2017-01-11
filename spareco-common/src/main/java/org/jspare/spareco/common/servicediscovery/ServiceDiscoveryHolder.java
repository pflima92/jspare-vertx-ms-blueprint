/*
 *
 */
package org.jspare.spareco.common.servicediscovery;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.jspare.core.annotation.Inject;
import org.jspare.core.annotation.Resource;
import org.jspare.core.container.MySupport;
import org.jspare.spareco.common.MicroserviceOptions;
import org.jspare.spareco.common.MicroserviceOptionsHolder;
import org.jspare.spareco.common.circuitbreaker.CircuitBreakerHolder;
import org.jspare.vertx.annotation.VertxInject;
import org.jspare.vertx.concurrent.FutureSupplier;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.json.JsonObject;
import io.vertx.servicediscovery.Record;
import io.vertx.servicediscovery.ServiceDiscovery;
import io.vertx.servicediscovery.ServiceReference;
import io.vertx.servicediscovery.types.HttpEndpoint;
import lombok.Getter;

@Resource
public class ServiceDiscoveryHolder extends MySupport {
	
	public static ServiceDiscoveryHolder create(ServiceDiscovery serviceDiscovery) {

		return new ServiceDiscoveryHolder(serviceDiscovery);
	}

	@VertxInject
	private Vertx vertx;

	@Getter
	private final ServiceDiscovery serviceDiscovery;

	@Inject
	private MicroserviceOptionsHolder microserviceOptions;

	@Inject
	private CircuitBreakerHolder circuitBreaker;

	private ServiceDiscoveryHolder(ServiceDiscovery serviceDiscovery) {

		this.serviceDiscovery = serviceDiscovery;
		startHealthCheck();
	}

	/**
	 * Lookups for a set of records. this method returns all matching records.
	 * <p>
	 * The filter is a {@link Function} taking a {@link Record} as argument and
	 * returning a boolean. You should see it as an {@code accept} method of a
	 * filter. This method return a record passing the filter.
	 * <p>
	 * This method only looks for records with a {@code UP} status.
	 *
	 * @param filter
	 *            the filter, must not be {@code null}. To return all records,
	 *            use a function accepting all records
	 * @param resultHandler
	 *            handler called when the lookup has been completed. When there
	 *            are no matching record, the operation succeed, but the async
	 *            result has an empty list as result.
	 */
	public void getRecords(Function<Record, Boolean> filter, Handler<AsyncResult<List<Record>>> resultHandler) {

		serviceDiscovery.getRecords(filter, resultHandler);
	}

	/**
	 * Gets a service reference from the given record.
	 *
	 * @param record
	 *            the chosen record
	 * @return the service reference, that allows retrieving the service object.
	 *         Once called the service reference is cached, and need to be
	 *         released.
	 */
	public ServiceReference getReference(Record record) {

		return serviceDiscovery.getReference(record);
	}

	/**
	 * Gets a service reference from the given record, the reference is
	 * configured with the given json object.
	 *
	 * @param record
	 *            the chosen record
	 * @param configuration
	 *            the configuration
	 * @return the service reference, that allows retrieving the service object.
	 *         Once called the service reference is cached, and need to be
	 *         released.
	 */
	public ServiceReference getReferenceWithConfiguration(Record record, JsonObject configuration) {

		return serviceDiscovery.getReferenceWithConfiguration(record, configuration);
	}

	private void startHealthCheck() {

		long period = microserviceOptions.getOptions().getPeriodicHealthCheck();
		vertx.setPeriodic(period, t -> {
			circuitBreaker.execute(future -> { 
				// behind the circuit breaker
				sendHeartBeatRequest().setHandler(future.completer());
			});
		});
	}

	/**
	 * Send heart-beat check request to every REST node in every interval and
	 * await response.
	 *
	 * @return async result. If all nodes are active, the result will be
	 *         assigned `true`, else the result will fail
	 */
	private Future<Object> sendHeartBeatRequest() {

		return getAllHttpEndpoints().compose(records -> {
			List<Future<JsonObject>> statusFutureList = records.stream()
					.filter(record -> record.getMetadata().getString("api.name") != null).map(record -> { 
						
						String hearthbeatPath = record.getMetadata().getString("hearthbeat.path", MicroserviceOptions.HEALTH_PATH_CHECK); 
						String apiName = record.getMetadata().getString("api.name");
						HttpClient client = serviceDiscovery.getReference(record).get();
						

						Future<JsonObject> future = Future.future();
						client.get(hearthbeatPath, response -> {
							future.complete(new JsonObject().put("name", apiName).put("status", healthStatus(response.statusCode())));
						}).exceptionHandler(future::fail).end();
						return future;
					}).collect(Collectors.toList());
			return FutureSupplier.sequenceFuture(statusFutureList); // get all
																// responses
		}).map(List::stream).compose(statusList -> {
			boolean notHealthy = statusList.anyMatch(status -> !status.getBoolean("status"));

			if (notHealthy) {
				String issues = statusList.filter(status -> !status.getBoolean("status")).map(status -> status.getString("name"))
						.collect(Collectors.joining(", "));

				String err = String.format("Heart beat check fail: %s", issues);
				// publish log
//				publishGatewayLog(err); TODO
				return Future.failedFuture(new IllegalStateException(err));
			} else {
				// publish log
//				publishGatewayLog("api_gateway_heartbeat_check_success"); TODO
				return Future.succeededFuture("OK");
			}
		});
	}
	
	/**
	 * Gets the all endpoints.
	 *
	 * @return async result
	 */
	public Future<List<Record>> getAllHttpEndpoints() {
		Future<List<Record>> future = Future.future();
		serviceDiscovery.getRecords(record -> record.getType().equals(HttpEndpoint.TYPE), future.completer());
		return future;
	}
	
	private boolean healthStatus(int code) {
	    return code == HttpResponseStatus.OK.code();
	  }
}