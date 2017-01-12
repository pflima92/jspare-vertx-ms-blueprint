/*
 * Copyright 2014 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package org.jspare.spareco.common.discovery;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;

/**
 * Converter for {@link org.jspare.spareco.common.discovery.Heartbeat}.
 *
 * NOTE: This class has been automatically generated from the {@link org.jspare.spareco.common.discovery.Heartbeat} original class using Vert.x codegen.
 */
public class HeartbeatConverter {

  public static void fromJson(JsonObject json, Heartbeat obj) {
    if (json.getValue("healthy") instanceof Boolean) {
      obj.setHealthy((Boolean)json.getValue("healthy"));
    }
    if (json.getValue("record") instanceof JsonObject) {
      obj.setRecord(new io.vertx.servicediscovery.Record((JsonObject)json.getValue("record")));
    }
  }

  public static void toJson(Heartbeat obj, JsonObject json) {
    json.put("healthy", obj.isHealthy());
    if (obj.getRecord() != null) {
      json.put("record", obj.getRecord().toJson());
    }
  }
}