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
package org.smartdata.action;

import org.smartdata.action.annotation.ActionSignature;

import java.util.Map;

@ActionSignature(
    actionId = "sleep",
    displayName = "sleep",
    usage = SleepAction.TIME_IN_MS + " $timeToSleepInMs"
)
public class SleepAction extends SmartAction {
  public static final String TIME_IN_MS = "-ms";

  @Override
  protected void execute() throws Exception {
    if (!getArguments().containsKey(TIME_IN_MS)) {
      throw new IllegalArgumentException("Time to sleep not specified (through option '"
          + TIME_IN_MS + "').");
    }
    long tm = Long.valueOf(getArguments().get(TIME_IN_MS));
    if (tm != 0) {
      Thread.sleep(tm);
    }
  }
}
