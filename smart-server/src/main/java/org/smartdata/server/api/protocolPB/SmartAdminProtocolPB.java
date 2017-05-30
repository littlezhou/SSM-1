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
package org.smartdata.server.api.protocolPB;

import com.google.protobuf.RpcController;
import com.google.protobuf.ServiceException;
import org.apache.hadoop.ipc.ProtocolInfo;
import org.smartdata.server.api.protocol.AdminServerProto.CheckRuleRequestProto;
import org.smartdata.server.api.protocol.AdminServerProto.CheckRuleResponseProto;
import org.smartdata.server.api.protocol.AdminServerProto.GetRuleInfoRequestProto;
import org.smartdata.server.api.protocol.AdminServerProto.GetRuleInfoResponseProto;
import org.smartdata.server.api.protocol.AdminServerProto.GetServiceStateRequestProto;
import org.smartdata.server.api.protocol.AdminServerProto.GetServiceStateResponseProto;
import org.smartdata.server.api.protocol.AdminServerProto.ListRulesInfoRequestProto;
import org.smartdata.server.api.protocol.AdminServerProto.ListRulesInfoResponseProto;
import org.smartdata.server.api.protocol.AdminServerProto.SubmitRuleRequestProto;
import org.smartdata.server.api.protocol.AdminServerProto.SubmitRuleResponseProto;
import org.smartdata.server.api.protocol.AdminServerProto.DeleteRuleResponseProto;
import org.smartdata.server.api.protocol.AdminServerProto.ActivateRuleResponseProto;
import org.smartdata.server.api.protocol.AdminServerProto.DisableRuleResponseProto;
import org.smartdata.server.api.protocol.AdminServerProto.DeleteRuleRequestProto;
import org.smartdata.server.api.protocol.AdminServerProto.ActivateRuleRequestProto;
import org.smartdata.server.api.protocol.AdminServerProto.DisableRuleRequestProto;
import org.smartdata.server.api.protocol.AdminServerProto.GetCommandInfoResponseProto;
import org.smartdata.server.api.protocol.AdminServerProto.GetCommandInfoRequestProto;
import org.smartdata.server.api.protocol.AdminServerProto.ListCommandInfoResponseProto;
import org.smartdata.server.api.protocol.AdminServerProto.ListCommandInfoRequestProto;
import org.smartdata.server.api.protocol.AdminServerProto.ActivateCommandResponseProto;
import org.smartdata.server.api.protocol.AdminServerProto.ActivateCommandRequestProto;
import org.smartdata.server.api.protocol.AdminServerProto.DisableCommandResponseProto;
import org.smartdata.server.api.protocol.AdminServerProto.DisableCommandRequestProto;
import org.smartdata.server.api.protocol.AdminServerProto.DeleteCommandResponseProto;
import org.smartdata.server.api.protocol.AdminServerProto.DeleteCommandRequestProto;

@ProtocolInfo(protocolName = "org.apache.hadoop.ssm.protocolPB.SmartAdminProtocolPB",
    protocolVersion = 1)
public interface SmartAdminProtocolPB {

  GetServiceStateResponseProto
  getServiceState(RpcController controller,
      GetServiceStateRequestProto req) throws ServiceException;

  GetRuleInfoResponseProto getRuleInfo(RpcController controller,
      GetRuleInfoRequestProto req) throws ServiceException;

  SubmitRuleResponseProto submitRule(RpcController controller,
      SubmitRuleRequestProto req) throws ServiceException;

  CheckRuleResponseProto checkRule(RpcController controller,
      CheckRuleRequestProto req) throws ServiceException;

  ListRulesInfoResponseProto listRulesInfo(RpcController controller,
      ListRulesInfoRequestProto req) throws ServiceException;

  DeleteRuleResponseProto deleteRule(RpcController controller,
      DeleteRuleRequestProto req) throws ServiceException;

  ActivateRuleResponseProto activateRule(RpcController controller,
      ActivateRuleRequestProto req) throws ServiceException;

  DisableRuleResponseProto disableRule(RpcController controller,
      DisableRuleRequestProto req) throws ServiceException;
  
  GetCommandInfoResponseProto getCommandInfo(RpcController controller,
      GetCommandInfoRequestProto req) throws ServiceException;

  ListCommandInfoResponseProto listCommandInfo(RpcController controller,
      ListCommandInfoRequestProto req) throws ServiceException;

  ActivateCommandResponseProto activateCommand(RpcController controller,
      ActivateCommandRequestProto req) throws ServiceException;

  DisableCommandResponseProto disableCommand(RpcController controller,
      DisableCommandRequestProto req) throws ServiceException;

  DeleteCommandResponseProto deleteCommand(RpcController controller,
      DeleteCommandRequestProto req) throws ServiceException;
}