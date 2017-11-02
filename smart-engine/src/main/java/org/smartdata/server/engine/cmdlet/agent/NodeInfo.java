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
package org.smartdata.server.engine.cmdlet.agent;

import org.smartdata.model.ExecutorType;

/**
 * Represent each nodes that SSM services (SmartServers and SmartAgents) running on.
 *
 */
public class NodeInfo {
  private String hostName;
  private String ip;
  private String port;
  private ExecutorType executorType;

  public NodeInfo(String hostName, String ip, String port, ExecutorType executorType) {
    this.hostName = hostName;
    this.ip = ip;
    this.port = port;
    this.executorType = executorType;
  }

  public String getHostName() {
    return hostName;
  }

  public void setHostName(String hostName) {
    this.hostName = hostName;
  }

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public String getPort() {
    return port;
  }

  public void setPort(String port) {
    this.port = port;
  }

  public ExecutorType getExecutorType() {
    return executorType;
  }

  public void setExecutorType(ExecutorType executorType) {
    this.executorType = executorType;
  }

  public static class Builder {
    private String hostName;
    private String ip;
    private String port;
    private ExecutorType executorType;

    public Builder setHostName(String hostName) {
      this.hostName = hostName;
      return this;
    }

    public Builder setIp(String ip) {
      this.ip = ip;
      return this;
    }

    public Builder setPort(String port) {
      this.port = port;
      return this;
    }

    public Builder setExecutorType(ExecutorType executorType) {
      this.executorType = executorType;
      return this;
    }

    public NodeInfo build() {
      return new NodeInfo(hostName, ip, port, executorType);
    }
  }
}
