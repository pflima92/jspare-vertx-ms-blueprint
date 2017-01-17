package io.github.pflima92.plyshare.microservice.utility;

import org.jspare.core.annotation.Component;
import org.jspare.vertx.annotation.RegisterProxyService;

import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;


@ProxyGen
@VertxGen
@Component
@RegisterProxyService(NotificationService.ADDRESS)
public interface NotificationService {
	
	String NAME = "utility-notification-eb-service";
	String ADDRESS = "service.utility.notification";
	
	@Fluent
	NotificationService createNotification(Handler<AsyncResult<JsonObject>> resultHandler);
}