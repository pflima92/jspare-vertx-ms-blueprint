/*
 * Copyright 2016 JSpare.org.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package io.github.pflima92.plyshare.gateway.persistance;

import java.util.List;
import java.util.Optional;

import org.jspare.core.annotation.Component;

import io.github.pflima92.plyshare.gateway.entity.Gateway;
import io.github.pflima92.plyshare.gateway.entity.User;
import io.vertx.core.Future;

@Component
public interface GatewayPersistance {

	Future<Optional<Gateway>> findGateway(String profile);

	Future<Gateway> persistGateway(Gateway gateway);

	Future<Optional<User>> findUserById(Integer id);

	Future<Optional<User>> findUserByUsernameAndPassword(String username, String password);

	Future<List<User>> listUsers();

	Future<User> persistUser(User user);
}