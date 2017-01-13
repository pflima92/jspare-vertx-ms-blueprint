/*
 * Copyright 2016 JSpare.org.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.jspare.spareco.gateway.manager;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.jspare.core.annotation.Inject;
import org.jspare.spareco.gateway.common.Buid;
import org.jspare.spareco.gateway.common.GatewayOptionsHolder;
import org.jspare.spareco.gateway.common.Version;
import org.jspare.spareco.gateway.entity.Gateway;
import org.jspare.spareco.gateway.entity.User;
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
	public Future<Optional<Gateway>> getGateway(String profile) {

		return persistance.findGateway(profile);
	}

	@Override
	public Future<Gateway> setup() {

		Future<Gateway> future = Future.future();
		return persistance.findGateway(gatewayOptionsHolder.getOptions().getProfile()).compose(oGateway -> {

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
		Gateway gateway = (Gateway) new Gateway().profile(gatewayOptionsHolder.getOptions().getProfile())
				.owner(gatewayOptionsHolder.getOptions().getOwner()).serialKey(gatewayOptionsHolder.getOptions().getSerialKey())
				.version(Version.currentVersion).build(Buid.getBuild()).privateKey(UUID.randomUUID().toString())
				.createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now());

		// TODO feature validate serialKey on hub, for now all of project are
		// open source
		persistance.persistGateway(gateway).setHandler(res -> {

			if (res.succeeded() && res.result() != null) {
				future.complete(gateway);
			} else {
				future.fail(res.cause());
			}
		}).compose(res -> {

			User admin = new User().setName("Administrator").setUsername("admin").setPassword("admin");

			userService.save(admin);
		}, Future.succeededFuture());
	}
}