/*
 *
 */
package io.github.pflima92.plyshare.gateway.api.routes;

import org.jspare.vertx.web.annotation.handler.Handler;
import org.jspare.vertx.web.annotation.method.Get;
import org.jspare.vertx.web.annotation.subrouter.SubRouter;
import org.jspare.vertx.web.handler.APIHandler;

import io.github.pflima92.plyshare.gateway.common.Version;

@SubRouter("/api")
public class CoreRoute extends APIHandler {

	@Get("/config")
	@Handler
	public void config() {

		success(vertx.getOrCreateContext().config().encodePrettily());
	}

	@Get("/version")
	@Handler
	public void version() {

		success(Version.currentVersion);
	}
}
