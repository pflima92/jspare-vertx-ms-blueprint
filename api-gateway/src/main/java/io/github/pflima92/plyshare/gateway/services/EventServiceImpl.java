package io.github.pflima92.plyshare.gateway.services;

import org.jspare.vertx.annotation.VertxInject;

import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EventServiceImpl implements EventService {

	@VertxInject
	private EventBus eventBus;
	
	@Override
	public EventService send(JsonObject event) {

		log.debug("Received event", event);
		log.debug("Dispatch to eventbus address {}", SEND_EVENT_ADDRESS);
		
		eventBus.send(SEND_EVENT_ADDRESS, event);
		
		return this;
	}
}