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
package org.apache.hadoop.ssm.rule;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Schedule the execution.
 */
public class ExecutorScheduler {
  Map<Long, Runnable> mapTasks = new ConcurrentHashMap<>();
  private ScheduledExecutorService service;

  public ExecutorScheduler() {
    service = Executors.newScheduledThreadPool(2);
  }

  private class EventGenTask implements Runnable {
    private long id;
    private ScheduleInfo scheduleInfo;
    private int triggered;

    public EventGenTask(long id) {
      this.id = id;
    }

    @Override
    public void run() {
      triggered++;
      if (triggered <= scheduleInfo.getRounds()) {
      } else {
        exitSchduler();
      }
    }
  }

  public boolean addPeriodicityTask(long id, ScheduleInfo schInfo, Runnable work) {
    long now = System.currentTimeMillis();
    service.scheduleAtFixedRate(new EventGenTask(id), schInfo.getStartTime() - now,
        schInfo.getRate(), TimeUnit.MILLISECONDS);
    return true;
  }

  private void exitSchduler() {
    String[] temp = new String[1];
    temp[1] += "The exception is created deliberately";
  }
}
