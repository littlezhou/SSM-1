///**
// * Licensed to the Apache Software Foundation (ASF) under one
// * or more contributor license agreements.  See the NOTICE file
// * distributed with this work for additional information
// * regarding copyright ownership.  The ASF licenses this file
// * to you under the Apache License, Version 2.0 (the
// * "License"); you may not use this file except in compliance
// * with the License.  You may obtain a copy of the License at
// *
// *     http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//package org.smartdata.hdfs.scheduler;
//
//import org.smartdata.SmartContext;
//import org.smartdata.metastore.ActionSchedulerService;
//import org.smartdata.metastore.MetaStore;
//import org.smartdata.model.ActionInfo;
//import org.smartdata.model.LaunchAction;
//import org.smartdata.model.action.ScheduleResult;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//public class DefaultActionSchedulerService extends ActionSchedulerService {
//
//  public DefaultActionSchedulerService(
//      SmartContext context, MetaStore metaStore) {
//    super(context, metaStore);
//  }
//
//  public void init() throws IOException {
//  }
//
//  public void start() throws IOException {
//  }
//
//  public void stop() throws IOException {
//  }
//
//  public List<String> getSupportedActions() {
//    return new ArrayList<>();
//  }
//
//  public ScheduleResult onSchedule(LaunchAction action) {
//    return ScheduleResult.SUCCESS;
//  }
//
//  public void onPreDispatch(LaunchAction action) {
//  }
//
//  public boolean onSubmit(ActionInfo actionInfo) {
//    return true;
//  }
//
//  public void onActionFinished(ActionInfo actionInfo) {
//  }
//}
