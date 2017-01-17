/*
 *
 */
package io.github.pflima92.plyshare.gateway.api.auth;

import java.util.Optional;

import org.jspare.core.annotation.Inject;
import org.jspare.core.container.MySupport;

import io.github.pflima92.plyshare.gateway.services.UserService;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.AuthProvider;
import io.vertx.ext.auth.User;

public class GatewayAuthProvider extends MySupport implements AuthProvider {

	public static GatewayAuthProvider create() {

		return new GatewayAuthProvider();
	}

	@Inject
	private UserService userService;

	private GatewayAuthProvider() {
	}

	@Override
	public void authenticate(JsonObject authInfo, Handler<AsyncResult<User>> resultHandler) {

		String username = authInfo.getString("username");
		String password = authInfo.getString("password");

		userService.getByUsernameAndPassword(username, password).setHandler(res -> {

			if (res.succeeded()) {

				Optional<io.github.pflima92.plyshare.gateway.entity.User> oUser = res.result();
				if (oUser.isPresent()) {

					resultHandler.handle(Future.succeededFuture(new AuthUser(oUser.get())));
				} else {

					resultHandler.handle(Future.failedFuture("Username or Password wrong"));
				}
			} else {

				resultHandler.handle(Future.failedFuture(res.cause()));
			}
		});
	}
}