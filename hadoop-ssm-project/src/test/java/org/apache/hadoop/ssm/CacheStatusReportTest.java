package org.apache.hadoop.ssm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.apache.hadoop.fs.FileSystemTestHelper;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;
import org.apache.hadoop.hdfs.DFSClient;
import org.apache.hadoop.hdfs.DFSConfigKeys;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.hadoop.hdfs.HdfsConfiguration;
import org.apache.hadoop.hdfs.MiniDFSCluster;
import org.apache.hadoop.hdfs.protocol.CacheDirectiveEntry;
import org.apache.hadoop.hdfs.protocol.CacheDirectiveInfo;
import org.apache.hadoop.hdfs.protocol.CacheDirectiveStats;
import org.apache.hadoop.hdfs.protocol.CachePoolInfo;
import org.apache.hadoop.hdfs.server.namenode.NameNode;
import org.apache.hadoop.io.nativeio.NativeIO;
import org.apache.hadoop.test.GenericTestUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Supplier;

/**
 * Created by cc on 17-3-7.
 */
public class CacheStatusReportTest {
  private static final String REPLICATION_KEY = "3";
  private static final int DEFAULT_BLOCK_SIZE = 1024;
  private static final long DEFAULT_CACHE_SIZE = 64 * 1024;
  private static NativeIO.POSIX.CacheManipulator prevCacheManipulator;
  private static HdfsConfiguration conf;

  private static MiniDFSCluster cluster = null;
  private static DistributedFileSystem dfs;
  private static DFSClient dfsClient;
  static private NameNode namenode;

  @Before
  public void setUp() throws Exception {
    conf = new HdfsConfiguration();
    conf.setLong(DFSConfigKeys.DFS_BLOCK_SIZE_KEY, DEFAULT_BLOCK_SIZE);
    conf.setInt(DFSConfigKeys.DFS_BYTES_PER_CHECKSUM_KEY, DEFAULT_BLOCK_SIZE);
    conf.setStrings(DFSConfigKeys.DFS_REPLICATION_KEY, REPLICATION_KEY);
    conf.setLong(DFSConfigKeys.DFS_DATANODE_MAX_LOCKED_MEMORY_KEY, DEFAULT_CACHE_SIZE);
    //cacheManager rescan time
    conf.setLong(DFSConfigKeys.DFS_NAMENODE_PATH_BASED_CACHE_REFRESH_INTERVAL_MS, 1000);
    prevCacheManipulator = NativeIO.POSIX.getCacheManipulator();
    NativeIO.POSIX.setCacheManipulator(new NativeIO.POSIX.NoMlockCacheManipulator());

    cluster = new MiniDFSCluster.Builder(conf).numDataNodes(3).build();
    cluster.waitActive();
    dfs = cluster.getFileSystem();
    dfsClient = cluster.getFileSystem().getClient();
    namenode = cluster.getNameNode();
    init();
  }

  //add a file and put cache in memory 
  private void init() throws IOException, InterruptedException {
    //file size 4M has been created
    FileSystemTestHelper.createFile(dfs, new Path("/test/file"), 3,
            DEFAULT_BLOCK_SIZE, (short) 3, false);
//    String[] str = {"/test/file"};
    CachePoolInfo cachePoolInfo = new CachePoolInfo("poolA");
    dfs.addCachePool(cachePoolInfo);

    short rep = 3;
    Path cacheFile = new Path("/test/file");
    CacheDirectiveInfo cacheDirectiveInfo = new CacheDirectiveInfo.Builder().setReplication(rep)
            .setPath(cacheFile).setPool("poolA").build();
    dfs.addCacheDirective(cacheDirectiveInfo);
    try {
      GenericTestUtils.waitFor(new Supplier<Boolean>() {
        @Override
        public Boolean get() {
          RemoteIterator<CacheDirectiveEntry> iter = null;
          CacheDirectiveEntry entry = null;
          try {
            iter = dfs.listCacheDirectives(new CacheDirectiveInfo.Builder().
                    setPath(new Path("/test/file")).
                    build());
            entry = iter.next();
          } catch (IOException e) {
            fail("got IOException while calling " +
                    "listCacheDirectives: " + e.getMessage());
          }
          Assert.assertNotNull(entry);
          CacheDirectiveStats stats = entry.getStats();
          Long targetBytesNeeded = 900l;
          Long targetBytesCached = 900l;
          Long targetFilesNeeded = 1l;
          Long targetFilesCached = 1l;
          if ((targetBytesNeeded == stats.getBytesNeeded()) &&
                  (targetBytesCached == stats.getBytesCached()) &&
                  (targetFilesNeeded == stats.getFilesNeeded()) &&
                  (targetFilesCached == stats.getFilesCached())) {
            return true;
          } else {
            return false;
          }
        }
      }, 500, 60000);
    } catch (TimeoutException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void TestgetCacheStatusReport() throws Exception {
    CacheStatusReport report = new CacheStatusReport(dfsClient, conf);
    CacheStatus status = report.getCacheStatusReport();
    assertEquals(DEFAULT_CACHE_SIZE * 3, status.getCacheCapacityTotal());
    assertEquals(159744l, status.getCacheRemainingTotal());
    assertEquals(36864l, status.getCacheUsedTotal());
    assertEquals((float)56.25,status.getCacheUsedPercentageTotal(),0.0001);
    assertEquals(3, status.getCacheStatusMap().get("poolA").get(0).getRepliNum());
    assertEquals("/test/file", status.getCacheStatusMap().get("poolA").get(0).getFilePath());
    assertEquals(65536l, status.getdnCacheStatusMap().get("127.0.0.1").getCacheCapacity());
    assertEquals(53248l, status.getdnCacheStatusMap().get("127.0.0.1").getCacheRemaining());
    assertEquals(12288l, status.getdnCacheStatusMap().get("127.0.0.1").getCacheUsed());
    assertEquals((float)18.75, status.getdnCacheStatusMap().get("127.0.0.1").getCacheUsedPercentage(),0.0001);
  }

  @After
  public void teardown() throws Exception {
    if (cluster != null) {
      cluster.shutdown();
      cluster = null;
    }
    // Restore the original CacheManipulator
    NativeIO.POSIX.setCacheManipulator(prevCacheManipulator);
  }
}
