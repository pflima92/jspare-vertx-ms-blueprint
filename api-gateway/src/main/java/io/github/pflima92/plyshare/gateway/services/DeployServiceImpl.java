package io.github.pflima92.plyshare.gateway.services;

import org.jspare.vertx.annotation.VertxInject;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

public class DeployServiceImpl implements DeployService {

	@VertxInject
	private Vertx vertx;
	
	@Override
	public DeployService deploy(JsonObject metadata, Handler<AsyncResult<String>> resultHandler) {

		vertx.deployVerticle(metadata.getString("verticle"), resultHandler);
		return this;
	}

	@Override
	public DeployService stop(JsonObject metadata, Handler<AsyncResult<Void>> resultHandler) {
		
		vertx.undeploy(metadata.getString("deploymentId"), resultHandler);
		return this;
	}

}
