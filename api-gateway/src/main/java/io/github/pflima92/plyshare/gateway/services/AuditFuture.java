package io.github.pflima92.plyshare.gateway.services;

import java.util.Optional;

import javax.sql.rowset.serial.SerialBlob;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.jspare.core.annotation.Inject;
import org.jspare.core.container.MySupport;
import org.jspare.vertx.annotation.VertxInject;

import io.github.pflima92.plyshare.common.web.HttpHeaders;
import io.github.pflima92.plyshare.gateway.GatewayOptionsHolder;
import io.github.pflima92.plyshare.gateway.entity.Audit;
import io.github.pflima92.plyshare.gateway.manager.GatewayManager;
import io.vertx.codegen.annotations.Nullable;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.WorkerExecutor;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import io.vertx.servicediscovery.Record;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class AuditFuture extends MySupport {

	private final RoutingContext context;

	@Inject
	private GatewayOptionsHolder gatewayOptionsHolder;

	@Inject
	private GatewayManager gatewayManager;

	@Inject
	private AuditService auditService;

	@VertxInject
	private WorkerExecutor workerExecutor;
	
	@VertxInject
	private EventBus eventBus;

	private Future<Audit> createFuture = Future.future();

	public static AuditFuture create(RoutingContext context) {

		return new AuditFuture(context);
	}

	private void persist(Audit audit, Handler<AsyncResult<Audit>> resultHandler) {
		// Skip audit if disabled
		if (!isAuditEnabled()) return;

		workerExecutor.executeBlocking(future -> {
			auditService.save(audit, res -> {
				// Update safe audit
				if (res.result() != null)
				future.complete(res.result());
			});
		}, resultHandler);
	}

	private boolean isAuditEnabled() {

		return gatewayOptionsHolder.getOptions().isAudit();
	}

	private void notifyAuditResult(AsyncResult<Audit> resultHandler) {

		if (resultHandler.succeeded()) {
			
			log.debug("Audit saved for TID [{}]", getTid());
		} else {
			log.error("Erro on save audit", resultHandler.cause());
			// TODO notify
		}
	}
	
	private String getTid() {
		
		return context.request().getHeader(HttpHeaders.TID);
	}

	@SneakyThrows
	public AuditFuture saveRequest(Buffer requestBuffer) {

		Audit audit = new Audit()
				.setGateway(gatewayManager.getCurrentGateway())
				.setTid(getTid())
				.setAlias(getAlias())
				.setUri(context.request().absoluteURI())
				.setHeaders(getHeaders())
				.setRequestContent(new SerialBlob(requestBuffer.getBytes()));

		persist(audit, res -> {
			if(res.succeeded()){
				
				createFuture.complete(res.result());
			}else{
				
				createFuture.fail(res.cause());
			}
		});
		
		return this;
	}

	private @Nullable String getAlias() {
		return context.request().getParam("alias");
	}

	@SneakyThrows
	public void saveResponse(Record record, Buffer responseBuffer) {

		createFuture.setHandler(res -> {
			
			try {

				Audit audit = res.result();
				audit
					.setRecord(record.toJson())
					.setResponseCode(context.response().getStatusCode())
					.setResponseContent(new SerialBlob(Optional.ofNullable(responseBuffer)
							.orElse(Buffer.buffer()).getBytes()
					));
				
				persist(audit, this::notifyAuditResult);
			} catch (Exception e) {
				
				log.error("Fail to save response");
			}
		});
	}
	
	@SneakyThrows
	public void saveResponse(Throwable throwable) {

		createFuture.setHandler(res -> {
			
			try {

				Audit audit = res.result();
				audit.setResponseCode(context.response().getStatusCode())
						.setError(ExceptionUtils.getStackTrace(throwable));
				
				persist(audit, this::notifyAuditResult);
			} catch (Exception e) {
				
				log.error("Fail to save response");
			}
		});
	}

	private JsonObject getHeaders() {
		JsonObject headers = new JsonObject();
		context.request().headers().forEach(e -> headers.put(e.getKey(), e.getValue()));
		return headers;
	}
}