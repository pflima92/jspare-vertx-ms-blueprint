/*
 *
 */
package io.github.pflima92.plyshare.common.discovery;

import java.util.Objects;

import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.oauth2.OAuth2Auth;
import io.vertx.servicediscovery.Record;
import io.vertx.servicediscovery.ServiceDiscovery;
import io.vertx.servicediscovery.spi.ServiceType;

/**
 * {@link ServiceType} for OAuth2 provider services.
 *
 * @author Eric Zhao
 */
@VertxGen
public interface OAuth2Service extends ServiceType {

	/**
	 * Name of the type.
	 */
	String TYPE = "auth-provider-oauth2";

	public static OAuth2Service create() {

		return new OAuth2ServiceImpl();
	}

	static Record createRecord(String name, JsonObject config, JsonObject metadata) {
		Objects.requireNonNull(name);

		JsonObject meta;
		if (metadata == null) {
			meta = new JsonObject();
		} else {
			meta = metadata.copy();
		}

		return new Record().setType(TYPE).setName(name).setMetadata(meta).setLocation(config);
	}

	static void getOAuth2Provider(ServiceDiscovery discovery, JsonObject filter, Handler<AsyncResult<OAuth2Auth>> resultHandler) {
		discovery.getRecord(filter, ar -> {
			if (ar.failed() || ar.result() == null) {
				resultHandler.handle(Future.failedFuture("No matching record"));
			} else {
				resultHandler.handle(Future.succeededFuture(discovery.getReference(ar.result()).get()));
			}
		});
	}

	static void getOAuth2Provider(ServiceDiscovery discovery, JsonObject filter, JsonObject consumerConfiguration,
			Handler<AsyncResult<OAuth2Auth>> resultHandler) {
		discovery.getRecord(filter, ar -> {
			if (ar.failed() || ar.result() == null) {
				resultHandler.handle(Future.failedFuture("No matching record"));
			} else {
				resultHandler
						.handle(Future.succeededFuture(discovery.getReferenceWithConfiguration(ar.result(), consumerConfiguration).get()));
			}
		});
	}

}
