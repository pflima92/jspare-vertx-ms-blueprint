package io.github.pflima92.plyshare.gateway.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.jspare.core.container.ContainerUtils;
import org.jspare.vertx.concurrent.FutureSupplier;

import io.github.pflima92.plyshare.gateway.ext.AuthAdapter;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import lombok.SneakyThrows;

public class SecurityManagerImpl implements SecurityManager {
	
	private final List<AuthAdapter> adapters;

	public SecurityManagerImpl() {

		adapters = new ArrayList<>();
	}
	
	@Override
	@SneakyThrows
	public SecurityManager registerAdapter(Class<?> clazz, Handler<AsyncResult<String>> resultHandler) {

		AuthAdapter authAdapter = (AuthAdapter) clazz.newInstance();
		ContainerUtils.processInjection(authAdapter);
		adapters.add(authAdapter);
		
		return this;
	}

	@Override
	public SecurityManager registerAdapter(AuthAdapter adapter, Handler<AsyncResult<String>> resultHandler) {
		adapters.add(adapter);
		return this;
	}

	@Override
	public SecurityManager registerAdapters(List<AuthAdapter> adapters, Handler<AsyncResult<String>> resultHandler) {
		adapters.addAll(adapters);
		return this;
	}

	@Override
	public SecurityManager execute(JsonObject authInfo, Handler<AsyncResult<JsonObject>> resultHandler) {

		List<Future<JsonObject>> futures = adapters.stream().map(a -> {
			
			Future<JsonObject> future = Future.future();
			
			return future;
		}).collect(Collectors.toList());
		
		FutureSupplier.sequenceFuture(futures).setHandler(res -> {
			
		});
		

		resultHandler.handle(Future.succeededFuture(new JsonObject()));
		return this;
	}

}
