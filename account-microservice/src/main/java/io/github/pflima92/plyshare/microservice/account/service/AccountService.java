package io.github.pflima92.plyshare.microservice.account.service;

import io.github.pflima92.plyshare.microservice.account.model.Account;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

public interface AccountService {
	
	AccountService findAccount(Handler<AsyncResult<Account>> resultHandler);

}
