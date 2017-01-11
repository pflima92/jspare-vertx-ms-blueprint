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

package org.jspare.spareco.common.servicediscovery;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;

/**
 * Converter for {@link org.jspare.spareco.common.servicediscovery.RecordMetadata}.
 *
 * NOTE: This class has been automatically generated from the {@link org.jspare.spareco.common.servicediscovery.RecordMetadata} original class using Vert.x codegen.
 */
public class RecordMetadataConverter {

  public static void fromJson(JsonObject json, RecordMetadata obj) {
    if (json.getValue("checkHealth") instanceof Boolean) {
      obj.setCheckHealth((Boolean)json.getValue("checkHealth"));
    }
    if (json.getValue("hearthbeatPath") instanceof String) {
      obj.setHearthbeatPath((String)json.getValue("hearthbeatPath"));
    }
    if (json.getValue("name") instanceof String) {
      obj.setName((String)json.getValue("name"));
    }
  }

  public static void toJson(RecordMetadata obj, JsonObject json) {
    json.put("checkHealth", obj.isCheckHealth());
    if (obj.getHearthbeatPath() != null) {
      json.put("hearthbeatPath", obj.getHearthbeatPath());
    }
    if (obj.getName() != null) {
      json.put("name", obj.getName());
    }
  }
}