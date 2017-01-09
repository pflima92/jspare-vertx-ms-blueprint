/*
 *
 */
package org.jspare.spareco.gateway.web.routes;

import org.jspare.core.annotation.Inject;
import org.jspare.spareco.gateway.model.User;
import org.jspare.spareco.gateway.services.UserService;
import org.jspare.spareco.gateway.web.handling.GatewayAPIHandler;
import org.jspare.vertx.web.annotation.auth.Auth;
import org.jspare.vertx.web.annotation.handler.Handler;
import org.jspare.vertx.web.annotation.handling.Model;
import org.jspare.vertx.web.annotation.handling.Parameter;
import org.jspare.vertx.web.annotation.method.Get;
import org.jspare.vertx.web.annotation.method.Post;
import org.jspare.vertx.web.annotation.subrouter.SubRouter;

@SubRouter("/api/users")
public class UserRoute extends GatewayAPIHandler {

	@Inject
	private UserService userService;

	@Auth
	@Get("/:id")
	@Handler
	public void byId(@Parameter("id") final Integer id) {

		userService.getById(id).setHandler(this::handler);
	}

	@Auth
	@Get
	@Handler
	public void list() {

		userService.list().setHandler(this::handler);
	}

	@Auth
	@Post
	@Handler
	public void save(@Model User user) {

		userService.save(user).setHandler(this::handler);
	}
}