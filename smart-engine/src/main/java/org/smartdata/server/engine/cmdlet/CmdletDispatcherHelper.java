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
package org.smartdata.server.engine.cmdlet;

import com.google.common.eventbus.Subscribe;
import org.smartdata.server.engine.EngineEventBus;
import org.smartdata.server.engine.message.AddNodeMessage;
import org.smartdata.server.engine.message.NodeMessage;
import org.smartdata.server.engine.message.RemoveNodeMessage;

import java.util.LinkedList;
import java.util.List;

public class CmdletDispatcherHelper {
  private static CmdletDispatcherHelper inst = new CmdletDispatcherHelper();
  private static List<NodeMessage> msgs = new LinkedList<>();
  private static List<Boolean> opers = new LinkedList<>();
  private static CmdletDispatcher dispatcher = null;

  static {
    EngineEventBus.register(inst);
  }

  public static void register(CmdletDispatcher dispatcher) {
    synchronized (msgs) {
      for (int i = 0; i < msgs.size(); i++) {
        dispatcher.onNodeMessage(msgs.get(i), opers.get(i));
      }
      msgs.clear();
    }
  }

  public static void init() {
  }


  @Subscribe
  public static void onAddNodeMessage(AddNodeMessage msg) {
    onNodeMessage(msg, true);
  }

  @Subscribe
  public static void onRemoveNodeMessage(RemoveNodeMessage msg) {
    onNodeMessage(msg, false);
  }

  private static void onNodeMessage(NodeMessage msg, boolean add) {
    synchronized (msgs) {
      if (dispatcher == null) {
        msgs.add(msg);
        opers.add(add);
      } else {
        dispatcher.onNodeMessage(msg, add);
      }
    }
  }

  public static void stop() {
    synchronized (msgs) {
      EngineEventBus.unregister(inst);
    }
  }
}
