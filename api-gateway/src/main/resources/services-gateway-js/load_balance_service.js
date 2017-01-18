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

/** @module services-gateway-js/load_balance_service */
var utils = require('vertx-js/util/utils');

var io = Packages.io;
var JsonObject = io.vertx.core.json.JsonObject;
var JLoadBalanceService = io.github.pflima92.plyshare.gateway.services.LoadBalanceService;
var Record = io.vertx.servicediscovery.Record;

/**
 @class
*/
var LoadBalanceService = function(j_val) {

  var j_loadBalanceService = j_val;
  var that = this;

  /**

   @public
   @param alias {string} 
   @param oRecordHandler {function} 
   @return {LoadBalanceService}
   */
  this.getRecord = function(alias, oRecordHandler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_loadBalanceService["getRecord(java.lang.String,io.vertx.core.Handler)"](alias, function(ar) {
      if (ar.succeeded()) {
        oRecordHandler(utils.convReturnDataObject(ar.result()), null);
      } else {
        oRecordHandler(null, ar.cause());
      }
    });
      return that;
    } else throw new TypeError('function invoked with invalid arguments');
  };

  // A reference to the underlying Java delegate
  // NOTE! This is an internal API and must not be used in user code.
  // If you rely on this property your code is likely to break if we change it / remove it without warning.
  this._jdel = j_loadBalanceService;
};

// We export the Constructor function
module.exports = LoadBalanceService;