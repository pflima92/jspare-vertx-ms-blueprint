package io.github.pflima92.plyshare.gateway.api.routes;

import org.jspare.core.annotation.Inject;
import org.jspare.vertx.annotation.VertxInject;
import org.jspare.vertx.web.annotation.handler.Handler;
import org.jspare.vertx.web.annotation.handling.Parameter;
import org.jspare.vertx.web.annotation.method.All;

import io.github.pflima92.plyshare.common.circuitbreaker.CircuitBreakerHolder;
import io.github.pflima92.plyshare.common.web.RestAPIHandler;
import io.github.pflima92.plyshare.gateway.services.LoadBalanceService;
import io.github.pflima92.plyshare.gateway.services.ProxyService;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.EventBus;
import io.vertx.servicediscovery.Record;

public class GatewayProxyRoute extends RestAPIHandler {

	@Inject
	private CircuitBreakerHolder circuitBreaker;

	@Inject
	private LoadBalanceService loadBalance;

	@Inject
	private ProxyService proxyService;

	@VertxInject
	private EventBus eventBus;
	
	@All("/proxy/:alias/*")
	@Handler
	public void gateway(@Parameter("alias") String alias) {
		
		processBuffer(buffer -> dispatchRequest(buffer, alias));
	}

	private void dispatchRequest(Buffer buffer, String alias) {

		circuitBreaker.execute(future -> loadBalance.getRecord(alias, ar -> doDispatch(future, buffer, ar))).setHandler(ar -> {
			
			if (ar.failed() && !res.closed()) {

				// TODO notify error
				badGateway(ar.cause());
			}
		});
	}

	private <T> void doDispatch(Future<T> future, Buffer buffer, AsyncResult<Record> resultHandler) {
		
		AuditFuture audit = AuditFuture.create(context).saveRequest(buffer.copy());
		if (resultHandler.succeeded()) {

			Record record = resultHandler.result();
			proxyService.proxy(context, record, buffer).setHandler(ar -> {
				
				audit.saveResponse(record,  ar.result());
				future.complete();
			});
		} else {

			audit.saveResponse(resultHandler.cause());
			future.fail(resultHandler.cause());
		}
	}

	private void processBuffer(io.vertx.core.Handler<Buffer> bufferHandler) {

		Buffer buffer = Buffer.buffer();
		req.handler(b -> {
			buffer.appendBuffer(b);
		});
		req.endHandler(v -> {
			
			bufferHandler.handle(buffer);
		});
	}
}