package org.apache.hadoop.ssm;

import org.apache.hadoop.ssm.sql.DBAdapter;

import java.io.IOException;

public interface ModuleSequenceProto {
  enum State {
    INITING,
    INITED,
    STARTING,
    STARTED,
    STOPPING,
    STOPPED,
    JOINING,
    JOINED
  }

  /**
   * Init module using info from configuration and database.
   * @param dbAdapter
   * @return
   * @throws IOException
   */
  boolean init(DBAdapter dbAdapter) throws IOException;

  /**
   * After start call, all services and public calls should work.
   * @return
   * @throws IOException
   */
  boolean start() throws IOException;

  /**
   * After stop call, all states in database will not be changed anymore.
   * @throws IOException
   */
  void stop() throws IOException;

  /**
   * Stop threads and other cleaning jobs.
   * @throws IOException
   */
  void join() throws IOException;
}
