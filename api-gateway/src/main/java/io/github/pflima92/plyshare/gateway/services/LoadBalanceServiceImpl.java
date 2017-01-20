/*
 *
 */
package io.github.pflima92.plyshare.gateway.services;

import java.util.List;
import java.util.Optional;

import org.jspare.core.annotation.Inject;

import io.github.pflima92.plyshare.common.discovery.RecordMetadata;
import io.github.pflima92.plyshare.common.discovery.ServiceDiscoveryHolder;
import io.github.pflima92.plyshare.common.web.BusinessException;
import io.github.pflima92.plyshare.common.web.Reason;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.servicediscovery.Record;

public class LoadBalanceServiceImpl implements LoadBalanceService {

	private static final Reason RECORD_NOT_FOUND_REASON = Reason.error("record_not_found").detail("Record not found");
	
	@Inject
	private ServiceDiscoveryHolder serviceDiscovery;

	@Override
	public LoadBalanceService getRecord(String alias, Handler<AsyncResult<Record>> resultHandler) {
		
		serviceDiscovery.getAllHttpEndpoints().setHandler(ar -> handleRecordList(alias, ar).setHandler(resultHandler));
		return this;
	}
	
	protected Future<Record> handleRecordList(String alias, AsyncResult<List<Record>> resultHandler){
		Future<Record> future = Future.future();
		
		if(resultHandler.succeeded()){

			// Simple LoadBalance with findAny on stream
			Optional<Record> client = resultHandler.result().stream()
					.filter(record -> RecordMetadata.of(record.getMetadata()).getName().equals(alias)).findAny();
			
			if(client.isPresent()) future.complete(client.get());
			else future.fail(new BusinessException(RECORD_NOT_FOUND_REASON));
		}else{
			future.fail(resultHandler.cause());
		}
		return future;
	}
}