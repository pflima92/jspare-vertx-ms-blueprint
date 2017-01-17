package io.github.pflima92.plyshare.gateway.services;

import org.jspare.core.annotation.Component;

import io.vertx.core.Future;
import io.vertx.core.buffer.Buffer;
import io.vertx.ext.web.RoutingContext;
import io.vertx.servicediscovery.Record;

//@VertxGen TODO change to AsyncResult allow iteroperate 
@Component
public interface ProxyService {

	String PROXY_PATTERN = "/api/%s";

	Future<Void> proxy(RoutingContext ctx, Record record, Buffer processedBuffer);
}