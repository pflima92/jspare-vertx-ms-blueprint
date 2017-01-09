/*
 *
 */
package org.jspare.spareco.gateway.persistance;

import java.util.List;
import java.util.Optional;

import org.jspare.core.annotation.Component;
import org.jspare.spareco.gateway.model.Gateway;
import org.jspare.spareco.gateway.model.User;

import io.vertx.core.Future;

@Component
public interface GatewayPersistance {

	Future<Optional<Gateway>> findGateway();

	Future<Optional<User>> findUserById(Integer id);

	Future<Optional<User>> findUserByUsernameAndPassword(String username, String password);

	Future<List<User>> listUsers();

	Future<Gateway> persistGateway(Gateway gateway);

	Future<User> persistUser(User user);
}