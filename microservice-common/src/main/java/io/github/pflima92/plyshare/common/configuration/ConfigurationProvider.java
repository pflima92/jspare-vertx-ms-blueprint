/*
 *
 */
package io.github.pflima92.plyshare.common.configuration;

import org.jspare.core.annotation.Component;
import org.jspare.vertx.annotation.RegisterProxyService;

import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;

@ProxyGen
@VertxGen
@RegisterProxyService(ConfigurationProvider.SERVICE_NAME)
@Component
public interface ConfigurationProvider {

	/** The service name for Proxy stub. */
	String SERVICE_NAME = "services.microservice.configuration";

	@Fluent
	ConfigurationProvider getConfiguration(String name, Handler<AsyncResult<JsonObject>> resultHandler);
}