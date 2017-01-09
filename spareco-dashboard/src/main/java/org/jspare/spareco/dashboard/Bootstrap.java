/*
 *
 */
package org.jspare.spareco.dashboard;

import org.jspare.core.bootstrap.Application;
import org.jspare.vertx.bootstrap.VertxRunner;

public class Bootstrap extends VertxRunner {

	public static void main(String[] args) {

		Application.run(Bootstrap.class);
	}
}