package io.github.pflima92.plyshare.microservice.utility.api.routes;

import org.jspare.vertx.web.annotation.auth.Auth;
import org.jspare.vertx.web.annotation.handler.Handler;
import org.jspare.vertx.web.annotation.method.Get;
import org.jspare.vertx.web.annotation.subrouter.SubRouter;

import io.github.pflima92.plyshare.common.web.RestAPIHandler;

@SubRouter("/menus")
public class MenuRoute extends RestAPIHandler {
	
	@Auth
	@Get
	@Handler
	public void getMenus(){

		success();
	}
}