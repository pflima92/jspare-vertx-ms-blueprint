package org.jspare.spareco.gateway.web.routes;

import java.util.Optional;

import org.jspare.core.annotation.Inject;
import org.jspare.spareco.common.circuitbreaker.CircuitBreakerHolder;
import org.jspare.spareco.gateway.services.AuditService;
import org.jspare.spareco.gateway.services.LoadBalanceService;
import org.jspare.spareco.gateway.services.ProxyService;
import org.jspare.spareco.gateway.web.handling.GatewayAPIHandler;
import org.jspare.vertx.annotation.VertxInject;
import org.jspare.vertx.web.annotation.handler.Handler;
import org.jspare.vertx.web.annotation.handling.Parameter;
import org.jspare.vertx.web.annotation.method.All;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.EventBus;
import io.vertx.servicediscovery.Record;

public class GatewayProxyRoute extends GatewayAPIHandler {

	@VertxInject
	private Vertx vertx;

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

			bufferHandler.handle(buffer);
		});
	}

	protected void dispatchRequest(Buffer buffer, String alias) {

		circuitBreaker.execute(future ->

		loadBalance.getRecord(alias, ar -> doDispatch(future, buffer, ar))).setHandler(ar -> {

			if (ar.failed() && !res.closed()) {

				// TODO notify error
				badGateway(ar.cause());
			}
		});
	}

	protected <T> void doDispatch(Future<T> future, Buffer buffer, AsyncResult<Optional<Record>> resultHandler) {

		if (resultHandler.succeeded()) {

			Optional<Record> oRecord = resultHandler.result();
			if (oRecord.isPresent()) {

				Record record = oRecord.get();
				proxyService.proxy(ctx, record, buffer).setHandler(v -> future.complete());
			} else {

				future.fail(new Throwable("Cannot find any instance for this request."));
			}
		} else {

			future.fail(resultHandler.cause());
		}
	}
}