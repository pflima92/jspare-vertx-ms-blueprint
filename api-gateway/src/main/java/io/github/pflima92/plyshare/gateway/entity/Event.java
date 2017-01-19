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
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@Entity
@Table(name = "event")
@DataObject(generateConverter = true)
public class Event extends Model {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	
	@Column(columnDefinition = "TEXT")
	@Convert(converter = JsonObjectConverter.class)
	private JsonObject content;

	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "fk_gateway_id")
	private Gateway gateway;
	
	public Event() {
	}
	
	public Event(JsonObject json) {
		EventConverter.fromJson(json, this);
	}
	
	public Gateway getGateway() {
		return gateway;
	}
	
	public JsonObject getContent() {
		return content;
	}

	public Event setContent(JsonObject content) {
		this.content = content;
		return this;
	}

	public JsonObject toJson(){
		JsonObject json = new JsonObject();
		EventConverter.toJson(this, json);
		return json;
	}
}