/*
 *
 */
package org.jspare.spareco.gateway.persistance.jdbc;

import static org.jspare.core.container.Environment.my;

import javax.persistence.EntityManager;
import javax.transaction.Transaction;

import org.jspare.vertx.concurrent.FutureSupplier;

import io.vertx.core.Future;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class JDBCExecutor {

	@FunctionalInterface
	public interface PerformQuery<T> {
		T doIt(EntityManager session);
	}

	/**
	 * Creates the JDBC Executor instance
	 *
	 * By default the {@link EntityManager} are initialized from
	 * {@link JDBCProvider}.
	 *
	 * @return the JDBC executor
	 */
	public static JDBCExecutor create() {

		return new JDBCExecutor();
	}

	public static JDBCExecutor create(EntityManager entityManager) {

		return new JDBCExecutor(entityManager);
	}

	private final EntityManager entityManager;

	@Getter
	@Setter
	private boolean autoCommit = true;

	@Getter
	@Setter
	private boolean closeSession = true;

	private JDBCExecutor() {

		entityManager = my(JDBCProvider.class).getEntityManager();
	}

	/**
	 * Execute one session of {@link EntityManager}
	 *
	 * By default the {@link Transaction} are initialized if is not active. Same
	 * for close, if you
	 *
	 * @param <T>
	 *            the generic type
	 * @param perform
	 *            the perform
	 * @return the future
	 */
	public <T> Future<T> execute(PerformQuery<T> perform) {

		return FutureSupplier.supply(() -> {

			try {

				if (!entityManager.getTransaction().isActive()) {

					entityManager.getTransaction().begin();
				}

				return perform.doIt(entityManager);
			} finally {

				if (entityManager.getTransaction().isActive()) {

					entityManager.getTransaction().commit();
				}

				if (entityManager.isOpen() && closeSession) {

					entityManager.close();
				}
			}
		});
	}
}
