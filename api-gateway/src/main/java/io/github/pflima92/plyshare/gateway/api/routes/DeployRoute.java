package io.github.pflima92.plyshare.gateway.api.routes;

import java.io.File;

import org.jspare.core.annotation.Inject;
import org.jspare.vertx.web.annotation.handler.Handler;
import org.jspare.vertx.web.annotation.method.Post;
import org.jspare.vertx.web.annotation.subrouter.SubRouter;

import io.github.pflima92.plyshare.common.web.RestAPIHandler;
import io.github.pflima92.plyshare.gateway.library.LibrarySupport;
import io.github.pflima92.plyshare.gateway.services.DeployService;
import io.vertx.core.json.JsonObject;

@SubRouter("/api/deploy")
public class DeployRoute extends RestAPIHandler {
	
	@Inject
	private DeployService deployService;
	
	@Inject
	private LibrarySupport librarySupport;
	
	@Post
	@Handler
	public void deploy(){

		fileUploads().stream().forEach(fu -> {
			
			File file = new File(fu.uploadedFileName());
			librarySupport.addSoftwareLibrary(file);
		});
		
		success("Deployed");
	} 
	
	@Post("/start")
	@Handler
	public void start(JsonObject metadata){

		deployService.deploy(metadata, this::handler);
	}
	
	@Post("/stop")
	@Handler
	public void stop(JsonObject metadata){
		
		deployService.stop(metadata, this::handler);
	}
}