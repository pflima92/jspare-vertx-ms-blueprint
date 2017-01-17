/*
 *
 */
package io.github.pflima92.plyshare.gateway.persistance.jdbc;

import java.util.List;

import javax.persistence.spi.PersistenceUnitInfo;

/**
 * The Interface DefaultPersistanceUnitInfo.
 */
public interface SmartPersistanceUnitInfo extends PersistenceUnitInfo {

	/**
	 * Adds the annotated class name.
	 *
	 * @param clazz
	 *            the clazz
	 */
	void addAnnotatedClassName(Class<?> clazz);

	/**
	 * Adds the annotated class name.
	 *
	 * @param className
	 *            the class name
	 */
	void addAnnotatedClassName(String className);

	/**
	 * Gets the managed packages.
	 *
	 * @return the managed packages
	 */
	List<String> getManagedPackages();

	/**
	 * Sets the persistence provider package name.
	 *
	 * @param persistenceProviderPackageName
	 *            the new persistence provider package name
	 */
	void setPersistenceProviderPackageName(String persistenceProviderPackageName);
}
