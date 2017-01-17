/*
 *
 */
package io.github.pflima92.plyshare.gateway.api;

import org.jspare.core.annotation.Inject;
import org.jspare.vertx.web.builder.RouterBuilder;

import io.github.pflima92.plyshare.common.RestAPIVerticle;
import io.github.pflima92.plyshare.gateway.api.handlers.TidHandler;
import io.github.pflima92.plyshare.gateway.api.routes.GatewayProxyRoute;
import io.github.pflima92.plyshare.gateway.common.GatewayOptionsHolder;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.ResponseTimeHandler;

public class ProxyAPIVerticle extends RestAPIVerticle {

	private final static String NAME = "apiGatewayProxy";

	@Inject
	private GatewayOptionsHolder gatewayOptionsHolder;


	@Override
	protected String getAPIName() {
		return NAME;
	}

	@Override
	protected Integer getApiPort() {
		return gatewayOptionsHolder.getOptions().getProxyPort();
	}

	@Override
	protected Router router() {

		return RouterBuilder.create(vertx)
					.scanClasspath(false)
					.addHandler(TidHandler.create())
					.addHandler(ResponseTimeHandler.create())
					.addRoute(GatewayProxyRoute.class)
					.build();
	}
}