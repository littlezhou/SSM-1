package org.apache.hadoop.ssm;


import org.apache.hadoop.conf.Configuration;
import org.junit.Test;

import java.io.IOException;

public class SSMRpcServerTest {

  @Test
  public void run () throws IOException {
    SSMServer ssmServer = new SSMServer();
    Configuration conf = new Configuration();
    SSMRpcServer ssmRpcServer = new SSMRpcServer(conf,ssmServer);
    ssmRpcServer.start();

  }
}
