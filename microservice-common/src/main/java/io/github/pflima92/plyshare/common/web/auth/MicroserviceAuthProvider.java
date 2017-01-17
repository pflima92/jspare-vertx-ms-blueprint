package io.github.pflima92.plyshare.common.web.auth;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import io.github.pflima92.plyshare.common.web.Gatekeeper;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.AuthProvider;
import io.vertx.ext.auth.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MicroserviceAuthProvider implements AuthProvider {

	private final List<Object> providers;
	
	@Override
	public void authenticate(JsonObject authInfo, Handler<AsyncResult<User>> resultHandler) {
		
		String username = authInfo.getString("username");
		String password = authInfo.getString("password");

		Gatekeeper gatekeeper = new Gatekeeper();
		gatekeeper.setUsername("paulo.ferreira");
		gatekeeper.setPermissions(Arrays.asList("a","b"));
		
		// TODO
		resultHandler.handle(Future.succeededFuture(gatekeeper));
	}
}