/*
 *
 */
package org.jspare.spareco.common.discovery;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true)
public class RecordMetadata {

	private String name;
	private boolean healthCheck;
	private String healthPathCheck;

	public static RecordMetadata of(JsonObject json){
		
		return new RecordMetadata(json);
	}
	
	public RecordMetadata() {
	}

	public RecordMetadata(JsonObject json) {
		RecordMetadataConverter.fromJson(json, this);
	}

	public String getHealthPathCheck() {
		return healthPathCheck;
	}

	public String getName() {
		return name;
	}

	public boolean isHealthCheck() {
		return healthCheck;
	}

	public RecordMetadata setHealthCheck(boolean healthCheck) {
		this.healthCheck = healthCheck;
		return this;
	}

	public RecordMetadata setHealthPathCheck(String healthPathCheck) {
		this.healthPathCheck = healthPathCheck;
		return this;
	}

	public RecordMetadata setName(String name) {
		this.name = name;
		return this;
	}

	public JsonObject toJson() {
		JsonObject json = new JsonObject();
		RecordMetadataConverter.toJson(this, json);
		return json;
	}
}