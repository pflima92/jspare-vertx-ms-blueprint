/*
 *
 */

package org.jspare.spareco.common;

import io.vertx.core.json.JsonObject;

/**
 * Converter for {@link org.jspare.spareco.common.MicroserviceOptions}.
 *
 * NOTE: This class has been automatically generated from the
 * {@link org.jspare.spareco.common.MicroserviceOptions} original class using
 * Vert.x codegen.
 */
public class MicroserviceOptionsConverter {

	public static void fromJson(JsonObject json, MicroserviceOptions obj) {
		if (json.getValue("address") instanceof String) {
			obj.setAddress((String) json.getValue("address"));
		}
		if (json.getValue("circuitBreakerOptions") instanceof JsonObject) {
			obj.setCircuitBreakerOptions(
					new io.vertx.circuitbreaker.CircuitBreakerOptions((JsonObject) json.getValue("circuitBreakerOptions")));
		}
		if (json.getValue("config") instanceof JsonObject) {
			obj.setConfig(((JsonObject) json.getValue("config")).copy());
		}
		if (json.getValue("healthPathCheck") instanceof String) {
			obj.setHealthPathCheck((String) json.getValue("healthPathCheck"));
		}
		if (json.getValue("httpServerOptions") instanceof JsonObject) {
			obj.setHttpServerOptions(new io.vertx.core.http.HttpServerOptions((JsonObject) json.getValue("httpServerOptions")));
		}
		if (json.getValue("name") instanceof String) {
			obj.setName((String) json.getValue("name"));
		}
		if (json.getValue("port") instanceof Number) {
			obj.setPort(((Number) json.getValue("port")).intValue());
		}
		if (json.getValue("serviceDiscoveryOptions") instanceof JsonObject) {
			obj.setServiceDiscoveryOptions(
					new io.vertx.servicediscovery.ServiceDiscoveryOptions((JsonObject) json.getValue("serviceDiscoveryOptions")));
		}
	}

	public static void toJson(MicroserviceOptions obj, JsonObject json) {
		if (obj.getAddress() != null) {
			json.put("address", obj.getAddress());
		}
		if (obj.getCircuitBreakerOptions() != null) {
			json.put("circuitBreakerOptions", obj.getCircuitBreakerOptions().toJson());
		}
		if (obj.getConfig() != null) {
			json.put("config", obj.getConfig());
		}
		if (obj.getHealthPathCheck() != null) {
			json.put("healthPathCheck", obj.getHealthPathCheck());
		}
		if (obj.getName() != null) {
			json.put("name", obj.getName());
		}
		json.put("port", obj.getPort());
		if (obj.getServiceDiscoveryOptions() != null) {
			json.put("serviceDiscoveryOptions", obj.getServiceDiscoveryOptions().toJson());
		}
	}
}