package io.github.pflima92.plyshare.gateway.ext.impl;

import java.util.Optional;
import java.util.stream.Collectors;

import org.jspare.core.container.MySupport;
import org.jspare.vertx.annotation.VertxInject;

import io.github.pflima92.plyshare.gateway.ext.AuthAdapter;
import io.github.pflima92.plyshare.gateway.ext.CredentialNotFoundException;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.file.FileSystem;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import lombok.Getter;
import lombok.Setter;

public class BasicAuthAdapterImpl extends MySupport implements AuthAdapter{

	@VertxInject
	private FileSystem fs;

	private String path = "auth.json";

	@Override
	public void authenticate(JsonObject authInfo, Handler<AsyncResult<JsonObject>> resultHandler) {
		
		fs.readFile(path, res -> {

			if(res.failed()){
				resultHandler.handle(Future.failedFuture(res.cause()));
				return;
			}
			
			String username = authInfo.getString("username");
			String password = authInfo.getString("password");
			
			JsonArray json = res.result().toJsonArray();
			Optional<JsonObject> oUser = json.stream()
			.map(o -> (JsonObject) o)
			.filter(o -> {
				
				JsonObject user = (JsonObject) o;
				
				return username.equals(user.getString("username")) && password.equals(user.getString("password"));
			}).collect(Collectors.toList()).stream().findFirst();
			
			if(oUser.isPresent()){
				
				resultHandler.handle(Future.succeededFuture(oUser.get()));
			}else{
				
				resultHandler.handle(Future.failedFuture(new CredentialNotFoundException()));
			}
		});
	}

	public String getPath() {
		return path;
	}

	public BasicAuthAdapterImpl setPath(String path) {
		this.path = path;
		return this;
	}
	
	
	
}
