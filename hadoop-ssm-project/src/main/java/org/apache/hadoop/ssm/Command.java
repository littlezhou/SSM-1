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
package org.apache.hadoop.ssm;

/**
 * Command is the minimum unit of execution. Different commands can be
 * executed at the same time, but actions belongs to an action can only be
 * executed in serial.
 *
 * The command get executed when rule conditions fulfilled.
 * A command can have many actions, for example:
 * Command 'archive' contains the following two actions:
 *  1.) SetStoragePolicy
 *  2.) EnforceStoragePolicy
 */
public class Command implements Runnable {
  public static final int NOTINITED = 0;
  public static final int READY = 1;
  public static final int EXECUTING = 2;
  public static final int DONE = 3;

  private long ruleId;   // id of the rule that this Command comes from
  private long id;
  private int state = NOTINITED;
  private ActionBase[] actions;

  private long createTime;
  private long scheduleToExecuteTime;
  private long ExecutionCompleteTime;

  private Command() {
  }

  public Command(ActionBase[] actions) {
    this.actions = actions.clone();
  }

  public long getRuleId() {
    return ruleId;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getId() {
    return id;
  }

  public int getState() {
    return state;
  }

  public ActionBase[] getActions() {
    return actions == null ? null : actions.clone();
  }

  public String toString() {
    return "Rule-" + ruleId + "-Cmd-" + id;
  }
  
  @Override
  public void run() {
    for (ActionBase act : actions) {
      if (ActionExecutor.run(act)) {
        break;
      }
    }
  }
}