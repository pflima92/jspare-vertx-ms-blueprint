/*
 *
 */
package org.jspare.spareco.gateway.manager;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.jspare.core.annotation.Inject;
import org.jspare.spareco.gateway.common.Buid;
import org.jspare.spareco.gateway.common.GatewayOptionsHolder;
import org.jspare.spareco.gateway.common.Version;
import org.jspare.spareco.gateway.model.Gateway;
import org.jspare.spareco.gateway.model.User;
import org.jspare.spareco.gateway.persistance.GatewayPersistance;
import org.jspare.spareco.gateway.services.UserService;

import io.vertx.core.Future;

public class GatewayManagerImpl implements GatewayManager {

	@Inject
	private GatewayPersistance persistance;

	@Inject
	private GatewayOptionsHolder gatewayOptionsHolder;

	@Inject
	private UserService userService;

	@Override
	public Future<Optional<Gateway>> getGateway() {

		return persistance.findGateway();
	}

	@Override
	public Future<Gateway> setup() {

		Future<Gateway> future = Future.future();
		return persistance.findGateway().compose(oGateway -> {

			if (oGateway.isPresent()) {

				future.complete(oGateway.get());
			} else {

				install(future);
			}
		}, future);
	}

	@Override
	public Future<Boolean> update(Gateway gateway) {

		return null;
	}

	protected void install(Future<Gateway> future) {
		// Install Gateway
		Gateway gateway = (Gateway) new Gateway().owner(gatewayOptionsHolder.getOptions().getOwner())
				.serialKey(gatewayOptionsHolder.getOptions().getSerialKey()).version(Version.currentVersion).build(Buid.getBuild())
				.privateKey(UUID.randomUUID().toString()).createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now());

		// TODO feature validate serialKey on hub, for now all of project are
		// open source
		persistance.persistGateway(gateway).setHandler(res -> {

			if (res.succeeded() && res.result() != null) {
				future.complete(gateway);
			} else {
				future.fail(res.cause());
			}
		}).compose(res -> {

			User admin = new User().name("Administrator").username("admin").password("admin");

			userService.save(admin);
		}, Future.succeededFuture());
	}
}