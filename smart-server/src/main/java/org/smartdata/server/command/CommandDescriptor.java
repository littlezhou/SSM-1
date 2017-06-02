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
package org.smartdata.server.command;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class CommandDescriptor {
  private long ruleId;
  private List<String> actionNames = new ArrayList<>();
  private List<String[]> actionArgs = new ArrayList<>();

  private static final String REG_ACTION_NAME = "^[a-zA-Z_]+[a-zA-Z0-9_]*\\s+";
  private static final String REG_OPT_CHARS = "^\\S+\\s+";
  private static final String REG_OPT_STRING = "^\\S+\\s+";


  public CommandDescriptor() {
  }

  public long getRuleId() {
    return ruleId;
  }

  public void setRuleId(long ruleId) {
    this.ruleId = ruleId;
  }

  public void addAction(String actionName, String[] args) {
    actionNames.add(actionName);
    actionArgs.add(args);
  }

  public String getActionName(int index) {
    return actionNames.get(index);
  }

  public String[] getActionArgs(int index) {
    return actionArgs.get(index);
  }

  public int getNumActions() {
    return actionNames.size();
  }

  // TODO: to be implemented
  /**
   * Construct an CommandDescriptor from command string.
   * @param cmdString
   * @return
   * @throws ParseException
   */
  public static CommandDescriptor fromCommandString(String cmdString)
      throws ParseException {
    return new CommandDescriptor();
  }

  private void parseCommandString(String cmdStr) throws ParseException {
    if (cmdStr == null) {
      return;
    }
    String cmd = cmdStr.trim();
    boolean matched = cmd.matches(REG_ACTION_NAME);
    if (!matched) {
      throw new ParseException("Invalid action name: " + cmd, 0);
    }
    String cmdArgs = cmd.replaceFirst(REG_ACTION_NAME, "");
    String actionName = cmd.substring(0,
        cmd.length() - cmdArgs.length()).trim();


    int start = 0;
    int end = cmdArgs.length();
    List<String> args = new ArrayList<>();

    while (start < end) {
      int i = start;
      if (cmdArgs.charAt(i) != '"') {
        i++;
        for (; i < end; i++) {
          char c = cmdArgs.charAt(i);
          if (c == ' ' || c == '\t' || c == '\r' || c == '\n' || c == ';') {
            break;
          }
        }
        args.add(cmdArgs.substring(start, i).trim());
        start = i + 1;
      } else {
        // TODO: check more cases
      }
    }

    while (cmdArgs.length() > 0) {
      if (cmdArgs.charAt(0) == '"') {

      } else {
      }
    }

    boolean optStart = true;
    for (int i = 0; i < cmdArgs.length(); i++) {
      if (optStart && cmdArgs.charAt(i))
    }
  }
}
