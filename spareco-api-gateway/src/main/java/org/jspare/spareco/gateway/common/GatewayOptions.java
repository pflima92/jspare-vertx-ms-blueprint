/*
 *
 */
package org.jspare.spareco.gateway.common;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.jspare.spareco.common.MicroserviceOptions;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true)
public class GatewayOptions extends MicroserviceOptions {

	private static final long DEFAULT_PERIOD_HEALTH_CHECK = TimeUnit.SECONDS.toMillis(60l);

	private static final int DEFAULT_DASHBOARD_PORT = 9009;

	private static final int DEFAULT_PROXY_PORT = 9080;

	private static final int DEFAULT_API_PORT = 9000;

	private static final String DEFAULT_OWNER = "unregistered";

	public static final String DEFAULT_LIB_PATH = "lib";
	
	protected String owner;
	protected String serialKey;

	protected GatewayDatabaseOptions gatewayDatabaseOptions;

	protected int apiPort;
	protected int proxyPort;
	protected int dashboardPort;

	protected long periodicHealthCheck;

	protected String libPath;

	public GatewayOptions() {

		super();
	}

	public GatewayOptions(JsonObject json) {
		this();
		GatewayOptionsConverter.fromJson(json, this);
	}

	protected void init() {

		super.init();
		
		owner = DEFAULT_OWNER;
		serialKey = UUID.randomUUID().toString();
		gatewayDatabaseOptions = new GatewayDatabaseOptions();
		apiPort = DEFAULT_API_PORT;
		proxyPort = DEFAULT_PROXY_PORT;
		dashboardPort = DEFAULT_DASHBOARD_PORT;
		httpServerOptions = new HttpServerOptions();
		periodicHealthCheck = DEFAULT_PERIOD_HEALTH_CHECK;
		libPath = DEFAULT_LIB_PATH;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getSerialKey() {
		return serialKey;
	}

	public void setSerialKey(String serialKey) {
		this.serialKey = serialKey;
	}

	public GatewayDatabaseOptions getGatewayDatabaseOptions() {
		return gatewayDatabaseOptions;
	}

	public void setGatewayDatabaseOptions(GatewayDatabaseOptions gatewayDatabaseOptions) {
		this.gatewayDatabaseOptions = gatewayDatabaseOptions;
	}

	public int getApiPort() {
		return apiPort;
	}

	public void setApiPort(int apiPort) {
		this.apiPort = apiPort;
	}

	public int getProxyPort() {
		return proxyPort;
	}

	public void setProxyPort(int proxyPort) {
		this.proxyPort = proxyPort;
	}

	public int getDashboardPort() {
		return dashboardPort;
	}

	public void setDashboardPort(int dashboardPort) {
		this.dashboardPort = dashboardPort;
	}

	public long getPeriodicHealthCheck() {
		return periodicHealthCheck;
	}

	public void setPeriodicHealthCheck(long periodicHealthCheck) {
		this.periodicHealthCheck = periodicHealthCheck;
	}

	public String getLibPath() {
		return libPath;
	}

	public void setLibPath(String libPath) {
		this.libPath = libPath;
	}
}