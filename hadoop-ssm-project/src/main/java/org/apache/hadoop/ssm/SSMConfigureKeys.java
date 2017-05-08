package org.apache.hadoop.ssm;

/**
 * This class contains the configure keys needed by SSM.
 */
public class SSMConfigureKeys {
  public final static String DFS_SSM_ENABLED_KEY = "dfs.ssm.enabled";

  public final static String DFS_SSM_NAMENODE_RPCSERVER_KEY = "dfs.ssm.namenode.rpcserver";

  //ssm
  public final static String DFS_SSM_RPC_ADDRESS_KEY = "dfs.ssm.rpc-address";
  public final static String DFS_SSM_RPC_ADDRESS_DEFAULT = "localhost:7042";
  public final static String DFS_SSM_HTTP_ADDRESS_KEY = "dfs.ssm.http-address";
  public final static String DFS_SSM_HTTP_ADDRESS_DEFAULT = "localhost:7045";
  public final static String DFS_SSM_HTTPS_ADDRESS_KEY = "dfs.ssm.https-address";
}
