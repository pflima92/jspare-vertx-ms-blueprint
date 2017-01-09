package org.jspare.spareco.gateway.model;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject
public class AuditData {
	
	private String tid;
	
	public AuditData(JsonObject json){
		
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}
}