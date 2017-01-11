/*
 *
 */
package org.jspare.spareco.common;

import static org.jspare.core.container.Environment.registryResource;

import org.jspare.spareco.common.circuitbreaker.CircuitBreakerHolder;
import org.jspare.spareco.common.servicediscovery.ServiceDiscoveryHolder;
import org.jspare.vertx.builder.ProxyServiceBuilder;
import org.jspare.vertx.utils.VerticleInitializer;

import io.vertx.circuitbreaker.CircuitBreaker;
import io.vertx.circuitbreaker.CircuitBreakerOptions;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Verticle;
import io.vertx.servicediscovery.ServiceDiscovery;
import io.vertx.servicediscovery.ServiceDiscoveryOptions;
import lombok.extern.slf4j.Slf4j;

/**
 * The Verticle BaseMicroservice.
 *
 * Responsible to Bootstrap the Application
 *
 * Registering common resources for use with Microservice for e.g:
 * CircuitBreaker, ServiceDiscovery, MessageServices
 *
 */
@Slf4j
public abstract class BaseVerticle extends AbstractVerticle {

	protected MicroserviceOptions microserviceOptions;

	@Override
	public void start() throws Exception {

		log.debug(
				"We are using sl4j with logger of this application, for change any configuration use file: simplelogger.properties instead of http://www.slf4j.org/api/org/slf4j/impl/SimpleLogger.html");

		if (!config().isEmpty()) {

			log.debug("Using follow custom configurations");
			log.debug(config().encodePrettily());
		} else {

			log.debug("Using default configurations");
		}

		// Hold MicroserviceOptions
		microserviceOptions = new MicroserviceOptions(config());
		registryResource(new MicroserviceOptionsHolder(microserviceOptions));

		// Registry resources on Container
		registryResource(ServiceDiscoveryHolder.create(createServiceDiscovery()));
		registryResource(CircuitBreakerHolder.create(createCircuitBreaker()));
	}

	protected void addProxyService(Class<?> proxyServiceClass) {
		ProxyServiceBuilder.create(vertx).addProxyService(proxyServiceClass).build();
	}

	protected CircuitBreaker createCircuitBreaker() {

		return CircuitBreaker.create("circuit-breaker", vertx, new CircuitBreakerOptions(microserviceOptions.getCircuitBreakerOptions()));
	}

	protected ServiceDiscovery createServiceDiscovery() {

		return ServiceDiscovery.create(vertx, new ServiceDiscoveryOptions(microserviceOptions.getServiceDiscoveryOptions()));
	}

	protected DeploymentOptions defaultDeploymentOptions() {

		return new DeploymentOptions().setConfig(config());
	}

	protected void deployVerticle(Class<? extends Verticle> verticleClass) {
		
		deployVerticle(verticleClass, defaultDeploymentOptions());
	}
	
	protected void deployVerticle(Class<? extends Verticle> verticleClass, DeploymentOptions deploymentOptions) {
		vertx.deployVerticle(VerticleInitializer.initialize(verticleClass), deploymentOptions);
	}
}