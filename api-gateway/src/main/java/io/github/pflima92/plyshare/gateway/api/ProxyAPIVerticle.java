/*
 *
 */
package io.github.pflima92.plyshare.gateway.api;

import java.util.Arrays;

import org.jspare.core.annotation.Inject;
import org.jspare.vertx.web.builder.RouterBuilder;

import io.github.pflima92.plyshare.common.RestAPIVerticle;
import io.github.pflima92.plyshare.common.web.auth.MicroserviceAuthHandler;
import io.github.pflima92.plyshare.common.web.auth.MicroserviceAuthProvider;
import io.github.pflima92.plyshare.gateway.GatewayOptionsHolder;
import io.github.pflima92.plyshare.gateway.api.handlers.TidHandler;
import io.github.pflima92.plyshare.gateway.api.routes.GatewayProxyRoute;
import io.github.pflima92.plyshare.gateway.api.routes.UAARoute;
import io.vertx.ext.auth.AuthProvider;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.ResponseTimeHandler;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.sstore.ClusteredSessionStore;

public class ProxyAPIVerticle extends RestAPIVerticle {

	private final static String NAME = "apiGatewayProxy";

	@Inject
	private GatewayOptionsHolder gatewayOptionsHolder;


	@Override
	protected String getAPIName() {
		return NAME;
	}

	@Override
	protected Integer getApiPort() {
		return gatewayOptionsHolder.getOptions().getProxyPort();
	}

	protected AuthProvider getAuthProvider() {

 		// TODO
		return new MicroserviceAuthProvider(Arrays.asList(Object.class));
	}
	
	@Override
	protected Router router() {
		
		AuthProvider authProvider = getAuthProvider();
		
		return RouterBuilder.create(vertx)
					.scanClasspath(false)
					.authHandler(() -> MicroserviceAuthHandler.create(authProvider))
					.addHandler(TidHandler.create())
					.addHandler(ResponseTimeHandler.create())
					.addHandler(SessionHandler.create(ClusteredSessionStore.create(vertx)))
					.addRoute(GatewayProxyRoute.class)
					.addRoute(UAARoute.class)
					.build();
	}
}