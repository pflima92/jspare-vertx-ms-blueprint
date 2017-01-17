package io.github.pflima92.plyshare.common.web.auth;

import io.vertx.ext.auth.AuthProvider;
import io.vertx.ext.web.handler.AuthHandler;

public interface MicroserviceAuthHandler extends AuthHandler {
	
	static MicroserviceAuthHandler create(AuthProvider authProvider){
		
		return new MicroserviceAuthHandlerImpl(authProvider); 
	}
}