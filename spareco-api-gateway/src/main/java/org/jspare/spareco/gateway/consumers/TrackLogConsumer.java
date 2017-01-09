/*
 *
 */
package org.jspare.spareco.gateway.consumers;

import org.jspare.vertx.annotation.Consumer;
import org.jspare.vertx.annotation.EventBusController;

import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;

@EventBusController
public class TrackLogConsumer {

	@Consumer("add-track-log-eb-address")
	public void add(Message<JsonObject> event) {

	}
}