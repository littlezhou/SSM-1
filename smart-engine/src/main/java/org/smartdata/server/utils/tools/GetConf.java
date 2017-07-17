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
package org.smartdata.server.utils.tools;

import com.hazelcast.config.ClasspathXmlConfig;
import org.smartdata.server.cluster.HazelcastInstanceProvider;

import java.io.PrintStream;
import java.util.List;

public class GetConf {
  private static final String USAGE =
      "Usage: GetConf <Option>\n"
      + "\tOption can be:\n"
      + "\t  HazelcastMembers  Print members in hazelcast.xml\n"
      + "\t  Help              Print this usage\n";

  public static int getHazelcastMembers(PrintStream ps) throws Exception {
    ClasspathXmlConfig conf =
        new ClasspathXmlConfig(HazelcastInstanceProvider.CONFIG_FILE);
    try {
      List<String> members = conf.getNetworkConfig().getJoin().getTcpIpConfig().getMembers();
      int found = 0;
      for (String m : members) {
        if (m.length() != 0) {
          found++;
          ps.println(m);
        }
      }

      if (found == 0) {
        ps.println("No valid Hazelcast Members found");
        return 2;
      }

      return 0;
    } catch (NullPointerException e) {
      ps.println("Hazelcast Members not found in "
          + HazelcastInstanceProvider.CONFIG_FILE);
    }
    return 1;
  }

  public static void main(String[] args) {
    if (args == null || args.length != 1) {
      System.out.println(USAGE);
      System.exit(1);
    }

    int ret = 1;

    try {
      if (args[0].equalsIgnoreCase("HazelcastMembers")) {
        ret = getHazelcastMembers(System.out);
      } else if (args[0].equalsIgnoreCase("Help")) {
        System.out.println(USAGE);
        ret = 0;
      }
    } catch (Throwable t) {
      //t.printStackTrace();
      System.out.println(t.getMessage());
    }
    System.exit(ret);
  }
}
