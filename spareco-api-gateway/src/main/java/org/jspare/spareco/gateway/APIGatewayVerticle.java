/*
 *
 */
package org.jspare.spareco.gateway;

import static org.jspare.core.container.Environment.my;
import static org.jspare.core.container.Environment.registryComponent;
import static org.jspare.core.container.Environment.registryResource;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.jspare.spareco.common.BaseVerticle;
import org.jspare.spareco.common.discovery.ConfigurationProvider;
import org.jspare.spareco.gateway.common.GatewayOptions;
import org.jspare.spareco.gateway.common.GatewayOptionsHolder;
import org.jspare.spareco.gateway.library.LibrarySupport;
import org.jspare.spareco.gateway.manager.GatewayManager;
import org.jspare.spareco.gateway.model.Gateway;
import org.jspare.spareco.gateway.persistance.GatewayPersistance;
import org.jspare.spareco.gateway.utils.Stopwatch;
import org.jspare.spareco.gateway.web.ProxyAPIVerticle;
import org.jspare.spareco.gateway.web.ServicesAPIVerticle;

import io.vertx.core.AsyncResult;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class APIGatewayVerticle extends BaseVerticle {

	@Override
	@SneakyThrows({ ClassNotFoundException.class })
	public void start() throws Exception {

		super.start();

		GatewayOptions gatewayOptions = new GatewayOptions(config());
		registryResource(new GatewayOptionsHolder(gatewayOptions));

		my(LibrarySupport.class).load();

		String persistanceClass = gatewayOptions.getGatewayDatabaseOptions().getDatabaseClass();
		log.debug("Initialize GatewayPersistance with: ", persistanceClass);

		Stopwatch stopwatch = Stopwatch.create().start("Initialized Database");
		registryComponent(Class.forName(persistanceClass));

		my(GatewayPersistance.class);

		stopwatch.stop("Database initialized").print();

		my(GatewayManager.class).setup().setHandler(this::onPrepare);
	}

	protected void onPrepare(AsyncResult<Gateway> resultHandler) {

		if (resultHandler.succeeded()) {

			log.debug("API Gateway Core initialized with success");
			log.debug(ReflectionToStringBuilder.toString(resultHandler.result(), ToStringStyle.MULTI_LINE_STYLE));

			// Registry API Gateway verticles
			deployVerticle(ProxyAPIVerticle.class);
			deployVerticle(ServicesAPIVerticle.class);

			// Registry Proxy Services
			addProxyService(ConfigurationProvider.class);

		} else {

			log.error("Fail to load API Gateway", resultHandler.cause());
			System.exit(1);
		}
	}
}