/*
 *
 */
package io.github.pflima92.plyshare.gateway.services;

import org.jspare.core.annotation.Component;

import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.servicediscovery.Record;

@VertxGen
@Component
public interface LoadBalanceService {

	@Fluent
	LoadBalanceService getRecord(String alias, Handler<AsyncResult<Record>> oRecordHandler);
}