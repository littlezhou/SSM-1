package org.apache.hadoop.ssm.rpc;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.ProtobufRpcEngine;
import org.apache.hadoop.ipc.RPC;

import java.io.IOException;
import java.net.InetSocketAddress;


public class ProtoRpcClient {
  final static long VERSION = 1;
  public static void main(String[] args) throws IOException {
    Configuration conf = new Configuration();
    String ADDRESS = "localhost";
    int port = 9998;
    RPC.setProtocolEngine(conf, ClientSSMProtocolPB.class, ProtobufRpcEngine.class);
    ClientSSMProtocolPB proxy = RPC.getProxy(ClientSSMProtocolPB.class, VERSION,  new InetSocketAddress(ADDRESS,port),
            conf);

    ClientSSMProtocol add = new ClientSSMProtocolTranslatorPB(proxy);
    int result = add.add(100, 200);
    System.out.println("client result:" + result);
  }
}