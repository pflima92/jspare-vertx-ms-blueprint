package io.github.pflima92.plyshare.common.web;

import org.apache.commons.lang.StringUtils;

import io.vertx.ext.web.RoutingContext;

/**
 * The Class ResponseHelper.
 */
public class ResponseHelper {

	/**
	 * Create SimpleResponse with {@link RoutingContext}.
	 *
	 * @param ctx
	 *            the routing context
	 * @return the simple response
	 */
	public static SimpleResponse create(RoutingContext ctx) {
		
		String self = ctx.request().getHeader(HttpHeaders.GATEWAY_ORIGIN);
		if(StringUtils.isEmpty(self)){
			self = ctx.request().absoluteURI();
		}
		return new SimpleResponse().setSelf(self);
	}

	/**
	 * Creates the.
	 *
	 * @param ctx the ctx
	 * @param e the e
	 * @return the simple response
	 */
	public static SimpleResponse create(RoutingContext ctx, BusinessException e) {

		ResponseInfo responseInfo = new ResponseInfo(e.getReasons());
		return create(ctx).setResponseInfo(responseInfo);
	}

	/**
	 * Creates the.
	 *
	 * @param ctx the ctx
	 * @param e the e
	 * @return the simple response
	 */
	public static SimpleResponse create(RoutingContext ctx, Throwable e) {

		return create(ctx).setResponseInfo(new ResponseInfo(Reason.error(e.getClass().getName()).detail(e.getMessage())));
	}
}