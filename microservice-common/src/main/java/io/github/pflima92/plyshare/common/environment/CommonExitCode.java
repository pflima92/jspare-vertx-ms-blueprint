/*
 *
 */
package io.github.pflima92.plyshare.common.environment;

import org.jspare.vertx.bootstrap.ExitCode;

/**
 * The Interface CommonExitCode.
 *
 * Extended from {@link ExitCode}
 *
 * <p>
 * For codes based on Microservices errror one error 2x will be selected.
 * </p>
 */
public interface CommonExitCode extends ExitCode {

	/**
	 * The http server cannot be initialized.
	 * <p>
	 * 20 if Microservice HttpServer cannot be initialized.
	 * </p>
	 */
	int HTTP_SERVER_ERROR = 20;

	/**
	 * The registry service discovery error.
	 * <p>
	 * 21 if Microservice try register in Servie Discovery and failed.
	 * </p>
	 */
	int REGISTRY_SERVICE_DISCOVERY_ERROR = 21;

}
