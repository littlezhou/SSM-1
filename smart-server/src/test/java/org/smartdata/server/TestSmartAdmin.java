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
package org.smartdata.server;

import org.junit.Test;
import org.smartdata.admin.SmartAdmin;
import org.smartdata.common.SmartConfigureKeys;
import org.smartdata.common.rule.RuleInfo;
import org.smartdata.common.rule.RuleState;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class TestSmartAdmin extends TestEmptyMiniSmartCluster {

  @Test
  public void test() throws Exception {
    waitTillSSMExitSafeMode();

    SmartAdmin ssmClient = new SmartAdmin(conf);

    while (true) {
      //test getServiceStatus
      String state = ssmClient.getServiceState().getName();
      if ("ACTIVE".equals(state)) {
        break;
      }
      Thread.sleep(1000);
    }

    //test listRulesInfo and submitRule
    List<RuleInfo> ruleInfos = ssmClient.listRulesInfo();
    int ruleCounts0 = ruleInfos.size();
    long ruleId = ssmClient.submitRule(
        "file: every 5s | path matches \"/foo*\"| cachefile",
        RuleState.DRYRUN);
    ruleInfos = ssmClient.listRulesInfo();
    int ruleCounts1 = ruleInfos.size();
    assertEquals(1, ruleCounts1 - ruleCounts0);

    //test checkRule
    //if success ,no Exception throw
    ssmClient.checkRule("file: every 5s | path matches \"/foo*\"| cachefile");
    boolean caughtException = false;
    try {
      ssmClient.checkRule("file.path");
    } catch (IOException e) {
      caughtException = true;
    }
    assertTrue(caughtException);

    //test getRuleInfo
    RuleInfo ruleInfo = ssmClient.getRuleInfo(ruleId);
    assertNotEquals(null, ruleInfo);

    //test disableRule
    ssmClient.disableRule(ruleId, true);
    assertEquals(RuleState.DISABLED, ssmClient.getRuleInfo(ruleId).getState());

    //test activateRule
    ssmClient.activateRule(ruleId);
    assertEquals(RuleState.ACTIVE, ssmClient.getRuleInfo(ruleId).getState());

    //test deleteRule
    ssmClient.deleteRule(ruleId, true);
    assertEquals(RuleState.DELETED, ssmClient.getRuleInfo(ruleId).getState());

    //test single SSM
    caughtException = false;
    try {
      conf.set(SmartConfigureKeys.DFS_SSM_RPC_ADDRESS_KEY, "localhost:8043");
      SmartServer.createSSM(null, conf);
    } catch (IOException e) {
      assertEquals("java.io.IOException: Another SmartServer is running",
          e.toString());
      caughtException = true;
    }
    assertTrue(caughtException);

    //test client close
    caughtException = false;
    ssmClient.close();
    try {
      ssmClient.getRuleInfo(ruleId);
    } catch (IOException e) {
      caughtException = true;
    }
    assertEquals(true, caughtException);
  }
}