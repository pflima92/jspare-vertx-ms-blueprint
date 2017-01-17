package io.github.pflima92.plyshare.microservice.utility;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.ext.mail.MailMessage;
import io.vertx.ext.mail.MailResult;

public class MailServiceImpl implements MailService {

	@Override
	public MailService send(MailMessage message, Handler<AsyncResult<MailResult>> resultHandler) {

		return this;
	}
}