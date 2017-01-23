package io.github.pflima92.plyshare.gateway.api.routes;

import org.jspare.core.annotation.Inject;
import org.jspare.vertx.web.annotation.auth.Auth;
import org.jspare.vertx.web.annotation.handler.Handler;
import org.jspare.vertx.web.annotation.method.Get;
import org.jspare.vertx.web.annotation.method.Post;

import io.github.pflima92.plyshare.common.web.RestAPIHandler;
import io.github.pflima92.plyshare.common.web.auth.JWTAuthFactory;
import io.github.pflima92.plyshare.gateway.api.model.AuthRequest;
import io.github.pflima92.plyshare.gateway.manager.SecurityManager;
import io.vertx.core.AsyncResult;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.User;
import io.vertx.ext.auth.jwt.JWTOptions;

public class UAARoute extends RestAPIHandler {

	@Inject
	private JWTAuthFactory jwtAuth;

	@Inject
	private SecurityManager securityManager;

	@Auth
	@Get("/principal")
	@Handler
	public void getUserPrincipal() {
		User user = getUser();
		if (user == null) {

			noContent();
			return;
		}
		success(user);
	}

	@Post("/login")
	@Handler
	public void login(AuthRequest authRequest) {

		securityManager.execute(authRequest.toJson(), this::handleAuthentication);
	}

	private void handleAuthentication(AsyncResult<JsonObject> resultHandler) {

		if (resultHandler.failed()) {
			unauthorized(resultHandler.cause());
			return;
		}

		JsonObject custom = vertx.getOrCreateContext().config().getJsonObject("jwtOptions", new JsonObject().put("expiresInSeconds", 60));

		JWTOptions options = new JWTOptions().setExpiresInSeconds(custom.getLong("expiresInSeconds"));

		for (int i = 0; i < 200; i++) {
			options.addPermission("teste:" + i);
		}

		JsonObject json = new JsonObject().put("username", "Paulo Ferreira");
		String jwt = jwtAuth.getJWTAuth().generateToken(json, options);
		success(new JsonObject().put("jwt", jwt));
	}
}