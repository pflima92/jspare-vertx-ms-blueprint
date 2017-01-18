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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@NamedQueries({ @NamedQuery(name = "Audit.findByTid", query = "from Audit a WHERE a.tid = :tid") })
@Entity
@Table(name = "audit")
@DataObject(generateConverter = true)
public class Audit extends Model {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Column
	private String tid;
	
	@Column
	private int responseCode;
	
	@ManyToOne
	@JoinColumn(name = "fk_gateway_id")
	private Gateway gateway;
	
	public Audit() {
	}
	
	public Audit(JsonObject json) {
		AuditConverter.fromJson(json, this);
	}
	
	public JsonObject toJson(){
		JsonObject json = new JsonObject();
		AuditConverter.toJson(this, json);
		return json;
	}

	public String getTid() {
		return tid;
	}

	public Audit setTid(String tid) {
		this.tid = tid;
		return this;
	}

	public Gateway getGateway() {
		return gateway;
	}

	public Audit setGateway(Gateway gateway) {
		this.gateway = gateway;
		return this;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public Audit setResponseCode(int responseCode) {
		this.responseCode = responseCode;
		return this;
	}
}