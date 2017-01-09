/*
 *
 */
package org.jspare.spareco.dashboard.verticle;

import org.jspare.vertx.web.builder.HttpServerBuilder;
import org.jspare.vertx.web.builder.RouterBuilder;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HttpDashboardVerticle extends AbstractVerticle {

	@Override
	public void start() throws Exception {

		Router router = RouterBuilder.create(vertx).route(r -> r.path("/*").handler(StaticHandler.create("webroot"))).build();

		HttpServer httpServer = HttpServerBuilder.create(vertx).router(router).build();
		httpServer.listen(9009, result -> {

			log.debug("HttpServer listening at port {}", result.result().actualPort());
		});
	}
}