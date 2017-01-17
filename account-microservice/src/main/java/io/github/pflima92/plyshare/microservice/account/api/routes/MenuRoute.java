package io.github.pflima92.plyshare.microservice.account.api.routes;

import org.jspare.vertx.web.annotation.handler.Handler;
import org.jspare.vertx.web.annotation.handling.Parameter;
import org.jspare.vertx.web.annotation.method.Get;
import org.jspare.vertx.web.annotation.subrouter.SubRouter;
import org.jspare.vertx.web.handler.APIHandler;

@SubRouter("/menus")
public class MenuRoute extends APIHandler {
	
	@Get
	@Handler
	public void message(@Parameter("message") String message){
		
	}
}