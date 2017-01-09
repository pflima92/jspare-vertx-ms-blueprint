/*
 *
 */
package org.jspare.spareco.common.servicediscovery;

import java.util.List;
import java.util.function.Function;

import org.jspare.core.annotation.Resource;
import org.jspare.core.container.MySupport;
import org.jspare.vertx.annotation.VertxInject;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.servicediscovery.Record;
import io.vertx.servicediscovery.ServiceDiscovery;
import io.vertx.servicediscovery.ServiceReference;
import io.vertx.servicediscovery.Status;
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

		// TODO
		long periodic = 5000l;
		vertx.setPeriodic(periodic, timer -> {

			serviceDiscovery.getRecords(r -> r.getType().equals(HttpEndpoint.TYPE) && r.getStatus().equals(Status.UP), ar -> {

				ar.result().stream().forEach(r -> {

					// TODO call health check

				});
			});
		});
	}
}