/*
 * 
 */
package io.github.pflima92.plyshare.microservice.utility.api;

import java.util.Arrays;

import org.jspare.vertx.web.builder.RouterBuilder;

import io.github.pflima92.plyshare.common.RestAPIVerticle;
import io.github.pflima92.plyshare.common.web.auth.MicroserviceAuthHandler;
import io.github.pflima92.plyshare.common.web.auth.MicroserviceAuthProvider;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.sstore.ClusteredSessionStore;

public class UtilityAPIVerticle extends RestAPIVerticle {

	@Override
	protected Router router() {

		return RouterBuilder.create(vertx)
				.scanClasspath(true)
				.authHandler(() -> MicroserviceAuthHandler.create(new MicroserviceAuthProvider(Arrays.asList(Object.class))))
				.addHandler(SessionHandler.create(ClusteredSessionStore.create(vertx)))
				.build();
	}
}