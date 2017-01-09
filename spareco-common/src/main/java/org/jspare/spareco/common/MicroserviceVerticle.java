/*
 *
 */
package org.jspare.spareco.common;

import org.jspare.spareco.common.discovery.ConfigurationProvider;

import io.vertx.core.AsyncResult;
import io.vertx.core.json.JsonObject;
import io.vertx.serviceproxy.ProxyHelper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class MicroserviceVerticle extends BaseVerticle {

	@Override
	public void start() throws Exception {
		super.start();

		String name = getName();

		ProxyHelper.createProxy(ConfigurationProvider.class, vertx, ConfigurationProvider.SERVICE_NAME).getConfiguration(name,
				this::setConfiguration);
	}

	protected String getName() {

		return microserviceOptions.getName();
	}

	protected abstract void initialize();

	protected void setConfiguration(AsyncResult<JsonObject> resHandler) {

		if (resHandler.succeeded()) {
			log.debug("Received configurations {}", resHandler.result());
			context.config().mergeIn(resHandler.result());
		}

		log.debug("Using follow configurations {}", context.config().encodePrettily());
		initialize();
	}
}
