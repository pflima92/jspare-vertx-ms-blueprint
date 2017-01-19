/*
 *
 */
package io.github.pflima92.plyshare.gateway.api.routes;

import org.jspare.core.annotation.Inject;
import org.jspare.vertx.web.annotation.handler.Handler;
import org.jspare.vertx.web.annotation.handling.Parameter;
import org.jspare.vertx.web.annotation.method.Get;
import org.jspare.vertx.web.annotation.method.Post;
import org.jspare.vertx.web.annotation.subrouter.SubRouter;

import io.github.pflima92.plyshare.common.web.RestAPIHandler;
import io.github.pflima92.plyshare.gateway.entity.Audit;
import io.github.pflima92.plyshare.gateway.services.AuditService;
import io.vertx.core.json.JsonObject;

@SubRouter("/api/audit")
public class AuditRoute extends RestAPIHandler {

	@Inject
	private AuditService auditService;
	
	@Get("/:tid")
	@Handler
	public void findById(@Parameter("tid") String tid) {

		auditService.findByTid(tid, this::handler);
	}
	
	@Post
	@Handler
	public void save(Audit audit) {

		auditService.save(audit, this::handler);
	}
	
	@Get("/search")
	@Post("/search")
	@Handler
	public void search(JsonObject filter) {

		auditService.list(filter, this::handler);
	}
}