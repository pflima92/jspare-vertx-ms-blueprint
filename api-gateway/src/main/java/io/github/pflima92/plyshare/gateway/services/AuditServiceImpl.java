package io.github.pflima92.plyshare.gateway.services;

import org.jspare.core.annotation.Inject;

import io.github.pflima92.plyshare.gateway.entity.Audit;
import io.github.pflima92.plyshare.gateway.persistance.GatewayPersistance;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;

public class AuditServiceImpl implements AuditService {

	@Inject
	private GatewayPersistance persistance;
	
	@Override
	public AuditService save(Audit audit, Handler<AsyncResult<Audit>> resultHandler) {
		
		resultHandler.handle(persistance.persistAudit(audit));
		return this;
	}

	@Override
	public AuditService findByTid(String tid, Handler<AsyncResult<Audit>> resultHandler) {

		Future<Audit> future = Future.future();
		persistance.findAuditByTid(tid).compose(oAudit -> {
			if(oAudit.isPresent()){
				
				future.complete(oAudit.get());
			}else{
				future.fail("not found");
			}
		}, future);
		resultHandler.handle(future);
		return this;
	}
}