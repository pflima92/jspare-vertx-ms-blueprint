/*
 *
 */
package org.jspare.spareco.gateway.services;

import java.util.List;
import java.util.Optional;

import org.jspare.core.annotation.Inject;
import org.jspare.spareco.common.servicediscovery.ServiceDiscoveryHolder;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.servicediscovery.Record;
import io.vertx.servicediscovery.types.HttpEndpoint;

public class LoadBalanceServiceImpl implements LoadBalanceService {

	@Inject
	private ServiceDiscoveryHolder discovery;

	@Override
	public LoadBalanceService getRecord(String alias, Handler<AsyncResult<Optional<Record>>> oRecordHandler) {
		
		Future<Optional<Record>> future = Future.future();
		oRecordHandler.handle(getAllEndpoints().compose(recordList -> {

			// Simple LoadBalance with findAny on stream
			Optional<Record> client = recordList.stream()
					.filter(record -> record.getMetadata().getString("api.name") != null)
					.filter(record -> record.getMetadata().getString("api.name").equals(alias)).findAny();
			
			future.complete(client);

		}, future));
		return this;
	}

	/**
	 * Gets the all endpoints.
	 *
	 * @return async result
	 */
	private Future<List<Record>> getAllEndpoints() {
		Future<List<Record>> future = Future.future();
		discovery.getServiceDiscovery().getRecords(record -> record.getType().equals(HttpEndpoint.TYPE), future.completer());
		return future;
	}
}