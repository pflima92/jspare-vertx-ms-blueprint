package io.github.pflima92.plyshare.common.web.auth;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.AuthProvider;
import io.vertx.ext.auth.User;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.Session;
import io.vertx.ext.web.handler.impl.AuthHandlerImpl;
import io.vertx.ext.web.handler.impl.UserHolder;

public class MicroserviceAuthHandlerImpl extends AuthHandlerImpl implements MicroserviceAuthHandler {

	private static final String SESSION_USER_HOLDER_KEY = "__vertx.userHolder";

	public MicroserviceAuthHandlerImpl(AuthProvider authProvider) {
		super(authProvider);
	}

	@Override
	public void handle(RoutingContext context) {

		Session session = context.session();
		User user = null;

		if (session != null) {

			UserHolder holder = session.get(SESSION_USER_HOLDER_KEY);

			if (holder != null) {
				RoutingContext prevContext = holder.context;
				if (prevContext != null) {
					user = prevContext.user();
				} else if (holder.user != null) {
					user = holder.user;
					user.setAuthProvider(authProvider);
					holder.context = context;
					holder.user = null;
				}
				holder.context = context;
			}
		}

		if (user != null) {
			
			context.setUser(user);
			authorise(user, context);
			return;
		}

		authProvider.authenticate(new JsonObject(), ar -> {

			if (ar.succeeded()) {

				context.setUser(ar.result());
				session.put(SESSION_USER_HOLDER_KEY, new UserHolder(context));
				authorise(ar.result(), context);
			} else {

				context.fail(401);
			}
		});
	}
}
