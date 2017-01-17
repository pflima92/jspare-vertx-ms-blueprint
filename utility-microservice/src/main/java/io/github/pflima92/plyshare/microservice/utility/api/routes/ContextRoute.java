package io.github.pflima92.plyshare.microservice.utility.api.routes;

import org.jspare.vertx.web.annotation.auth.Auth;
import org.jspare.vertx.web.annotation.handler.Handler;
import org.jspare.vertx.web.annotation.method.Get;
import org.jspare.vertx.web.annotation.subrouter.SubRouter;
import org.jspare.vertx.web.handler.APIHandler;

@SubRouter("/context")
public class ContextRoute extends APIHandler {
	
	@Auth
	@Get
	@Handler
	public void getContext(){
		
		success(ctx.user());
	}
}