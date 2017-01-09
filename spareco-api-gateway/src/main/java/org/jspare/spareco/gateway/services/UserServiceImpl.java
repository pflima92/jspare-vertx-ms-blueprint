/*
 *
 */
package org.jspare.spareco.gateway.services;

import java.util.List;
import java.util.Optional;

import org.jspare.core.annotation.Inject;
import org.jspare.spareco.gateway.model.User;
import org.jspare.spareco.gateway.persistance.GatewayPersistance;
import org.jspare.spareco.gateway.utils.Cipher;

import io.vertx.core.Future;

public class UserServiceImpl implements UserService {

	@Inject
	private GatewayPersistance persistance;

	@Override
	public Future<Optional<User>> getById(int id) {

		return persistance.findUserById(id);
	}

	@Override
	public Future<Optional<User>> getByUsernameAndPassword(String username, String password) {

		String encryptedPassword = Cipher.encrypt(password);
		return persistance.findUserByUsernameAndPassword(username, encryptedPassword);
	}

	@Override
	public Future<List<User>> list() {

		return persistance.listUsers();
	}

	@Override
	public Future<User> save(User user) {

		user.encryptedPassword(Cipher.encrypt(user.password()));
		return persistance.persistUser(user);
	}
}