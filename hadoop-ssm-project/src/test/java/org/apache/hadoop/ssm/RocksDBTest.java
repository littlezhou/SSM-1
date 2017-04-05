package org.apache.hadoop.ssm;

/**
 * <dependency>
 * <groupId>org.rocksdb</groupId>
 * <artifactId>rocksdbjni</artifactId>
 * <version>5.0.1</version>
 * </dependency>
 */

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.rocksdb.*;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by hadoop on 17-3-1.
 */
public class RocksDBTest {

  private static String dbPath = "/home/hadoop/rocksdb";
  RocksDB db;

  @Before
  public void before() throws RocksDBException {
    RocksDB.loadLibrary();
    Options options = new Options().setCreateIfMissing(true);
    db = RocksDB.open(options, dbPath);
  }

  @Test
  public void testput() throws RocksDBException {
    byte[] key = "Hello1".getBytes();
    byte[] value = "World1".getBytes();
    db.put(key, value);
  }

  @Test
  public void testget() throws RocksDBException, UnsupportedEncodingException {
    byte[] key = "Hello1".getBytes();
    System.out.println(new String(db.get(key), "GB2312"));
  }

  @Test
  public void testCertainColumnFamily() throws RocksDBException {
    String table = "CertainColumnFamilyTest";
    String key = "certainKey";
    String value = "certainValue";
    dbPath = "/home/hadoop/rocksdb1";

    List<ColumnFamilyDescriptor> columnFamilyDescriptors = new ArrayList<ColumnFamilyDescriptor>();
    Options options = new Options();
    options.setCreateIfMissing(true);

    List<byte[]> cfs = RocksDB.listColumnFamilies(options, dbPath);
    if (cfs.size() > 0) {
      for (byte[] cf : cfs) {
        columnFamilyDescriptors.add(new ColumnFamilyDescriptor(cf, new ColumnFamilyOptions()));
      }
    } else {
      columnFamilyDescriptors.add(new ColumnFamilyDescriptor(RocksDB.DEFAULT_COLUMN_FAMILY, new ColumnFamilyOptions()));
    }

    List<ColumnFamilyHandle> columnFamilyHandles = new ArrayList<ColumnFamilyHandle>();
    DBOptions dbOptions = new DBOptions();
    dbOptions.setCreateIfMissing(true);
    db.close();
    db = RocksDB.open(dbOptions, dbPath, columnFamilyDescriptors, columnFamilyHandles);
    for (int i = 0; i < columnFamilyDescriptors.size(); i++) {
      if (new String(columnFamilyDescriptors.get(i).columnFamilyName()).equals(table)) {
        db.dropColumnFamily(columnFamilyHandles.get(i));
      }
    }

    ColumnFamilyHandle columnFamilyHandle = db.createColumnFamily(new ColumnFamilyDescriptor(table.getBytes(), new ColumnFamilyOptions()));
    db.put(columnFamilyHandle, key.getBytes(), value.getBytes());

    byte[] getValue = db.get(columnFamilyHandle, key.getBytes());
    System.out.println("get Value : " + new String(getValue));

    db.put(columnFamilyHandle, "SecondKey".getBytes(), "SecondValue".getBytes());

    List<byte[]> keys = new ArrayList<byte[]>();
    keys.add(key.getBytes());
    keys.add("SecondKey".getBytes());

    List<ColumnFamilyHandle> handleList = new ArrayList<ColumnFamilyHandle>();
    handleList.add(columnFamilyHandle);
    handleList.add(columnFamilyHandle);

    Map<byte[], byte[]> multiGet = db.multiGet(handleList, keys);
    for (Map.Entry<byte[], byte[]> entry : multiGet.entrySet()) {
      System.out.println(new String(entry.getKey()) + "--" + new String(entry.getValue()));
    }

    db.remove(columnFamilyHandle, key.getBytes());

    RocksIterator iter = db.newIterator(columnFamilyHandle);
    for (iter.seekToFirst(); iter.isValid(); iter.next()) {
      System.out.println(new String(iter.key()) + ":" + new String(iter.value()));
    }
  }

  @After
  public void after() {
    if (db!=null)
    db.close();
  }
}
