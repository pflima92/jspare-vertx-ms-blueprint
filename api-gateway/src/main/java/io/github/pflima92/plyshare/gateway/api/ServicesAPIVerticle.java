/*
 *
 */
package io.github.pflima92.plyshare.gateway.api;

import org.jspare.core.annotation.Inject;
import org.jspare.vertx.web.builder.RouterBuilder;

import io.github.pflima92.plyshare.common.RestAPIVerticle;
import io.github.pflima92.plyshare.gateway.common.GatewayOptionsHolder;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.ResponseTimeHandler;

public class ServicesAPIVerticle extends RestAPIVerticle {

	private final static String NAME = "apiGatewayServices";

	@Inject
	private GatewayOptionsHolder gatewayOptionsHolder;

	@Override
	protected String getAPIName() {
		return NAME;
	}

	@Override
	protected Integer getApiPort() {
		return gatewayOptionsHolder.getOptions().getApiPort();
	}

	@Override
	protected Router router() {

		return RouterBuilder.create(vertx)
				.scanClasspath(true)
				.addHandler(ResponseTimeHandler.create())
				.addHandler(BodyHandler.create())
				.build();
	}
}