package io.github.pflima92.plyshare.gateway.ext.impl;

import io.github.pflima92.plyshare.gateway.ext.AuthAdapter;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;

public class LdapAuthAdapterImpl implements AuthAdapter {

  @Override
  public void authenticate(JsonObject authInfo, Handler<AsyncResult<JsonObject>> resultHandler) {
    
  }
}
