package org.apache.hadoop.ssm.rpc;

import com.google.protobuf.RpcController;
import org.apache.hadoop.ipc.ProtocolInfo;
import org.apache.hadoop.ssm.rpc.ClientProto.AddParameters;

@ProtocolInfo(protocolName = "rpcTest.protobuf.AddProtocolPB",
        protocolVersion = 1)
public interface ClientSSMProtocolPB {
  public ClientProto.AddResult add(RpcController controller, AddParameters p) ;
}