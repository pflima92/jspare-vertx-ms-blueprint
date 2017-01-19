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
package io.github.pflima92.plyshare.gateway.manager;

import java.util.Optional;
import java.util.UUID;

import org.jspare.core.annotation.Inject;

import io.github.pflima92.plyshare.gateway.GatewayOptionsHolder;
import io.github.pflima92.plyshare.gateway.entity.Gateway;
import io.github.pflima92.plyshare.gateway.persistance.GatewayPersistance;
import io.vertx.core.Future;
import lombok.Getter;

public class GatewayManagerImpl implements GatewayManager {

	@Inject
	private GatewayPersistance persistance;

	@Inject
	private GatewayOptionsHolder gatewayOptionsHolder;
	
	@Getter
	private Gateway currentGateway;

	@Override
	public Future<Optional<Gateway>> findGateway(String profile) {

		return persistance.findGateway(profile);
	}

	private String getBuild() {

		return "DEV-MODE";
	}

	protected void install(Future<Gateway> future) {
		// Install Gateway
		Gateway gateway = (Gateway) new Gateway()
				.setProfile(gatewayOptionsHolder.getOptions().getProfile())
				.setOwner(gatewayOptionsHolder.getOptions().getOwner())
				.setSerialKey(gatewayOptionsHolder.getOptions().getSerialKey())
				.setVersion(VERSION)
				.setBuild(getBuild())
				.setPrivateKey(UUID.randomUUID().toString());

		// TODO feature validate serialKey on hub, for now all of project are
		// open source
		persistance.persistGateway(gateway).setHandler(res -> {

			if (res.succeeded() && res.result() != null) {
				this.currentGateway = gateway;
				future.complete(gateway);
			} else {
				future.fail(res.cause());
			}
		});
	}

	@Override
	public Future<Gateway> setup() {

		Future<Gateway> future = Future.future();
		return persistance.findGateway(gatewayOptionsHolder.getOptions().getProfile()).compose(oGateway -> {

			if (oGateway.isPresent()) {

				currentGateway = oGateway.get();
				future.complete(currentGateway);
			} else {

				install(future);
			}
		}, future);
	}

	@Override
	public Future<Boolean> update(Gateway gateway) {

		return null;
	}
}