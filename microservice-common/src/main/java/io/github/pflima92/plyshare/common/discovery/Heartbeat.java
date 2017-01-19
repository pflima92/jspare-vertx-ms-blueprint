/*
 *
 */
package io.github.pflima92.plyshare.common.discovery;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import io.vertx.servicediscovery.Record;

@DataObject(generateConverter = true)
public class Heartbeat {

	private Record record;
	private boolean healthy;
	
	public Heartbeat() {
	}

	public Heartbeat(JsonObject json) {
		HeartbeatConverter.fromJson(json, this);
	}

	public Record getRecord() {
		return record;
	}


	public boolean isHealthy() {
		return healthy;
	}

	public Heartbeat setHealthy(boolean healthy) {
		this.healthy = healthy;
		return this;
	}

	public Heartbeat setRecord(Record record) {
		this.record = record;
		return this;
	}

	public JsonObject toJson() {
		JsonObject json = new JsonObject();
		HeartbeatConverter.toJson(this, json);
		return json;
	}
}