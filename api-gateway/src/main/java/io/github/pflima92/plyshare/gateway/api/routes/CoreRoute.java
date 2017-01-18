/*
 *
 */
package io.github.pflima92.plyshare.gateway.api.routes;


import org.jspare.core.annotation.Inject;
import org.jspare.vertx.web.annotation.handler.Handler;
import org.jspare.vertx.web.annotation.method.Get;
import org.jspare.vertx.web.annotation.subrouter.SubRouter;

import io.github.pflima92.plyshare.common.web.RestAPIHandler;
import io.github.pflima92.plyshare.gateway.GatewayOptionsHolder;
import io.github.pflima92.plyshare.gateway.manager.GatewayManager;


@SubRouter("/api")
public class CoreRoute extends RestAPIHandler {

	@Inject
	private GatewayOptionsHolder gatewayOptionsHolder;
	
	@Inject
	private GatewayManager gatewayManager;
	
	@Get("/options")
	@Handler
	public void config() {

		success(gatewayOptionsHolder.getOptions());
	}

	@Get("/gateway")
	@Handler
	public void version() {

		gatewayManager.findGateway(gatewayOptionsHolder.getOptions().getProfile()).setHandler(this::handler);
	}
}
