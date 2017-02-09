package io.github.pflima92.plyshare.gateway.manager;

import org.jspare.core.annotation.Component;

import io.github.pflima92.plyshare.gateway.ext.AuthAdapter;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;

@Component
public interface SecurityManager {

	SecurityManager registerAdapter(Class<?> clazz, Handler<AsyncResult<String>> resultHandler);

	SecurityManager registerAdapter(AuthAdapter adapter, Handler<AsyncResult<String>> resultHandler);

	SecurityManager execute(JsonObject authInfo, Handler<AsyncResult<JsonObject>> resultHandler );
}