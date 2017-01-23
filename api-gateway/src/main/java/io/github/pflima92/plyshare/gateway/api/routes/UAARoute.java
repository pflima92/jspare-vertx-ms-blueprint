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
	public void login() {
		
		context.request().handler(buffer -> {
			
			securityManager.execute(buffer.toJsonObject(), this::handleAuthentication);
		});

	}

	private void handleAuthentication(AsyncResult<JsonObject> resultHandler) {

		if (resultHandler.failed()) {
			unauthorized(resultHandler.cause());
			return;
		}

		JsonObject jwtOptions = vertx.getOrCreateContext().config().getJsonObject("jwtOptions", new JsonObject().put("expiresInSeconds", 60));
		JWTOptions options = new JWTOptions().setExpiresInSeconds(jwtOptions.getLong("expiresInSeconds"));

		JsonObject user = resultHandler.result();
		String jwt = jwtAuth.getJWTAuth().generateToken(user, options);
		success(new JsonObject().put("jwt", jwt));
	}
}