/*
 * Copyright 2016 JSpare.org.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package io.github.pflima92.plyshare.common.web.handlers;

import org.apache.commons.lang.StringUtils;

import io.github.pflima92.plyshare.common.web.HttpHeaders;
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

		String tid = ctx.request().getHeader(HttpHeaders.TID);
		if (StringUtils.isEmpty(tid)) {
			tid = TidGenerator.generate();
			ctx.put(HttpHeaders.TID, tid);
			ctx.request().headers().add(HttpHeaders.TID, tid);
			ctx.request().headers().add(HttpHeaders.GATEWAY_ORIGIN, ctx.request().absoluteURI());
			ctx.response().putHeader(HttpHeaders.TID, tid);
			if (log.isDebugEnabled()) {

				log.debug("Received request to [{}] with TID [{}]", ctx.request().uri(), tid);
			}
		}
		final String fTid = tid;
		ctx.response().bodyEndHandler(v -> {
			if (log.isDebugEnabled()) {

				log.debug("Finish request for TID [{}]", fTid);
			}
		});

		ctx.next();
	}
}