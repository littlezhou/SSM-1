/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.hadoop.ssm;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.CacheFlag;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;
import org.apache.hadoop.hdfs.DFSClient;
import org.apache.hadoop.hdfs.protocol.CacheDirectiveEntry;
import org.apache.hadoop.hdfs.protocol.CacheDirectiveInfo;
import org.apache.hadoop.hdfs.protocol.CachePoolEntry;
import org.apache.hadoop.hdfs.protocol.CachePoolInfo;

import java.io.IOException;
import java.util.EnumSet;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by hadoop on 17-1-15.
 */
public class MoveToCache extends ActionBase {
  private static final Log LOG = LogFactory.getLog(MoveToCache.class);
  private static MoveToCache instance;
  private DFSClient dfsClient;
  private String fileName;
  private Configuration conf;
  private LinkedBlockingQueue<String> actionEvents;
  private final String SSMPOOL = "SSMPool";

  public MoveToCache(DFSClient client) {
    super(client);
    this.dfsClient = client;
    this.actionEvents = new LinkedBlockingQueue<String>();
  }

  public static MoveToCache getInstance(DFSClient dfsClient,
                                        Configuration conf) {
    if (instance == null) {
      instance = new MoveToCache(dfsClient);
      instance.conf = conf;
    }
    return instance;
  }

  public void initial(String[] args) {
    fileName = args[0];
  }

  /**
   * Execute an action.
   *
   * @return true if success, otherwise return false.
   */
  public boolean execute() {
    Action action = Action.getActionType("cache");
    MoveToCache.getInstance(dfsClient, conf).addActionEvent(fileName);
    MoveToCache.getInstance(dfsClient, conf).runCache(fileName);
    return true;
  }

  public void addActionEvent(String fileName) {
    try {
      actionEvents.put(fileName);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  private void runCache(String fileName) {
    if (createPool()) {
      if (isCached(fileName)) {
        return;
      }
      LOG.info("*" + System.currentTimeMillis() + " : " + fileName + " -> " + "cache");
      addDirective(fileName);
    } else {
      LOG.info("*" + System.currentTimeMillis() + " : " + "createPool failed!");
    }
  }

  private boolean createPool() {
    try {
      RemoteIterator<CachePoolEntry> poolEntries = dfsClient.listCachePools();
      if (isSSMPoolExist(poolEntries)) {
        return true;
      } else {
        dfsClient.addCachePool(new CachePoolInfo(SSMPOOL));
        if (isSSMPoolExist(poolEntries)) return true;
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return false;
  }

  private boolean isSSMPoolExist(RemoteIterator<CachePoolEntry> poolEntries) {
    try {
      while (poolEntries.hasNext()) {
        CachePoolEntry poolEntry = poolEntries.next();
        if (poolEntry.getInfo().getPoolName().equals(SSMPOOL)) {
          return true;
        }
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return false;
  }

  /**
   * check file isCached
   *
   * @return true if success, otherwise return false.
   */
  public boolean isCached(String fileName) {
    CacheDirectiveInfo.Builder filterBuilder = new CacheDirectiveInfo.Builder();
    filterBuilder.setPath(new Path(fileName));
    CacheDirectiveInfo filter = filterBuilder.build();
    try {
      RemoteIterator<CacheDirectiveEntry> directiveEntries = dfsClient.listCacheDirectives(filter);
      return directiveEntries.hasNext();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private void addDirective(String fileName) {
    CacheDirectiveInfo.Builder filterBuilder = new CacheDirectiveInfo.Builder();
    filterBuilder.setPath(new Path(fileName));
    filterBuilder.setPool(SSMPOOL);
    CacheDirectiveInfo filter = filterBuilder.build();
    EnumSet<CacheFlag> flags = EnumSet.noneOf(CacheFlag.class);
    try {
      dfsClient.addCacheDirective(filter, flags);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}