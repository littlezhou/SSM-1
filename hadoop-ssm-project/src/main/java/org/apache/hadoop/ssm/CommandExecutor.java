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

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.util.Daemon;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Schedule and execute commands passed down.
 */
public class CommandExecutor implements Runnable {
  private List<Command> cmdsWaiting = new LinkedList<>();
  private Map<Long, Command> cmdsInExecution = new HashMap<>();

  private Daemon commandExecutorThread;
  private ThreadGroup execThreadGroup;

  private SSM ssm;

  public CommandExecutor(SSM ssm, Configuration conf) {
    //ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
    this.ssm = ssm;
    execThreadGroup = new ThreadGroup("CommandExecutorWorker");
    execThreadGroup.setDaemon(true);
  }

  /**
   * Start CommandExecutor.
   */
  public synchronized void start() {
    commandExecutorThread = new Daemon(this);
    commandExecutorThread.setName(this.getClass().getCanonicalName());
    commandExecutorThread.start();
  }

  /**
   * Stop CommandExecutor
   */
  public synchronized void stop() {
    if (commandExecutorThread == null) {
      return;
    }

    commandExecutorThread.interrupt();
    execThreadGroup.interrupt();
    execThreadGroup.destroy();
    try {
      commandExecutorThread.join(1000);
    } catch (InterruptedException e) {
    }
    commandExecutorThread = null;
    execThreadGroup = null;
  }

  @Override
  public void run() {
    boolean running = true;
    while (running) {
      try {
        if (execThreadGroup.activeCount() <= 5) {  // TODO: use configure value
          Command toExec = schedule();
          cmdsInExecution.put(new Long(toExec.getId()), toExec);
          if (toExec != null) {
            new Daemon(execThreadGroup, toExec).start();
          } else {
            Thread.sleep(1000);
          }
        } else {
          Thread.sleep(1000);
        }
      } catch (InterruptedException e) {
        running = false;
      }
    }
  }


  /**
   * Add a command to CommandExecutor for execution.
   * @param cmd
   */
  public synchronized void addCommand(Command cmd) {
    cmdsWaiting.add(cmd);
  }

  /**
   * Get command to for execution.
   * @return
   */
  private synchronized Command schedule() {
    // currently FIFO
    if (cmdsWaiting.size() == 0) {
      return null;
    }
    return cmdsWaiting.remove(0);
  }



}
