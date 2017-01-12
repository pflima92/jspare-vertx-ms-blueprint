/*
 *
 */
package org.jspare.spareco.common;

import org.jspare.core.annotation.Inject;
import org.jspare.spareco.common.discovery.ServiceDiscoveryHolder;
import org.jspare.spareco.common.environment.CommonExitCode;
import org.jspare.vertx.web.builder.HttpServerBuilder;
import org.jspare.vertx.web.builder.RouterBuilder;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.servicediscovery.Record;
import io.vertx.servicediscovery.ServiceDiscovery;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class RestAPIVerticle extends MicroserviceVerticle {

	@Inject
	private ServiceDiscoveryHolder discoveryHolder;

	private Record record;

	@Override
	public void start() throws Exception {

		log.debug("Started RestAPIVerticle {}", getClass().getName());

		setOptions();

		initialize();
	}

	@Override
	public void stop(Future<Void> future) throws Exception {

		log.debug("Stoping verticle {}", getClass().getName());

		ServiceDiscovery discovery = discoveryHolder.getServiceDiscovery();

		Future<Void> unregistryFuture = Future.future();
		discovery.unpublish(record.getRegistration(), unregistryFuture.completer());

		unregistryFuture.setHandler(ar -> {

			if (discovery != null) {
				discovery.close();
			}

			if (ar.failed()) {
				future.fail(ar.cause());
			} else {
				future.complete();
			}
		});
	}

	/**
	 * Enable heartbeat check.
	 *
	 * @param router
	 *            the router
	 */
	protected void enableHeartbeatCheck(Router router) {

		router.get(getHealthCheckPath()).handler(ctx -> ctx.response().end(new JsonObject().put("status", "UP").encode()));
	}

	protected HttpServerOptions httpServerOptions() {

		HttpServerOptions httpServerOptions = new HttpServerOptions(options.getHttpServerOptions());
		return httpServerOptions;
	}

	@Override
	protected void initialize() {

		Router router = router();
		enableHeartbeatCheck(router);

		String address = getAddress();
		Integer port = getApiPort();

		HttpServerBuilder.create(vertx).router(router).httpServerOptions(httpServerOptions()).build().listen(port, address,
				this::onHttpServer);
	}

	protected void onHttpServer(AsyncResult<HttpServer> ar) {

		if (ar.succeeded()) {

			record = createHttpEndpoint(ar.result().actualPort(), httpServerOptions().isSsl());

			log.info("{} HttpServer listening at port {}", record.getName(), ar.result().actualPort());

			ServiceDiscovery discovery = discoveryHolder.getServiceDiscovery();
			discovery.publish(record, this::onPublish);
		} else {

			log.error("HttpServer cannot be  initialized", ar.cause());
			System.exit(CommonExitCode.HTTP_SERVER_ERROR);
		}
	}

	protected void onPublish(AsyncResult<Record> resultHandler) {

		if (resultHandler.succeeded()) {

			log.info("{} microservice registered on ServiceDiscovery", resultHandler.result().getName());
		} else {

			log.error("Fail to registry {} microservice, fall out of process", record.getName(), resultHandler.cause());
			System.exit(CommonExitCode.REGISTRY_SERVICE_DISCOVERY_ERROR);
		}
	}

	protected Router router() {

		return RouterBuilder.create(vertx).build();
	}
}