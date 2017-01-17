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
package io.github.pflima92.plyshare.gateway.persistance.jdbc;

import static org.jspare.core.container.Environment.my;

import java.util.List;
import java.util.Optional;

import javax.persistence.Query;

import io.github.pflima92.plyshare.gateway.entity.Gateway;
import io.github.pflima92.plyshare.gateway.entity.User;
import io.github.pflima92.plyshare.gateway.persistance.GatewayPersistance;
import io.vertx.core.Future;

public class GatewayPersistanceJDBC implements GatewayPersistance {

	public GatewayPersistanceJDBC() {

		// Load JDBCProvider
		my(JDBCProvider.class);
	}

	@Override
	public Future<Optional<Gateway>> findGateway(String profile) {

		return JDBCExecutor.create().execute(session -> {

			return session.createNamedQuery("Gateway.findByProfile", Gateway.class).setParameter("profile", profile).getResultList()
					.stream().findFirst();
		});
	}

	@Override
	public Future<Optional<User>> findUserById(Integer id) {

		return JDBCExecutor.create().execute(session -> {

			Optional<User> oUser = Optional.ofNullable(session.find(User.class, id));
			return oUser;
		});
	}

	@Override
	@SuppressWarnings("unchecked")
	public Future<Optional<User>> findUserByUsernameAndPassword(String username, String password) {

		return JDBCExecutor.create().execute(session -> {

			Query query = session.createNamedQuery("User.findByLoginOrMailAndPassword", User.class);
			query.setParameter("username", username);
			query.setParameter("mail", username);
			query.setParameter("encryptedPassword", password);

			return query.getResultList().stream().findFirst();
		});
	}

	@Override
	@SuppressWarnings("unchecked")
	public Future<List<User>> listUsers() {

		return JDBCExecutor.create().execute(session -> {

			return session.createQuery(String.format("from %s t", User.class.getSimpleName())).getResultList();
		});
	}

	@Override
	public Future<Gateway> persistGateway(Gateway gateway) {

		return JDBCExecutor.create().execute(session -> {

			session.persist(gateway);
			return gateway;
		});
	}

	@Override
	public Future<User> persistUser(User user) {

		return JDBCExecutor.create().execute(session -> {

			session.persist(user);
			return user;
		});
	}
}