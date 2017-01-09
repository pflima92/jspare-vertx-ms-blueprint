/*
 *
 */
package org.jspare.spareco.gateway.web.handling;

import java.util.Optional;

import org.jspare.vertx.web.handler.APIHandler;

import io.vertx.core.AsyncResult;
import io.vertx.core.json.JsonObject;

public abstract class GatewayAPIHandler extends APIHandler {

	@SuppressWarnings("rawtypes")
	protected void handler(AsyncResult<?> asyncResult) {

		if (asyncResult.failed()) {
			error(asyncResult.cause());
			return;
		}

		if (asyncResult.result().getClass().equals(Optional.class) && !((Optional) asyncResult.result()).isPresent()) {

			noContent();
			return;
		}

		success(asyncResult.result());
	}
	
	protected JsonObject responseInfo(String message){
		// TODO validate
		return new JsonObject().put("message", message);
	}
	
}