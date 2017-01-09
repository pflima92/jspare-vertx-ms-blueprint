/*
 *
 */
package org.jspare.spareco.gateway.persistance.jdbc;

import org.apache.commons.lang.StringUtils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class HibernateDialectProvider {

	public String dialectFromUrl(String url) {

		if (url.startsWith("jdbc:h2")) {
			return "org.hibernate.dialect.H2Dialect";
		} else if (url.startsWith("jdbc:oracle")) {
			return "org.hibernate.dialect.Oracle12cDialect";
		} else if (url.startsWith("jdbc:mysql")) {
			return "org.hibernate.dialect.MySQLDialect";
		} else if (url.startsWith("jdbc:mariadb")) {
			return "org.hibernate.dialect.MySQLDialect";
		} else if (url.startsWith("jdbc:postgresql")) {
			return "org.hibernate.dialect.PostgreSQLDialect";
		}
		return StringUtils.EMPTY;
	}
}
