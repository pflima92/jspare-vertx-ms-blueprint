/*
 *
 */
package org.jspare.spareco.gateway.web;

import org.jspare.core.annotation.Inject;
import org.jspare.spareco.common.RestAPIVerticle;
import org.jspare.spareco.gateway.common.GatewayOptionsHolder;
import org.jspare.spareco.gateway.web.handlers.TidHandler;
import org.jspare.spareco.gateway.web.routes.GatewayProxyRoute;
import org.jspare.vertx.web.builder.RouterBuilder;

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