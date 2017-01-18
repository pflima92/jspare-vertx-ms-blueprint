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
package io.github.pflima92.plyshare.gateway.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@NamedQueries({ @NamedQuery(name = "Gateway.findByProfile", query = "from Gateway g WHERE g.profile = :profile") })
@Entity
@Table(name = "gateway")
@DataObject(generateConverter = true)
public class Gateway extends Model {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Column(unique = true, nullable = false)
	private String profile;
	@Column
	private String serialKey;
	@Column
	private String privateKey;
	@Column
	private String owner;
	@Column
	private String version;
	@Column
	private String build;
	@Column
	private LocalDateTime lastStarted;
	
	public Gateway() {
	}
	
	public Gateway(JsonObject json) {
		GatewayConverter.fromJson(json, this);
	}
	
	public JsonObject toJson(){
		JsonObject json = new JsonObject();
		GatewayConverter.toJson(this, json);
		return json;
	}
	
	public String getProfile() {
		return profile;
	}
	public Gateway setProfile(String profile) {
		this.profile = profile;
		return this;
	}
	public String getSerialKey() {
		return serialKey;
	}
	public Gateway setSerialKey(String serialKey) {
		this.serialKey = serialKey;
		return this;
	}
	public String getPrivateKey() {
		return privateKey;
	}
	public Gateway setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
		return this;
	}
	public String getOwner() {
		return owner;
	}
	public Gateway setOwner(String owner) {
		this.owner = owner;
		return this;
	}
	public String getVersion() {
		return version;
	}
	public Gateway setVersion(String version) {
		this.version = version;
		return this;
	}
	public String getBuild() {
		return build;
	}
	public Gateway setBuild(String build) {
		this.build = build;
		return this;
	}
	public LocalDateTime getLastStarted() {
		return lastStarted;
	}
	public Gateway setLastStarted(LocalDateTime lastStarted) {
		this.lastStarted = lastStarted;
		return this;
	}
}