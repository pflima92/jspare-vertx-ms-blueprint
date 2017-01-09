/*
 *
 */
package org.jspare.spareco.gateway.common;

import java.util.UUID;

import io.vertx.circuitbreaker.CircuitBreakerOptions;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.servicediscovery.ServiceDiscoveryOptions;

@DataObject(generateConverter = true)
public class GatewayOptions {

	private static final String DEFAULT_LIB_PATH = "lib";
	private String owner;
	private String serialKey;

	private GatewayDatabaseOptions gatewayDatabaseOptions;

	private String httpAddress;
	private int httpApiPort;
	private int httpProxyPort;
	private int httpDashboardPort;
	private HttpServerOptions httpServerOptions;

	private CircuitBreakerOptions circuitBreakerOptions;
	private ServiceDiscoveryOptions serviceDiscoveryOptions;

	private String libPath;

	public GatewayOptions() {

		init();
	}

	public GatewayOptions(JsonObject json) {
		this();
		GatewayOptionsConverter.fromJson(json, this);
	}

	public CircuitBreakerOptions getCircuitBreakerOptions() {
		return circuitBreakerOptions;
	}

	public GatewayDatabaseOptions getGatewayDatabaseOptions() {
		return gatewayDatabaseOptions;
	}

	public String getHttpAddress() {
		return httpAddress;
	}

	public int getHttpApiPort() {
		return httpApiPort;
	}

	public int getHttpDashboardPort() {
		return httpDashboardPort;
	}

	public int getHttpProxyPort() {
		return httpProxyPort;
	}

	public HttpServerOptions getHttpServerOptions() {
		return httpServerOptions;
	}

	public String getLibPath() {
		return libPath;
	}

	public String getOwner() {
		return owner;
	}

	public String getSerialKey() {
		return serialKey;
	}

	public ServiceDiscoveryOptions getServiceDiscoveryOptions() {
		return serviceDiscoveryOptions;
	}

	public void setCircuitBreakerOptions(CircuitBreakerOptions circuitBreakerOptions) {
		this.circuitBreakerOptions = circuitBreakerOptions;
	}

	public void setGatewayDatabaseOptions(GatewayDatabaseOptions gatewayDatabaseOptions) {
		this.gatewayDatabaseOptions = gatewayDatabaseOptions;
	}

	public void setHttpAddress(String httpAddress) {
		this.httpAddress = httpAddress;
	}

	public void setHttpApiPort(int httpApiPort) {
		this.httpApiPort = httpApiPort;
	}

	public void setHttpDashboardPort(int httpDashboardPort) {
		this.httpDashboardPort = httpDashboardPort;
	}

	public void setHttpProxyPort(int httpProxyPort) {
		this.httpProxyPort = httpProxyPort;
	}

	public void setHttpServerOptions(HttpServerOptions httpServerOptions) {
		this.httpServerOptions = httpServerOptions;
	}

	public void setLibPath(String libPath) {
		this.libPath = libPath;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public void setSerialKey(String serialKey) {
		this.serialKey = serialKey;
	}

	public void setServiceDiscoveryOptions(ServiceDiscoveryOptions serviceDiscoveryOptions) {
		this.serviceDiscoveryOptions = serviceDiscoveryOptions;
	}

	private void init() {

		owner = "unregistered";
		serialKey = UUID.randomUUID().toString();
		gatewayDatabaseOptions = new GatewayDatabaseOptions();
		httpAddress = "localhost";
		httpApiPort = 9000;
		httpProxyPort = 9080;
		httpDashboardPort = 9009;
		httpServerOptions = new HttpServerOptions();

		libPath = DEFAULT_LIB_PATH;
	}
}