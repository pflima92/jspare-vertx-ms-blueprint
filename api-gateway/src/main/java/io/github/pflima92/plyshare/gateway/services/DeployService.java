package io.github.pflima92.plyshare.gateway.services;

import org.jspare.core.annotation.Component;

import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;

@VertxGen
@Component
public interface DeployService {

	@Fluent
	DeployService deploy(JsonObject metadata, Handler<AsyncResult<String>> resultHandler);
	
	@Fluent
	DeployService stop(JsonObject metadata, Handler<AsyncResult<Void>> resultHandler);
}
