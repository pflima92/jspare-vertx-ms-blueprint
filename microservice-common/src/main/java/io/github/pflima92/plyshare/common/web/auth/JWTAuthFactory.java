package io.github.pflima92.plyshare.common.web.auth;

import org.jspare.core.annotation.Resource;
import org.jspare.vertx.annotation.VertxInject;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.jwt.JWTAuth;

@Resource
public class JWTAuthFactory {

	@VertxInject
	private Vertx vertx;

	private JWTAuth jwtAuth;

	public JWTAuth getJWTAuth() {

		if (jwtAuth == null) {

			jwtAuth = createJWTAuth();
		}
		return jwtAuth;
	}

	private JWTAuth createJWTAuth() {

		// Set default config
		JsonObject defaultConfig = new JsonObject().put("keyStore",
				new JsonObject()
					.put("path", "keystore.jceks")
					.put("type", "jceks")
					.put("password", "secret")
				);

		// Merge with config jwtAuth attribute
		JsonObject config = vertx.getOrCreateContext().config().getJsonObject("jwtAuth", defaultConfig);

		// Prepare JWT Auth
		return JWTAuth.create(vertx, config);
	}
}