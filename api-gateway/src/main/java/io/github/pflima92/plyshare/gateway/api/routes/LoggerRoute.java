/*
 *
 */
package io.github.pflima92.plyshare.gateway.api.routes;

import org.jspare.vertx.web.annotation.handler.Handler;
import org.jspare.vertx.web.annotation.method.Get;
import org.jspare.vertx.web.annotation.subrouter.SubRouter;

import io.github.pflima92.plyshare.common.web.RestAPIHandler;

@SubRouter("/api/logger")
public class LoggerRoute extends RestAPIHandler {

	@Get
	@Handler
	public void list() {

		success();
	}
}