/*
 *
 */
package io.github.pflima92.plyshare.common;

import io.vertx.circuitbreaker.CircuitBreakerOptions;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.servicediscovery.ServiceDiscoveryOptions;

@DataObject(generateConverter = true)
public class MicroserviceOptions {

	public static final int DEFAULT_PORT = 0;

	public static final String DEFAULT_LOCALHOST = "localhost";

	public static final String DEFAULT_NAME = "unnamed";

	public static final String DEFAULT_HEALTH_PATH_CHECK = "/health";

	protected String name;
	protected String address;
	protected int port;
	protected boolean healthCheck;
	protected String healthPathCheck;
	protected HttpServerOptions httpServerOptions;
	protected CircuitBreakerOptions circuitBreakerOptions;
	protected ServiceDiscoveryOptions serviceDiscoveryOptions;
	protected JsonObject config;

	public MicroserviceOptions() {
		init();
	}

	public MicroserviceOptions(JsonObject json) {
		this();
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

	protected void init() {

		name = DEFAULT_NAME;
		healthPathCheck = DEFAULT_HEALTH_PATH_CHECK;
		healthCheck = true;
		address = DEFAULT_LOCALHOST;
		port = DEFAULT_PORT;
		httpServerOptions = new HttpServerOptions();
		circuitBreakerOptions = new CircuitBreakerOptions();
		serviceDiscoveryOptions = new ServiceDiscoveryOptions();
		config = new JsonObject();
	}

	public boolean isHealthCheck() {
		return healthCheck;
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

	public void setHealthCheck(boolean healthCheck) {
		this.healthCheck = healthCheck;
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