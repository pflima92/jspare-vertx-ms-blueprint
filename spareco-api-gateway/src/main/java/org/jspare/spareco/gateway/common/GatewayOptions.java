/*
 * Copyright 2016 JSpare.org.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
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

	private static final String DEFAULT_PROFILE = "default";

	private static final long DEFAULT_PERIOD_HEALTH_CHECK = TimeUnit.SECONDS.toMillis(60l);

	private static final int DEFAULT_DASHBOARD_PORT = 9009;

	private static final int DEFAULT_PROXY_PORT = 9080;

	private static final int DEFAULT_API_PORT = 9000;

	private static final String DEFAULT_OWNER = "unregistered";

	public static final String DEFAULT_LIB_PATH = "lib";

	protected String profile;
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

	public int getApiPort() {
		return apiPort;
	}

	public int getDashboardPort() {
		return dashboardPort;
	}

	public GatewayDatabaseOptions getGatewayDatabaseOptions() {
		return gatewayDatabaseOptions;
	}

	public String getLibPath() {
		return libPath;
	}

	public String getOwner() {
		return owner;
	}

	public long getPeriodicHealthCheck() {
		return periodicHealthCheck;
	}

	/**
	 * @return the profile
	 */
	public String getProfile() {
		return profile;
	}

	public int getProxyPort() {
		return proxyPort;
	}

	public String getSerialKey() {
		return serialKey;
	}

	public void setApiPort(int apiPort) {
		this.apiPort = apiPort;
	}

	public void setDashboardPort(int dashboardPort) {
		this.dashboardPort = dashboardPort;
	}

	public void setGatewayDatabaseOptions(GatewayDatabaseOptions gatewayDatabaseOptions) {
		this.gatewayDatabaseOptions = gatewayDatabaseOptions;
	}

	public void setLibPath(String libPath) {
		this.libPath = libPath;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public void setPeriodicHealthCheck(long periodicHealthCheck) {
		this.periodicHealthCheck = periodicHealthCheck;
	}

	/**
	 * @param profile
	 *            the profile to set
	 */
	public void setProfile(String profile) {
		this.profile = profile;
	}

	public void setProxyPort(int proxyPort) {
		this.proxyPort = proxyPort;
	}

	public void setSerialKey(String serialKey) {
		this.serialKey = serialKey;
	}

	@Override
	protected void init() {

		super.init();

		profile = DEFAULT_PROFILE;
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
}