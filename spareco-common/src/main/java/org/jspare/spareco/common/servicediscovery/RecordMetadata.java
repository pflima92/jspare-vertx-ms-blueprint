package org.jspare.spareco.common.servicediscovery;

import org.jspare.spareco.common.MicroserviceOptionsConverter;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter=true)
public class RecordMetadata {
	
	private String name;
	private boolean checkHealth;
	private String hearthbeatPath;

	public RecordMetadata() {
	}

	public RecordMetadata(JsonObject json) {
		RecordMetadataConverter.fromJson(json, this);
	}
	
	public JsonObject toJson(){
		JsonObject json = new JsonObject();
		RecordMetadataConverter.toJson(this, json);
		return json;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isCheckHealth() {
		return checkHealth;
	}

	public void setCheckHealth(boolean checkHealth) {
		this.checkHealth = checkHealth;
	}

	public String getHearthbeatPath() {
		return hearthbeatPath;
	}

	public void setHearthbeatPath(String hearthbeatPath) {
		this.hearthbeatPath = hearthbeatPath;
	}
}