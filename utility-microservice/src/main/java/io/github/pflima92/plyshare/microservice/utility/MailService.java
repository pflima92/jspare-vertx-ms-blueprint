package io.github.pflima92.plyshare.microservice.utility;

import org.jspare.core.annotation.Component;
import org.jspare.vertx.annotation.RegisterProxyService;

import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.ext.mail.MailMessage;
import io.vertx.ext.mail.MailResult;


@ProxyGen
@VertxGen
@Component
@RegisterProxyService(MailService.ADDRESS)
public interface MailService {
	
	String NAME = "utility-mail-eb-service";
	String ADDRESS = "service.mail.notification";
	
	@Fluent
	MailService send(MailMessage message, Handler<AsyncResult<MailResult>> resultHandler);
}