/*
 *
 */
package org.jspare.spareco.gateway.services;

import java.util.Optional;

import org.jspare.core.annotation.Component;

import io.vertx.codegen.annotations.Fluent;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.servicediscovery.Record;

//@VertxGen TODO fix service class to use vertxgen
@Component
public interface LoadBalanceService {

	@Fluent
	LoadBalanceService getRecord(String alias, Handler<AsyncResult<Optional<Record>>> oRecordHandler);
}