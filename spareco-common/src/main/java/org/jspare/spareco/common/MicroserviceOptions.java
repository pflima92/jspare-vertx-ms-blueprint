/*
 *
 */
package org.jspare.spareco.common;

import io.vertx.circuitbreaker.CircuitBreakerOptions;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.servicediscovery.ServiceDiscoveryOptions;

@DataObject(generateConverter = true)
public class MicroserviceOptions {

	private String name;
	private String healthPathCheck;
	private String address;
	private int port;
	private HttpServerOptions httpServerOptions;
	private CircuitBreakerOptions circuitBreakerOptions;
	private ServiceDiscoveryOptions serviceDiscoveryOptions;
	private JsonObject config;

	public MicroserviceOptions() {
		init();
	}

	public MicroserviceOptions(JsonObject json) {
		init();
		MicroserviceOptionsConverter.fromJson(json, this);
	}

	public String getAddress() {
		return address;
	}

	public CircuitBreakerOptions getCircuitBreakerOptions() {
		return circuitBreakerOptions;
	}

	public JsonObject getConfig() {
		return config;
	}

	public String getHealthPathCheck() {
		return healthPathCheck;
	}

	public HttpServerOptions getHttpServerOptions() {
		return httpServerOptions;
	}

	public String getName() {
		return name;
	}

	public int getPort() {
		return port;
	}

	public ServiceDiscoveryOptions getServiceDiscoveryOptions() {
		return serviceDiscoveryOptions;
	}

	public void init() {

		name = "unnamed";
		healthPathCheck = "/health";
		address = "localhost";
		port = 0;
		httpServerOptions = new HttpServerOptions();
		circuitBreakerOptions = new CircuitBreakerOptions();
		serviceDiscoveryOptions = new ServiceDiscoveryOptions();
		config = new JsonObject();
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setCircuitBreakerOptions(CircuitBreakerOptions circuitBreakerOptions) {
		this.circuitBreakerOptions = circuitBreakerOptions;
	}

	public void setConfig(JsonObject config) {
		this.config = config;
	}

	public void setHealthPathCheck(String healthPathCheck) {
		this.healthPathCheck = healthPathCheck;
	}

	public void setHttpServerOptions(HttpServerOptions httpServerOptions) {
		this.httpServerOptions = httpServerOptions;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setServiceDiscoveryOptions(ServiceDiscoveryOptions serviceDiscoveryOptions) {
		this.serviceDiscoveryOptions = serviceDiscoveryOptions;
	}

	public JsonObject toJson() {
		JsonObject json = new JsonObject();
		MicroserviceOptionsConverter.toJson(this, json);
		return json;
	}

	@Override
	public String toString() {
		return toJson().encodePrettily();
	}
}