package org.apache.hadoop.ssm.rpc;

import com.google.protobuf.RpcController;
import org.apache.hadoop.ssm.rpc.ClientProto.AddParameters;
import org.apache.hadoop.ssm.rpc.ClientProto.AddResult;

public class ClientSSMProtocolServerSideTranslatorPB implements ClientSSMProtocolPB,
        Add.AddService.BlockingInterface {
  final private ClientSSMProtocol server;

  public ClientSSMProtocolServerSideTranslatorPB(ClientSSMProtocol server) {
    this.server = server;
  }

  public AddResult add(RpcController controller, AddParameters p) {
    // TODO Auto-generated method stub
    ClientProto.AddResult.Builder builder = ClientProto.AddResult
            .newBuilder();
    int result = server.add(p.getPara1(), p.getPara2());
    builder.setResult(result);
    return builder.build();
  }

}