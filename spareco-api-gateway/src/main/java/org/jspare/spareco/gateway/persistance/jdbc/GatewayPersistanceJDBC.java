/*
 *
 */
package org.jspare.spareco.gateway.persistance.jdbc;

import static org.jspare.core.container.Environment.my;

import java.util.List;
import java.util.Optional;

import javax.persistence.Query;

import org.jspare.spareco.gateway.model.Gateway;
import org.jspare.spareco.gateway.model.User;
import org.jspare.spareco.gateway.persistance.GatewayPersistance;

import io.vertx.core.Future;

public class GatewayPersistanceJDBC implements GatewayPersistance {

	public GatewayPersistanceJDBC() {

		// Load JDBCProvider
		my(JDBCProvider.class);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Future<Optional<Gateway>> findGateway() {

		return JDBCExecutor.create().execute(session -> {

			List<Gateway> result = session.createQuery(String.format("from %s t", Gateway.class.getSimpleName())).getResultList();
			return result.stream().findFirst();
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