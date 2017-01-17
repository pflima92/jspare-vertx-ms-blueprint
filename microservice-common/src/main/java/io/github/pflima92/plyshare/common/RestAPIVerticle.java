/*
 *
 */
package io.github.pflima92.plyshare.common;

import org.jspare.core.annotation.Inject;
import org.jspare.vertx.web.builder.HttpServerBuilder;
import org.jspare.vertx.web.builder.RouterBuilder;

import io.github.pflima92.plyshare.common.circuitbreaker.CircuitBreakerHolder;
import io.github.pflima92.plyshare.common.discovery.ServiceDiscoveryHolder;
import io.github.pflima92.plyshare.common.environment.CommonExitCode;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.servicediscovery.Record;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class RestAPIVerticle extends MicroserviceVerticle {

	@Inject
	private ServiceDiscoveryHolder discoveryHolder;

	@Inject
	private CircuitBreakerHolder circuitBreakerHolder;

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
		
		discovery = discoveryHolder.getServiceDiscovery();
		circuitBreaker = circuitBreakerHolder.getCircuitBreaker();

		Router router = router();
		enableHeartbeatCheck(router);

		String address = getAddress();
		Integer port = getApiPort();

		HttpServerBuilder.create(vertx).router(router).httpServerOptions(httpServerOptions()).build().listen(port, address,
				this::onHttpServer);
	}

	protected void onHttpServer(AsyncResult<HttpServer> resultHandler) {

		log.info("{} HttpServer listening at port {}", getAPIName(), resultHandler.result().actualPort());
		
		if (resultHandler.succeeded()) {

			publishHttpEndpoint(resultHandler.result().actualPort(), httpServerOptions().isSsl()).setHandler(this::onPublish);
		} else {

			log.error("HttpServer cannot be  initialized", resultHandler.cause());
			System.exit(CommonExitCode.HTTP_SERVER_ERROR);
		}
	}

	protected void onPublish(AsyncResult<Record> resultHandler) {

		if (resultHandler.succeeded()) {
			
			record = resultHandler.result();
			log.info("{} microservice registered on ServiceDiscovery", resultHandler.result().getName());
		} else {

			log.error("Fail to registry microservice, fall out of process", resultHandler.cause());
			System.exit(CommonExitCode.REGISTRY_SERVICE_DISCOVERY_ERROR);
		}
	}

	protected Router router() {

		return RouterBuilder.create(vertx).build();
	}
}