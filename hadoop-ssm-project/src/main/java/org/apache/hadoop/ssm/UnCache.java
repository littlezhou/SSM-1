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
import org.apache.hadoop.hdfs.DFSClient;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by hadoop on 17-2-9.
 */
public class UnCache extends ActionBase {
  private static final Log LOG = LogFactory.getLog(UnCache.class);
  private static UnCache instance;
  private long id;
  private Configuration conf;
  private LinkedBlockingQueue<String> actionEvents;

  public UnCache(DFSClient client) {
    super(client);
  }

  public static UnCache getInstance(DFSClient dfsClient,
                                    Configuration conf) {
    if (instance == null) {
      instance = new UnCache(dfsClient);
      instance.conf = conf;
    }
    return instance;
  }

  @Override
  public void initial(String[] args) {
    this.id = Long.parseLong(args[0]);
  }

  @Override
  public boolean execute() {
    removeDirective(id);
    return false;
  }

  private void removeDirective(long id) {
    try {
      dfsClient.removeCacheDirective(id);
      LOG.info("*" + System.currentTimeMillis() + " : " + id + " -> " + "unCache");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
