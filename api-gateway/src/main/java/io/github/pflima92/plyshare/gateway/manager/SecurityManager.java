package io.github.pflima92.plyshare.gateway.manager;

import org.jspare.core.annotation.Component;

import io.github.pflima92.plyshare.gateway.ext.AuthAdapter;
import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;

@VertxGen
@Component
public interface SecurityManager {

	@Fluent
	SecurityManager registerAdapter(Class<?> clazz, Handler<AsyncResult<String>> resultHandler);

	@Fluent
	SecurityManager registerAdapter(AuthAdapter adapter, Handler<AsyncResult<String>> resultHandler);

	@Fluent
	SecurityManager execute(JsonObject authInfo, Handler<AsyncResult<JsonObject>> resultHandler );

}