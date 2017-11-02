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
package org.smartdata.server.engine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smartdata.server.engine.cmdlet.HazelcastExecutorService;
import org.smartdata.server.engine.cmdlet.agent.AgentExecutorService;
import org.smartdata.server.engine.cmdlet.agent.NodeInfo;

import java.util.List;

/**
 * Manage peers in SSM cluster.
 *
 */
public class NodesManager {
  static final Logger LOG = LoggerFactory.getLogger(NodesManager.class);
  private HazelcastExecutorService hazelExecSrv;
  private AgentExecutorService agentExecSrv;

  public List<NodeInfo> nodes;

  public List<NodeInfo> getStandbySmartServers() {
    return null;
  }

  public List<NodeInfo> getSmartAgents() {
    return null;
  }

  public List<NodeInfo> getNodes() {
    return nodes;
  }

  public NodesManager(HazelcastExecutorService hazelcastExecService,
      AgentExecutorService agentExecService) {
    this.hazelExecSrv = hazelcastExecService;
    this.agentExecSrv = agentExecService;
  }

  public void dispatch() {
  }

}
