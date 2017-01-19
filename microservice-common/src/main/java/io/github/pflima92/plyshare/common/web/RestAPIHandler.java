/*
 *
 */
package io.github.pflima92.plyshare.common.web;

import java.util.Optional;

import org.apache.commons.lang.StringUtils;
import org.jspare.vertx.web.handler.APIHandler;

import io.vertx.core.AsyncResult;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class RestAPIHandler extends APIHandler {

	protected String getTid(){
		return getHeader(Header.TID.value()).orElse(StringUtils.EMPTY);
	}
	
	@SuppressWarnings("rawtypes")
	protected void handler(AsyncResult<?> asyncResult) {

		if (asyncResult.failed()) {
			
			log.error("AsyncResult failed", asyncResult.cause());
			error(asyncResult.cause());
			return;
		}

		if (asyncResult.result().getClass().equals(Optional.class) && !((Optional) asyncResult.result()).isPresent()) {

			noContent();
			return;
		}
		
		if(asyncResult.result() == null){
			
			success();
		}
		else{
			
			success(asyncResult.result());
		}
	}
	
	protected JsonObject responseInfo(String message){
		// TODO validate
		return new JsonObject().put("message", message);
	}
	
	public SimpleResponse simpleResponse() {

		return ResponseHelper.create(context);
	}
	
	public SimpleResponse simpleResponse(BusinessException e) {

		return ResponseHelper.create(context, e);
	}

	public SimpleResponse simpleResponse(Object data) {

		if (data instanceof SimpleResponse) {
			
			return (SimpleResponse) data;
		}else if(data instanceof BusinessException){
			
			return ResponseHelper.create(context, (BusinessException) data);
		}else if(data instanceof Throwable){
			
			return ResponseHelper.create(context, (Throwable) data);
		}

		return ResponseHelper.create(context).setValue(data);
	}

	public SimpleResponse simpleResponse(Throwable t) {

		return ResponseHelper.create(context, t);
	}
	
	@Override
	protected String transform(Object data) {
		
		return Json.encode(simpleResponse(data));
	}
}