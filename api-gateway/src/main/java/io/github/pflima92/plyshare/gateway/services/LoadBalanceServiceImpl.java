/*
 *
 */
package io.github.pflima92.plyshare.gateway.services;

import java.util.List;
import java.util.Optional;

import org.jspare.core.annotation.Inject;

import io.github.pflima92.plyshare.common.discovery.RecordMetadata;
import io.github.pflima92.plyshare.common.discovery.ServiceDiscoveryHolder;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.servicediscovery.Record;

public class LoadBalanceServiceImpl implements LoadBalanceService {

	@Inject
	private ServiceDiscoveryHolder serviceDiscovery;

	@Override
	public LoadBalanceService getRecord(String alias, Handler<AsyncResult<Optional<Record>>> oRecordHandler) {
		
		serviceDiscovery.getAllHttpEndpoints().setHandler(ar -> handleRecordList(alias, ar).setHandler(oRecordHandler));
		return this;
	}
	
	protected Future<Optional<Record>> handleRecordList(String alias, AsyncResult<List<Record>> resultHandler){
		Future<Optional<Record>> future = Future.future();
		
		if(resultHandler.succeeded()){

			// Simple LoadBalance with findAny on stream
			Optional<Record> client = resultHandler.result().stream()
					.filter(record -> RecordMetadata.of(record.getMetadata()).getName().equals(alias)).findAny();
			
			future.complete(client);
		}else{
			future.fail(resultHandler.cause());
		}
		return future;
	}
}