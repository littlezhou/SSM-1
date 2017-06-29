/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.smartdata.integration;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Test;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

public class TestCaseCacheFile extends IntegrationTestBase {

  @Test
  public void test() throws Exception {
    String rule = "file : every 1s | accessCount(1min) > 1 | cache";
    Response respSubmitRule = RestAssured.post(RULEROOT + "/add?args=" + rule);
    respSubmitRule.then().body("status", equalTo("CREATED"));
    long ruleId = respSubmitRule.jsonPath().getLong("body");

    RestAssured.post(RULEROOT + "/" + ruleId + "/start").then()
        .body("status", equalTo("OK"));

    String file = "/testCache/testCacheFile";
    RestAssured.post(CMDLETROOT + "/submit/write?args=-file " + file + " -length 64")
        .then().body("status", equalTo("CREATED"));
    Thread.sleep(6000);

    RestAssured.post(CMDLETROOT + "/submit/read?args=-file " + file)
        .then().body("status", equalTo("CREATED"));
    Thread.sleep(2000);
    RestAssured.post(CMDLETROOT + "/submit/read?args=-file " + file)
        .then().body("status", equalTo("CREATED"));
    Thread.sleep(2000);

    RestAssured.get(RULEROOT + "/" + ruleId + "/info")
        .then().body("status", equalTo("OK"))
        .body("body.numCmdsGen", greaterThan(0));

    for (int i = 0; i < 10; i++) {
      Response resp = RestAssured.get(PRIMCLUSTERROOT + "/cachedfiles");
      resp.then().body("status", equalTo("OK"));
      if (resp.jsonPath().getMap("body").size() > 0) {
        resp.then().root("body").body("path", contains(file));
        break;
      }
    }
  }
}
