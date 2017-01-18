package io.github.pflima92.plyshare.gateway.api.routes;

import org.jspare.core.annotation.Inject;
import org.jspare.vertx.annotation.VertxInject;
import org.jspare.vertx.web.annotation.handler.Handler;
import org.jspare.vertx.web.annotation.handling.Parameter;
import org.jspare.vertx.web.annotation.method.All;

import io.github.pflima92.plyshare.common.circuitbreaker.CircuitBreakerHolder;
import io.github.pflima92.plyshare.common.web.RestAPIHandler;
import io.github.pflima92.plyshare.gateway.GatewayOptionsHolder;
import io.github.pflima92.plyshare.gateway.entity.Audit;
import io.github.pflima92.plyshare.gateway.manager.GatewayManager;
import io.github.pflima92.plyshare.gateway.services.AuditService;
import io.github.pflima92.plyshare.gateway.services.LoadBalanceService;
import io.github.pflima92.plyshare.gateway.services.ProxyService;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.EventBus;
import io.vertx.servicediscovery.Record;

public class GatewayProxyRoute extends RestAPIHandler {

	@Inject
	private GatewayOptionsHolder gatewayOptionsHolder;
	
	@Inject
	private GatewayManager gatewayManager;
	
	@Inject
	private CircuitBreakerHolder circuitBreaker;

	@Inject
	private LoadBalanceService loadBalance;

	@Inject
	private ProxyService proxyService;

	@Inject
	private AuditService auditService;
	
	@VertxInject
	private EventBus eventBus;
	
	private Audit audit;
	
	@All("/api/:alias/*")
	@Handler
	public void gateway(@Parameter("alias") String alias) {

		processBuffer(buffer -> dispatchRequest(buffer, alias));
	}

	protected void processBuffer(io.vertx.core.Handler<Buffer> bufferHandler) {

		Buffer buffer = Buffer.buffer();
		req.handler(b -> {
			buffer.appendBuffer(b);
		});
		req.endHandler(v -> {

			if(isAuditEnabled()){
				audit(buffer);
			}
			bufferHandler.handle(buffer);
		});
	}

	protected void dispatchRequest(Buffer buffer, String alias) {

		circuitBreaker.execute(future -> 
			loadBalance.getRecord(alias, ar -> doDispatch(future, buffer, ar))
		).setHandler(ar -> {

			if (ar.failed() && !res.closed()) {

				// TODO notify error
				badGateway(ar.cause());
			}
		});
	}

	protected <T> void doDispatch(Future<T> future, Buffer buffer, AsyncResult<Record> resultHandler) {

		if (resultHandler.succeeded()) {

			Record record = resultHandler.result();
			proxyService.proxy(context, record, buffer).setHandler(v -> future.complete());
		} else {

			future.fail(resultHandler.cause());
		}
	}
	
	protected void audit(Buffer buffer) {
		onReceiveAudit(buffer.copy());
		res.bodyEndHandler(e -> {
			onDispatchAudit();
		});
	}
	
	protected void onReceiveAudit(Buffer buffer){
		
		audit = new Audit();
		audit.setTid(getTid());
		audit.setGateway(gatewayManager.getCurrentGateway());
		
		auditService.save(audit, res -> {
			if(res.succeeded()){
				audit = res.result();
			}
			// TODO notify error
		});
	}
	
	protected void onDispatchAudit(){
		auditService.save(audit, res -> {});
	}
	
	protected boolean isAuditEnabled(){
		
		return gatewayOptionsHolder.getOptions().isAudit();
	}
}