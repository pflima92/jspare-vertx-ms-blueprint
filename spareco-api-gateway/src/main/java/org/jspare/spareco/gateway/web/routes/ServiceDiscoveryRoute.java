/*
 *
 */
package org.jspare.spareco.gateway.web.routes;

import org.jspare.core.annotation.Inject;
import org.jspare.spareco.common.discovery.ServiceDiscoveryHolder;
import org.jspare.spareco.gateway.web.handling.GatewayAPIHandler;
import org.jspare.vertx.web.annotation.handler.Handler;
import org.jspare.vertx.web.annotation.method.Get;
import org.jspare.vertx.web.annotation.subrouter.SubRouter;

@SubRouter("/api/servicediscovery")
public class ServiceDiscoveryRoute extends GatewayAPIHandler {

	@Inject
	private ServiceDiscoveryHolder discoveryHolder;

	@Get
	@Handler
	public void list() {

		discoveryHolder.getServiceDiscovery().getRecords(r -> true, res -> {

			if (res.succeeded()) {

				success(res.result());
			} else {

				internalServerError(res.cause());
			}
		});
	}
}