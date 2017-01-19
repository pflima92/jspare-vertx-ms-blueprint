package io.github.pflima92.plyshare.common.web;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.AbstractUser;
import io.vertx.ext.auth.AuthProvider;
import lombok.Getter;
import lombok.Setter;

public class Gatekeeper extends AbstractUser {

	@Setter
	private AuthProvider authProvider;

	@Getter @Setter
	private List<String> permissions = new ArrayList<>();

	private String username;

	public Gatekeeper(){
	}
	
	@Override
	protected void doIsPermitted(String permission, Handler<AsyncResult<Boolean>> resultHandler) {

		resultHandler.handle(Future.succeededFuture(permissions.contains(permission)));
	}

	public String getUsername() {
		return username;
	}

	@Override
	public JsonObject principal() {

		return new JsonObject().put("username", username).put("permissions", permissions);
	}

	@Override
	public int readFromBuffer(int pos, Buffer buffer) {
		int num = buffer.getInt(pos);
	    pos += 4;
	    for (int i = 0; i < num; i++) {
	      int len = buffer.getInt(pos);
	      pos += 4;
	      byte[] bytes = buffer.getBytes(pos, pos + len);
	      pos += len;
	      permissions.add(new String(bytes, StandardCharsets.UTF_8));
	    }
		int len = buffer.getInt(pos);
		pos += 4;
		byte[] bytes = buffer.getBytes(pos, pos + len);
		username = new String(bytes, StandardCharsets.UTF_8);
		return pos;
	}
	
	public Gatekeeper setUsername(String username) {
		this.username = username;
		return this;
	}

	@Override
	public void writeToBuffer(Buffer buff) {
		 buff.appendInt(permissions == null ? 0 : permissions.size());
		    if (permissions != null) {
		      for (String entry : permissions) {
		        byte[] bytes = entry.getBytes(StandardCharsets.UTF_8);
		        buff.appendInt(bytes.length).appendBytes(bytes);
		      }
		    }
		buff.appendInt(username.length()).appendBytes(username.getBytes());
	}
}