package org.apache.hadoop.ssm.rpc;

public class ClientSSMProtocolTranslatorPB implements ClientSSMProtocol {
  final private ClientSSMProtocolPB rpcProxy;

  public ClientSSMProtocolTranslatorPB(ClientSSMProtocolPB proxy) {
    this.rpcProxy = proxy;

  }

  public int add(int para1, int para2) {
    // TODO Auto-generated method stub
    ClientProto.AddParameters req = ClientProto.AddParameters.newBuilder()
            .setPara1(para1).setPara2(para2).build();
    return rpcProxy.add(null, req).getResult();
  }

}