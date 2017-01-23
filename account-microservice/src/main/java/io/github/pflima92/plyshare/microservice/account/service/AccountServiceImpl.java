package io.github.pflima92.plyshare.microservice.account.service;

import io.github.pflima92.plyshare.microservice.account.model.Account;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;

public class AccountServiceImpl implements AccountService {

	@Override
	public AccountService findAccount(Handler<AsyncResult<Account>> resultHandler) {

		resultHandler.handle(Future.succeededFuture(new Account()));
		
		return null;
	}

}
