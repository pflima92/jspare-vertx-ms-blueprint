package io.github.pflima92.plyshare.gateway.api.routes;

import org.jspare.vertx.web.annotation.auth.Auth;
import org.jspare.vertx.web.annotation.handler.Handler;
import org.jspare.vertx.web.annotation.method.Get;
import org.jspare.vertx.web.annotation.method.Post;

import io.github.pflima92.plyshare.common.web.RestAPIHandler;
import io.vertx.ext.web.RoutingContext;

public class UAARoute extends RestAPIHandler {

	@Auth
	@Get("/gatekeeper")
	@Handler
	public void getGatekeeper(RoutingContext context) {

		if (context.user() != null) {

			success(context.user().principal());

		} else {
			unauthorized();
		}
	}

	@Auth
	@Post("/login")
	@Handler
	public void login(RoutingContext ctx) {

		ctx.response().setStatusCode(204).end();
	}

	@Auth
	@Post("/logout")
	@Handler
	public void logout(RoutingContext ctx) {

		ctx.clearUser();
		ctx.session().destroy();
		ctx.response().setStatusCode(204).end();
	}

}