/*
 * 
 */
package io.github.pflima92.plyshare.microservice.account.api;

import org.jspare.vertx.web.builder.RouterBuilder;

import io.github.pflima92.plyshare.common.RestAPIVerticle;
import io.vertx.ext.web.Router;

public class SampleAPIVerticle extends RestAPIVerticle {

	@Override
	protected Router router() {

		return RouterBuilder.create(vertx)
				.scanClasspath(true)
				.build();
	}
}