/*
 *
 */
package io.github.pflima92.plyshare.common.discovery;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;

public class ConfigurationProviderImpl implements ConfigurationProvider {

	@Override
	public ConfigurationProvider getConfiguration(String alias, Handler<AsyncResult<JsonObject>> resultHandler) {

		// TODO ConfigurationManager
		resultHandler.handle(Future.succeededFuture(new JsonObject().put("apiGatewayVersion", "dev")));
		return this;
	}
}