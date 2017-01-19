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

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "fk_gateway_id")
	private Gateway gateway;	
	
	@Column
	private String tid;
	
	@Column
	private String alias;
	
	@Column
	private String uri;
	
	@Column(columnDefinition = "TEXT")
	@Convert(converter=JsonObjectConverter.class)
	private JsonObject record;
	
	@Column(columnDefinition = "TEXT")
	@Convert(converter=JsonObjectConverter.class)
	private JsonObject headers;
	
	@JsonIgnore
	@Lob
	@Column(columnDefinition = "varbinary(MAX)")
	private Blob requestContent;
	
	@JsonIgnore
	@Lob
	@Column(columnDefinition = "varbinary(MAX)")
	private Blob responseContent;
	
	@Column
	private int responseCode;
	
	@Column(columnDefinition = "TEXT")
	private String error;
	
	public Audit() {
	}
	
	public Audit(JsonObject json) {
		AuditConverter.fromJson(json, this);
	}
	
	public Gateway getGateway() {
		return gateway;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public String getTid() {
		return tid;
	}

	public String getAlias() {
		return alias;
	}

	public Audit setAlias(String alias) {
		this.alias = alias;
		return this;
	}

	public JsonObject getRecord() {
		return record;
	}

	public Audit setRecord(JsonObject record) {
		this.record = record;
		return this;
	}

	public String getUri() {
		return uri;
	}

	public Audit setUri(String uri) {
		this.uri = uri;
		return this;
	}

	public JsonObject getHeaders() {
		return headers;
	}

	public Audit setHeaders(JsonObject headers) {
		this.headers = headers;
		return this;
	}

	public Blob getRequestContent() {
		return requestContent;
	}

	public Audit setRequestContent(Blob requestContent) {
		this.requestContent = requestContent;
		return this;
	}

	public Blob getResponseContent() {
		return responseContent;
	}

	public Audit setResponseContent(Blob responseContent) {
		this.responseContent = responseContent;
		return this;
	}

	public Audit setGateway(Gateway gateway) {
		this.gateway = gateway;
		return this;
	}

	public Audit setResponseCode(int responseCode) {
		this.responseCode = responseCode;
		return this;
	}

	public Audit setTid(String tid) {
		this.tid = tid;
		return this;
	}

	public String getError() {
		return error;
	}

	public Audit setError(String error) {
		this.error = error;
		return this;
	}

	public JsonObject toJson(){
		JsonObject json = new JsonObject();
		AuditConverter.toJson(this, json);
		return json;
	}
}