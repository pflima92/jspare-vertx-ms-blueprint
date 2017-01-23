package io.github.pflima92.plyshare.gateway.ext;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;

public interface AuthAdapter {

	/**
	   * Authenticate a user.
	   * <p>
	   * The first argument is a JSON object containing information for authenticating the user. What this actually contains
	   * depends on the specific implementation. In the case of a simple username/password based
	   * authentication it is likely to contain a JSON object with the following structure:
	   * <pre>
	   *   {
	   *     "username": "tim",
	   *     "password": "mypassword"
	   *   }
	   * </pre>
	   * For other types of authentication it contain different information - for example a JWT token or OAuth bearer token.
	   * <p>
	   * If the user is successfully authenticated a {@link JsonObject} object is passed to the handler in an {@link io.vertx.core.AsyncResult}.
	   * The user object can then be used for authorisation.
	   *
	   * @param authInfo  The auth information
	   * @param resultHandler  The result handler
	   */
	void authenticate(JsonObject authInfo, Handler<AsyncResult<JsonObject>> resultHandler);
	
}