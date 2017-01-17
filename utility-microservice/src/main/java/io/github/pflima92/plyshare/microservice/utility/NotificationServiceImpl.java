package io.github.pflima92.plyshare.microservice.utility;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;

public class NotificationServiceImpl implements NotificationService {

	@Override
	public NotificationService createNotification(Handler<AsyncResult<JsonObject>> resultHandler) {

		Future<JsonObject> future = Future.future();
		future.complete();
		resultHandler.handle(future);
		return this;
	}
}