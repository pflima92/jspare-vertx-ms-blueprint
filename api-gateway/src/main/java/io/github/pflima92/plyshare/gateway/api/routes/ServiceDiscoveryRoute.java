/*
 *
 */
package io.github.pflima92.plyshare.gateway.api.routes;

import org.jspare.core.annotation.Inject;
import org.jspare.vertx.web.annotation.handler.Handler;
import org.jspare.vertx.web.annotation.method.Get;
import org.jspare.vertx.web.annotation.subrouter.SubRouter;

import io.github.pflima92.plyshare.common.discovery.ServiceDiscoveryHolder;
import io.github.pflima92.plyshare.common.web.RestAPIHandler;

@SubRouter("/api/servicediscovery")
public class ServiceDiscoveryRoute extends RestAPIHandler {

	@Inject
	private ServiceDiscoveryHolder discoveryHolder;

	@Get
	@Handler
	public void list() {

		discoveryHolder.getServiceDiscovery().getRecords(r -> true, this::handler);
	}
}