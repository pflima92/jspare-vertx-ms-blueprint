/*
 *
 */
package io.github.pflima92.plyshare.gateway.api.routes;

import org.jspare.vertx.web.annotation.handler.Handler;
import org.jspare.vertx.web.annotation.method.Get;
import org.jspare.vertx.web.annotation.subrouter.SubRouter;

import io.github.pflima92.plyshare.gateway.api.handling.GatewayAPIHandler;

@SubRouter("/api/logger")
public class LoggerRoute extends GatewayAPIHandler {

	@Get
	@Handler
	public void list() {

		success();
	}
}