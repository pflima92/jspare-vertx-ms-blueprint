/*
 *
 */
package org.jspare.spareco.gateway.services;

import java.util.Optional;

import org.jspare.core.annotation.Inject;
import org.jspare.spareco.common.servicediscovery.ServiceDiscoveryHolder;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.servicediscovery.Record;

public class LoadBalanceServiceImpl implements LoadBalanceService {

	@Inject
	private ServiceDiscoveryHolder serviceDiscovery;

	@Override
	public LoadBalanceService getRecord(String alias, Handler<AsyncResult<Optional<Record>>> oRecordHandler) {
		
		Future<Optional<Record>> future = Future.future();
		oRecordHandler.handle(serviceDiscovery.getAllHttpEndpoints().compose(recordList -> {

			// Simple LoadBalance with findAny on stream
			Optional<Record> client = recordList.stream()
					.filter(record -> record.getMetadata().getString("api.name") != null)
					.filter(record -> record.getMetadata().getString("api.name").equals(alias)).findAny();
			
			future.complete(client);

		}, future));
		return this;
	}
}