/*
 *
 */
package org.jspare.spareco.gateway.manager;

import java.util.Optional;

import org.jspare.core.annotation.Component;
import org.jspare.spareco.gateway.model.Gateway;

import io.vertx.core.Future;

@Component
public interface GatewayManager {

	Future<Optional<Gateway>> getGateway();

	Future<Gateway> setup();

	Future<Boolean> update(Gateway gateway);
}