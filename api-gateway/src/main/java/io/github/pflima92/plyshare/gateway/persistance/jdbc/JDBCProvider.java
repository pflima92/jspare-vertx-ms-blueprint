/*
 *
 */
package io.github.pflima92.plyshare.gateway.persistance.jdbc;

import static org.jspare.vertx.builder.ClasspathScannerUtils.ALL_SCAN_QUOTE;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.spi.PersistenceProvider;
import javax.persistence.spi.PersistenceUnitTransactionType;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.jspare.core.annotation.After;
import org.jspare.core.annotation.Inject;
import org.jspare.core.annotation.Resource;
import org.jspare.vertx.builder.ClasspathScannerUtils;

import io.github.lukehutch.fastclasspathscanner.matchprocessor.ClassAnnotationMatchProcessor;
import io.github.pflima92.plyshare.gateway.common.GatewayDatabaseOptions;
import io.github.pflima92.plyshare.gateway.common.GatewayOptionsHolder;
import lombok.Setter;

@Resource
public class JDBCProvider {

	private static final int NUMBER_CLASSPATH_SCANNER_THREADS = 3;

	private static final String JSPARE_GATEWAY_DATASOURCE = "jspareGateway";

	@Inject
	private GatewayOptionsHolder gatewayOptionsHolder;

	@Setter
	private PersistenceProvider persistenceProvider;

	@Setter
	private EntityManagerFactory entityManagerFactory;

	public EntityManagerFactory build() {

		Properties properties = createProperties();

		DefaultPersistenceUnitInfoImpl persistenceUnitInfo = new DefaultPersistenceUnitInfoImpl(JSPARE_GATEWAY_DATASOURCE);
		persistenceUnitInfo.setProperties(properties);

		// Using RESOURCE_LOCAL for manage transactions on DAO side.
		persistenceUnitInfo.setTransactionType(PersistenceUnitTransactionType.RESOURCE_LOCAL);

		// Add all entities to configuration
		ClassAnnotationMatchProcessor processor = (c) -> persistenceUnitInfo.addAnnotatedClassName(c);
		ClasspathScannerUtils.scanner(ALL_SCAN_QUOTE).matchClassesWithAnnotation(Entity.class, processor)
				.scan(NUMBER_CLASSPATH_SCANNER_THREADS);

		Map<String, Object> configuration = new HashMap<>();
		properties.forEach((k, v) -> configuration.put((String) k, v));

		EntityManagerFactory entityManagerFactory = persistenceProvider.createContainerEntityManagerFactory(persistenceUnitInfo,
				configuration);
		return entityManagerFactory;
	}

	public EntityManager getEntityManager() {

		return entityManagerFactory.createEntityManager();
	}

	protected Properties createProperties() {

		GatewayDatabaseOptions options = gatewayOptionsHolder.getOptions().getGatewayDatabaseOptions();

		Properties properties = new Properties();
		properties.setProperty("hibernate.connection.url", options.getUrl());
		properties.setProperty("hibernate.connection.driver_class", options.getDriverClass());
		properties.setProperty("hibernate.hbm2ddl.auto", "update");
		properties.setProperty("hibernate.dialect", HibernateDialectProvider.dialectFromUrl(options.getUrl()));
		properties.setProperty("hibernate.show_sql", String.valueOf(options.getShowCommands()));
		properties.setProperty("hibernate.connection.username", options.getUsername());
		properties.setProperty("hibernate.connection.password", options.getPassword());
		properties.setProperty("hibernate.c3p0.max_size", String.valueOf(options.getMaxPool()));
		properties.setProperty("hibernate.c3p0.min_size", String.valueOf(options.getMinPool()));
		properties.setProperty("hibernate.c3p0.max_statements", String.valueOf(options.getMaxStatementsPerConnection()));
		properties.setProperty("hibernate.c3p0.idle_test_period", String.valueOf(options.getMaxIdleTime()));
		properties.setProperty("hibernate.c3p0.testConnectionOnCheckout", String.valueOf(options.getTestConnection()));
		properties.setProperty("hibernate.temp.use_jdbc_metadata_defaults", "false");
		return properties;
	}

	@After
	private void setup() {

		persistenceProvider = new HibernatePersistenceProvider();
		setEntityManagerFactory(build());
	}
}
