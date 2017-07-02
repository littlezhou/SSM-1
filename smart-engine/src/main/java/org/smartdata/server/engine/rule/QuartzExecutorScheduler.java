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
package org.smartdata.server.engine.rule;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import java.io.IOException;

public class QuartzExecutorScheduler {
  private static SchedulerFactory sf = new StdSchedulerFactory();
  private Scheduler scheduler;

  public void init() throws IOException {
    try {
      scheduler = sf.getScheduler();
      JobDetail jobDetail = JobBuilder.newJob(ExecuteRuleJob.class)
          .withIdentity("ExecuteRuleJob", "ExecuteRuleJobGroup").build();
      Trigger trigger = TriggerBuilder.newTrigger()
          .withIdentity("Trigger", "TriggerGroup").build();
      scheduler.scheduleJob(jobDetail, trigger);
    } catch (SchedulerException e) {
      throw new IOException(e);
    }
  }

  public void start() {
    try {
      if (scheduler != null) {
        scheduler.start();
      }
    } catch (Exception e) {
    }
  }

  public void stop() {
    try {
      if (scheduler != null) {
        scheduler.shutdown();
      }
    } catch (Exception e) {
    }
  }
}
