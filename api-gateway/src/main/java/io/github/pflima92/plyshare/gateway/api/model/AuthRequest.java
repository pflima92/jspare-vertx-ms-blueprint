package io.github.pflima92.plyshare.gateway.api.model;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter=true)
public class AuthRequest {
	
	private String username;
	private String password;
	
	public AuthRequest() {
	}
	
	public AuthRequest(JsonObject json) {
		
		AuthRequestConverter.fromJson(json, this);
	}
	
	public JsonObject toJson(){
		JsonObject json = new JsonObject();
		AuthRequestConverter.toJson(this, json);
		return json;
	}
	
	public String getUsername() {
		return username;
	}
	public AuthRequest setUsername(String username) {
		this.username = username;
		return this;
	}
	public String getPassword() {
		return password;
	}
	public AuthRequest setPassword(String password) {
		this.password = password;
		return this;
	}
}