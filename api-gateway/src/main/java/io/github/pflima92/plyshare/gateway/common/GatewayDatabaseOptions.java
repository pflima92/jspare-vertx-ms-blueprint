/*
 *
 */
package io.github.pflima92.plyshare.gateway.common;

import org.apache.commons.lang.StringUtils;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true)
public class GatewayDatabaseOptions {

	public static final int DEFAULT_MAX_STATEMENTS_PER_CONNECTION = 0;
	public static final int DEFAULT_MAX_IDLE_TIME = 0;
	public static final int DEFAULT_INITIAL_POOL = 1;
	public static final int DEFAULT_MAX_POOL = 15;
	public static final int DEFAULT_MIN_POOL = 1;
	public static final int DEFAULT_EMBEDDED_PORT = 9092;
	public static final String DEFAULT_H2_DRIVER = "org.h2.Driver";
	public static final String DETAULT_URL = "jdbc:h2:file:C:\\aplic\\gateway\\database;";
	public static final String DEFAULT_DATABASE_CLASS = "io.github.pflima92.plyshare.gateway.persistance.jdbc.GatewayPersistanceJDBC";

	private String databaseClass;
	private String url;
	private String driverClass;
	private String username;
	private String password;
	private Integer embeddedPort;
	private Integer maxPool;
	private Integer minPool;
	private Integer initialPool;
	private Integer maxIdleTime;
	private Integer maxStatementsPerConnection;
	private Boolean showCommands;
	private Boolean testConnection;

	public GatewayDatabaseOptions() {
		init();
	}

	public GatewayDatabaseOptions(GatewayDatabaseOptions other) {
		this();
		databaseClass = other.getDatabaseClass();
		url = other.getUrl();
		driverClass = other.getDriverClass();
		username = other.getUsername();
		password = other.getPassword();
		embeddedPort = other.getEmbeddedPort();
		maxPool = other.getMaxPool();
		minPool = other.getMinPool();
		initialPool = other.getInitialPool();
		maxIdleTime = other.getMaxIdleTime();
		maxStatementsPerConnection = other.getMaxStatementsPerConnection();
		showCommands = other.getShowCommands();
		testConnection = other.getTestConnection();
	}

	public GatewayDatabaseOptions(JsonObject json) {
		this();
		GatewayDatabaseOptionsConverter.fromJson(json, this);
	}

	public String getDatabaseClass() {
		return databaseClass;
	}

	public String getDriverClass() {
		return driverClass;
	}

	public Integer getEmbeddedPort() {
		return embeddedPort;
	}

	public Integer getInitialPool() {
		return initialPool;
	}

	public Integer getMaxIdleTime() {
		return maxIdleTime;
	}

	public Integer getMaxPool() {
		return maxPool;
	}

	public Integer getMaxStatementsPerConnection() {
		return maxStatementsPerConnection;
	}

	public Integer getMinPool() {
		return minPool;
	}

	public String getPassword() {
		return password;
	}

	public Boolean getShowCommands() {
		return showCommands;
	}

	public Boolean getTestConnection() {
		return testConnection;
	}

	public String getUrl() {
		return url;
	}

	public String getUsername() {
		return username;
	}

	public void setDatabaseClass(String databaseClass) {
		this.databaseClass = databaseClass;
	}

	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}

	public void setEmbeddedPort(Integer embeddedPort) {
		this.embeddedPort = embeddedPort;
	}

	public void setInitialPool(Integer initialPool) {
		this.initialPool = initialPool;
	}

	public void setMaxIdleTime(Integer maxIdleTime) {
		this.maxIdleTime = maxIdleTime;
	}

	public void setMaxPool(Integer maxPool) {
		this.maxPool = maxPool;
	}

	public void setMaxStatementsPerConnection(Integer maxStatementsPerConnection) {
		this.maxStatementsPerConnection = maxStatementsPerConnection;
	}

	public void setMinPool(Integer minPool) {
		this.minPool = minPool;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setShowCommands(Boolean showCommands) {
		this.showCommands = showCommands;
	}

	public void setTestConnection(Boolean testConnection) {
		this.testConnection = testConnection;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	private void init() {

		databaseClass = DEFAULT_DATABASE_CLASS;
		url = DETAULT_URL;
		driverClass = DEFAULT_H2_DRIVER;
		username = StringUtils.EMPTY;
		password = StringUtils.EMPTY;
		embeddedPort = DEFAULT_EMBEDDED_PORT;
		maxPool = DEFAULT_MAX_POOL;
		minPool = DEFAULT_MIN_POOL;
		initialPool = DEFAULT_INITIAL_POOL;
		maxIdleTime = DEFAULT_MAX_IDLE_TIME;
		maxStatementsPerConnection = DEFAULT_MAX_STATEMENTS_PER_CONNECTION;
		showCommands = false;
		testConnection = true;
	}
}