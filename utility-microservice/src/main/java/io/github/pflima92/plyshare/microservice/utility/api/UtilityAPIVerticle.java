/*
 * 
 */
package io.github.pflima92.plyshare.microservice.utility.api;

import org.jspare.vertx.web.builder.RouterBuilder;

import io.github.pflima92.plyshare.common.RestAPIVerticle;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.sstore.ClusteredSessionStore;

public class UtilityAPIVerticle extends RestAPIVerticle {

	@Override
	protected Router router() {

		return RouterBuilder.create(vertx)
				.scanClasspath(true)
				.authHandler(this::authHandler)
				.addHandler(SessionHandler.create(ClusteredSessionStore.create(vertx)))
				.build();
	}
}