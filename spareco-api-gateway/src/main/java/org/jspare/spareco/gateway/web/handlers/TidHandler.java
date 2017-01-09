/*
 *
 */
package org.jspare.spareco.gateway.web.handlers;

import org.apache.commons.lang.StringUtils;
import org.jspare.spareco.common.web.Header;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TidHandler implements Handler<RoutingContext> {

	public static TidHandler create() {

		return new TidHandler();
	}

	@Override
	public void handle(RoutingContext ctx) {

		String tid = ctx.request().getHeader(Header.TID.value());
		if (StringUtils.isEmpty(tid)) {
			tid = TidGenerator.generate();
			ctx.put(Header.TID.value(), tid);
			ctx.request().headers().add(Header.TID.value(), tid);
			ctx.request().headers().add(Header.GATEWAY_ORIGIN.value(), ctx.request().absoluteURI());
			ctx.response().putHeader(Header.TID.value(), tid);
			log.debug("Received request to [{}] with TID [{}]", ctx.request().uri(), tid);
		}

		ctx.next();
	}
}