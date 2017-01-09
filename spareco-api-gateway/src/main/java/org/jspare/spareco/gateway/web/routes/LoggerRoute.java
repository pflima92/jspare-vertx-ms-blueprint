/*
 *
 */
package org.jspare.spareco.gateway.web.routes;

import org.jspare.spareco.gateway.web.handling.GatewayAPIHandler;
import org.jspare.vertx.web.annotation.handler.Handler;
import org.jspare.vertx.web.annotation.method.Get;
import org.jspare.vertx.web.annotation.subrouter.SubRouter;

@SubRouter("/api/logger")
public class LoggerRoute extends GatewayAPIHandler {

	@Get
	@Handler
	public void list() {

		success();
	}
}