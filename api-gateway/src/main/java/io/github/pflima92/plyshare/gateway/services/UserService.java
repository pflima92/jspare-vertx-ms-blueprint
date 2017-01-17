/*
 *
 */
package io.github.pflima92.plyshare.gateway.services;

import java.util.List;
import java.util.Optional;

import org.jspare.core.annotation.Component;

import io.github.pflima92.plyshare.gateway.entity.User;
import io.vertx.core.Future;

//@VertxGen TODO change to AsyncResult allow iteroperate
@Component
public interface UserService {

	Future<Optional<User>> getById(int id);

	Future<Optional<User>> getByUsernameAndPassword(String username, String password);

	Future<List<User>> list();

	Future<User> save(User user);
}