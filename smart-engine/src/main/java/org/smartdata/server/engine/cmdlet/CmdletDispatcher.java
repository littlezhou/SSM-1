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

import com.google.common.collect.ListMultimap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smartdata.SmartContext;
import org.smartdata.conf.SmartConfKeys;
import org.smartdata.model.LaunchAction;
import org.smartdata.model.action.ActionScheduler;
import org.smartdata.server.engine.CmdletManager;
import org.smartdata.server.engine.cmdlet.agent.DispatchInfo;
import org.smartdata.server.engine.cmdlet.message.LaunchCmdlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CmdletDispatcher {
  private static final Logger LOG = LoggerFactory.getLogger(CmdletDispatcher.class);
  private final Queue<Long> pendingCmdlets;
  private final CmdletManager cmdletManager;
  private final List<Long> runningCmdlets;
  private final Map<Long, LaunchCmdlet> idToLaunchCmdlet;
  private final ListMultimap<String, ActionScheduler> schedulers;

  private final ScheduledExecutorService schExecService;

  private List<CmdletExecutorService> cmdExecServices;
  // TODO: to be refined
  private final Map<String, Integer> execCmdletSlots = new ConcurrentHashMap<>();
  private final int defaultSlots;
  private int index;

  public CmdletDispatcher(SmartContext smartContext, CmdletManager cmdletManager,
      Queue<Long> scheduledCmdlets, Map<Long, LaunchCmdlet> idToLaunchCmdlet,
      List<Long> runningCmdlets, ListMultimap<String, ActionScheduler> schedulers) {
    this.cmdletManager = cmdletManager;
    this.pendingCmdlets = scheduledCmdlets;
    this.runningCmdlets = runningCmdlets;
    this.idToLaunchCmdlet = idToLaunchCmdlet;
    this.schedulers = schedulers;
    defaultSlots = smartContext.getConf().getInt(SmartConfKeys.SMART_CMDLET_EXECUTORS_KEY,
        SmartConfKeys.SMART_CMDLET_EXECUTORS_DEFAULT);

    this.cmdExecServices = new ArrayList<>();
    boolean disableLocal = smartContext.getConf().getBoolean(
        SmartConfKeys.SMART_ACTION_LOCAL_EXECUTION_DISABLED_KEY,
        SmartConfKeys.SMART_ACTION_LOCAL_EXECUTION_DISABLED_DEFAULT);
    if (!disableLocal) {
      this.cmdExecServices.add(
          new LocalCmdletExecutorService(smartContext.getConf(), cmdletManager));
    }
    this.index = 0;

    schExecService = Executors.newScheduledThreadPool(1);
    schExecService.scheduleAtFixedRate(
        new DispatchTask(this), 200, 100, TimeUnit.MILLISECONDS);
  }

  public void registerExecutorService(CmdletExecutorService executorService) {
    this.cmdExecServices.add(executorService);
  }

  public boolean canDispatchMore() {
    for (CmdletExecutorService service : cmdExecServices) {
      if (service.canAcceptMore()) {
        return true;
      }
    }
    return false;
  }

  public void dispatch(LaunchCmdlet cmdlet) {
    //Todo: optimize dispatching
    if (canDispatchMore()) {
      while (!cmdExecServices.get(index % cmdExecServices.size()).canAcceptMore()) {
        index += 1;
      }
      CmdletExecutorService selected = cmdExecServices.get(index % cmdExecServices.size());
      selected.execute(cmdlet);
      LOG.info(
          String.format(
              "Dispatching cmdlet->[%s] to executor service %s",
              cmdlet.getCmdletId(), selected.getClass()));
      index += 1;
    }
  }

  public void dispatch(DispatchInfo dispatchInfo, LaunchCmdlet cmdlet) {

  }

  //Todo: pick the right service to stop cmdlet
  public void stop(long cmdletId) {
    for (CmdletExecutorService service : cmdExecServices) {
      service.stop(cmdletId);
    }
  }

  //Todo: move this function to a proper place
  public void shutDownExcutorServices() {
    for (CmdletExecutorService service : cmdExecServices) {
      service.shutdown();
    }
  }

  public LaunchCmdlet getNextCmdletToRun() throws IOException {
    Long cmdletId = pendingCmdlets.poll();
    if (cmdletId == null) {
      return null;
    }
    LaunchCmdlet launchCmdlet = idToLaunchCmdlet.get(cmdletId);
    runningCmdlets.add(cmdletId);
    return launchCmdlet;
  }

  private class DispatchTask implements Runnable {
    private final CmdletDispatcher dispatcher;

    public DispatchTask(CmdletDispatcher dispatcher) {
      this.dispatcher = dispatcher;
    }

    @Override
    public void run() {
      while (dispatcher.canDispatchMore()) {
        try {
          LaunchCmdlet launchCmdlet = getNextCmdletToRun();
          if (launchCmdlet == null) {
            break;
          } else {
            cmdletPreExecutionProcess(launchCmdlet);
            dispatcher.dispatch(launchCmdlet);
          }
        } catch (IOException e) {
          LOG.error("Cmdlet dispatcher error", e);
        }
      }
    }
  }

  public void cmdletPreExecutionProcess(LaunchCmdlet cmdlet) {
    for (LaunchAction action : cmdlet.getLaunchActions()) {
      for (ActionScheduler p : schedulers.get(action.getActionType())) {
        p.onPreDispatch(action);
      }
    }
  }
}
