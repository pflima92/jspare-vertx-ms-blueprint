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
package org.jspare.spareco.gateway.services;

import java.util.List;
import java.util.Optional;

import org.jspare.core.annotation.Inject;
import org.jspare.spareco.gateway.entity.User;
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

		user.setEncryptedPassword(Cipher.encrypt(user.getPassword()));
		return persistance.persistUser(user);
	}
}