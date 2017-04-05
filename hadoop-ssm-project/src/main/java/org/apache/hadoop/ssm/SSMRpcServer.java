package org.apache.hadoop.ssm;


import com.google.protobuf.BlockingService;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hdfs.DFSUtil;
import org.apache.hadoop.ipc.ProtobufRpcEngine;
import org.apache.hadoop.ipc.RPC;
import org.apache.hadoop.ssm.rpc.*;

import java.io.IOException;
import java.net.InetSocketAddress;

public class SSMRpcServer implements ClientSSMProtocol {

  protected final RPC.Server clientRpcServer;
  protected final InetSocketAddress clientRpcAddress;
  protected int serviceHandlerCount = 1;

  public SSMRpcServer(Configuration conf, SSMServer ssmServer) throws IOException {

    InetSocketAddress rpcAddr = ssmServer.getRpcServerAddress(conf);

    RPC.setProtocolEngine(conf, ClientSSMProtocolPB.class, ProtobufRpcEngine.class);

    ClientSSMProtocolServerSideTranslatorPB clientSSMProtocolServerSideTranslatorPB =
            new ClientSSMProtocolServerSideTranslatorPB(this);
    BlockingService clientSSMPbService = Add.AddService
            .newReflectiveBlockingService(clientSSMProtocolServerSideTranslatorPB);

    clientRpcServer = new RPC.Builder(conf)
            .setProtocol(ClientSSMProtocolPB.class)
            .setInstance(clientSSMPbService)
            .setBindAddress(rpcAddr.getHostName())
            .setPort(rpcAddr.getPort())
            .setNumHandlers(serviceHandlerCount)
            .setVerbose(true)
            .build();

    InetSocketAddress listenAddr = clientRpcServer.getListenerAddress();
    clientRpcAddress = new InetSocketAddress(
            rpcAddr.getHostName(), listenAddr.getPort());

    DFSUtil.addPBProtocol(conf, ClientSSMProtocolPB.class, clientSSMPbService,
            clientRpcServer);

  }

  void start() {
    if (clientRpcServer != null) {
      clientRpcServer.start();
    }
  }

  @Override
  public int add(int para1, int para2) {
    return para1 + para2;
  }

  public static void main(String[] args) throws IOException {
    SSMRpcServer ssmRpcServer = new SSMRpcServer(new Configuration(), new SSMServer());
    ssmRpcServer.start();
  }

}
