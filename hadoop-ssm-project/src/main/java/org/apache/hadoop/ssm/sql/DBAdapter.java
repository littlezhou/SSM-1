/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.hadoop.ssm.sql;

import org.apache.hadoop.fs.permission.FsPermission;
import org.apache.hadoop.hdfs.protocol.ErasureCodingPolicy;
import org.apache.hadoop.hdfs.protocol.HdfsFileStatus;
import org.apache.hadoop.io.erasurecode.ECSchema;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Operations supported for upper functions.
 */
public class DBAdapter {

  private Connection conn;

  private Map<Integer, String> mapOwnerIdName = null;
  private Map<Integer, String> mapGroupIdName = null;
  private Map<Integer, String> mapStoragePolicyIdName = null;
  private Map<Integer, ErasureCodingPolicy> mapECPolicy = null;

  public DBAdapter(Connection conn) {
    this.conn = conn;
  }

  /**
   * Insert a new rule to SSM.
   *
   * @param rule
   * @return rule id if success
   */
  public long createRule(String rule) {
    return 0;
  }

  public Map<Long, Integer> getAccessCount(long startTime, long endTime,
      String countFilter) {
    String sqlGetTableNames = "SELECT table_name FROM access_count_tables " +
        "WHERE start_time >= " + startTime + " AND end_time <= " + endTime;
    try {
      ResultSet rsTableNames = executeQuery(sqlGetTableNames);
      List<String> tableNames = new LinkedList<>();
      while (rsTableNames.next()) {
        tableNames.add(rsTableNames.getString(0));
      }

      if (tableNames.size() == 0) {
        return null;
      }

      String sqlPrefix = "SELECT fid, SUM(count) AS count FROM (\n";
      String sqlUnion = "SELECT fid, count FROM \'"
          + tableNames.get(0) + "\'\n";
      for (int i = 1; i < tableNames.size(); i++) {
        sqlUnion += "UNION ALL\n" +
            "SELECT fid, count FROM \'" + tableNames.get(i) + "\'\n";
      }
      String sqlSufix = ") GROUP BY fid ";
      // TODO: safe check
      String sqlCountFilter =
          (countFilter == null || countFilter.length() == 0) ?
          "" :
          "HAVING SUM(count) " + countFilter;
      String sqlFinal = sqlPrefix + sqlUnion + sqlSufix + sqlCountFilter;

      ResultSet rsValues = executeQuery(sqlGetTableNames);

      Map<Long, Integer> ret = new HashMap<>();
      while (rsValues.next()) {
        ret.put(rsValues.getLong(1), rsValues.getInt(2));
      }
      return ret;
    } catch (SQLException e) {
      // TODO: log and handle
    }
    return null;
  }

  /**
   * Store access count data for the given time interval.
   *
   * @param startTime
   * @param endTime
   * @param fids
   * @param counts
   */
  public synchronized void insertAccessCountData(long startTime, long endTime,
      long[] fids, int[] counts) {
  }

  /**
   * Store files info into database.
   *
   * @param files
   */
  public synchronized void insertFiles(HdfsFileStatus[] files) {
  }

  public HdfsFileStatus getFile(long fid) {
    String sql = "SELECT * FROM files WHERE fid = " + fid;
    ResultSet result;
    try {
      result = executeQuery(sql);
    } catch (SQLException e) {
      return null;
    }
    List<HdfsFileStatus> ret = convertFilesTableItem(result);
    return ret.size() > 0 ? ret.get(0) : null;
  }

  public HdfsFileStatus getFile(String path) {
    return null;
  }

  /**
   * Convert query result into HdfsFileStatus list.
   * Note: Some of the info in HdfsFileStatus are not the same
   *       as stored in NN.
   *
   * @param resultSet
   * @return
   */
  public List<HdfsFileStatus> convertFilesTableItem(ResultSet resultSet) {
    List<HdfsFileStatus> ret = new LinkedList<>();
    if (resultSet == null) {
      return ret;
    }
    try {
      while (resultSet.next()) {
        HdfsFileStatus status = new HdfsFileStatus(
            resultSet.getLong("length"),
            resultSet.getBoolean("is_dir"),
            resultSet.getShort("block_replication"),
            resultSet.getLong("block_size"),
            resultSet.getLong("modification_time"),
            resultSet.getLong("access_time"),
            new FsPermission(resultSet.getShort("permission")),
            mapOwnerIdName.get(resultSet.getShort("oid")),
            mapGroupIdName.get(resultSet.getShort("gid")),
            null, // Not tracked for now
            resultSet.getString("path").getBytes(),
            resultSet.getLong("fid"),
            0,    // Not tracked for now, set to 0
            null, // Not tracked for now, set to null
            resultSet.getByte("sid"),
            mapECPolicy.get(resultSet.getShort("ec_policy_id")));
        ret.add(status);
      }
    } catch (SQLException e) {
      return null;
    }
    return ret;
  }

  private void updateCache() {
    try {
      if (mapOwnerIdName == null) {
        String sql = "SELECT * FROM owners";
        mapOwnerIdName = convertToMap(executeQuery(sql));
      }

      if (mapGroupIdName == null) {
        String sql = "SELECT * FROM groups";
        mapGroupIdName = convertToMap(executeQuery(sql));
      }

      if (mapStoragePolicyIdName == null) {
        String sql = "SELECT * FROM storage_policy";
        mapStoragePolicyIdName = convertToMap(executeQuery(sql));
      }

      if (mapECPolicy == null) {
        String sql = "SELECT * FROM ecpolicys";
        mapECPolicy = convertEcPoliciesTableItem(executeQuery(sql));
      }
    } catch (SQLException e) {
    }
  }

  private Map<Integer, ErasureCodingPolicy> convertEcPoliciesTableItem(ResultSet resultSet) {
    Map<Integer, ErasureCodingPolicy> ret = new HashMap<>();
    if (resultSet == null) {
      return ret;
    }
    try {
      while (resultSet.next()) {
        int id = resultSet.getInt("id");

        ECSchema schema = new ECSchema(
            resultSet.getString("codecName"),
            resultSet.getInt("numDataUnits"),
            resultSet.getInt("numParityUnits")
        );

        ErasureCodingPolicy ec = new ErasureCodingPolicy(
            resultSet.getString("name"),
            schema,
            resultSet.getInt("cellsize"),
            (byte)id
        );

        ret.put(id, ec);
      }
    } catch (SQLException e) {
      return null;
    }
    return ret;
  }

  private Map<Integer, String> convertToMap(ResultSet resultSet) {
    Map<Integer, String> ret = new HashMap<>();
    if (resultSet == null) {
      return ret;
    }
    try {
      while (resultSet.next()) {
        // TODO: Tests for this
        ret.put(resultSet.getInt(1), resultSet.getString(2));
      }
    } catch (SQLException e) {
      return null;
    }
    return ret;
  }


  public synchronized void insertCachedFiles(long fid, long fromTime,
      long lastAccessTime, int numAccessed) {
  }

  public synchronized void insertCachedFiles(List<CachedFileStatus> s) {
  }

  public synchronized void updateCachedFiles(long fid,
      long lastAccessTime, int numAccessed) {
  }

  public List<CachedFileStatus> getCachedFileStatus() {
    String sql = "SELECT * FROM cached_files";
    return getCachedFileStatus(sql);
  }

  public CachedFileStatus getCachedFileStatus(long fid) {
    String sql = "SELECT * FROM cached_files WHERE fid = " + fid;
    List<CachedFileStatus> s = getCachedFileStatus(sql);
    return s != null ? s.get(0) : null;
  }

  private List<CachedFileStatus> getCachedFileStatus(String sql) {
    ResultSet resultSet;
    List<CachedFileStatus> ret = new LinkedList<>();

    try {
      resultSet = executeQuery(sql);

      while (resultSet.next()) {
        CachedFileStatus f = new CachedFileStatus(
            resultSet.getLong("fid"),
            resultSet.getLong("from_time"),
            resultSet.getLong("last_access_time"),
            resultSet.getInt("count")
        );
        ret.add(f);
      }
    } catch (SQLException e) {
      return null;
    }
    return ret.size() == 0 ? null : ret;
  }


  public ResultSet executeQuery(String sqlQuery) throws SQLException {
    Statement s = conn.createStatement();
    return s.executeQuery(sqlQuery);
  }

  public int executeUpdate(String sqlUpdate) throws SQLException {
    Statement s = conn.createStatement();
    return s.executeUpdate(sqlUpdate);
  }

  public synchronized void close() {
  }
}