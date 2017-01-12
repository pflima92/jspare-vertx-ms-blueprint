package org.jspare.spareco.gateway.services;

import org.jspare.core.annotation.Component;
import org.jspare.spareco.gateway.model.AuditData;

import io.vertx.codegen.annotations.Fluent;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

//@VertxGen TODO fix service class to use vertxgen
@Component
public interface AuditService {
	
	@Fluent
	AuditService save(AuditData audit, Handler<AsyncResult<?>> resultHandler);
}