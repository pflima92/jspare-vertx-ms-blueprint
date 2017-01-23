package io.github.pflima92.plyshare.gateway.manager;

import org.jspare.core.container.ContainerUtils;

import io.github.pflima92.plyshare.gateway.ext.AuthAdapter;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import lombok.SneakyThrows;

public class SecurityManagerImpl implements SecurityManager {
	
	private AuthAdapter authAdapter;
	
	public SecurityManagerImpl() {
	}
	public SecurityManagerImpl(AuthAdapter authAdapter) {
		this.authAdapter = authAdapter;
	}

	@Override
	@SneakyThrows
	public SecurityManager registerAdapter(Class<?> clazz, Handler<AsyncResult<String>> resultHandler) {

		AuthAdapter authAdapter = (AuthAdapter) clazz.newInstance();
		ContainerUtils.processInjection(authAdapter);
		this.authAdapter = authAdapter;
		
		return this;
	}

	@Override
	public SecurityManager registerAdapter(AuthAdapter authAdapter, Handler<AsyncResult<String>> resultHandler) {
		this.authAdapter = authAdapter;
		return this;
	}

	@Override
	public SecurityManager execute(JsonObject authInfo, Handler<AsyncResult<JsonObject>> resultHandler) {

		authAdapter.authenticate(authInfo, resultHandler);
		return this;
	}

}
