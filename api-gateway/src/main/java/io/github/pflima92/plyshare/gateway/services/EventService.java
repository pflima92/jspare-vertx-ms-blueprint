package io.github.pflima92.plyshare.gateway.services;

import org.jspare.core.annotation.Component;

import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.json.JsonObject;

@VertxGen
@Component
public interface EventService {
	
	String SEND_EVENT_ADDRESS = "__gateway.event.send";
	
	@Fluent
	EventService send(JsonObject event);
}