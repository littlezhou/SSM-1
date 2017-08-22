Smart Storage Management 1.1.0
==================

Highlights:
------------------
1. File movement enhancement. Shift load from SSM server, decrease the impaction on Namenode when moving files, and make it possible for potential fine-grained move scheduler.
2. HDFS HA support.
3. SSM Agent support. Optional service, actions can be dispatched to agents for execution to shift loads from SSM server.
4. New web user interface. Based on Apache Zeppelin, many improvements made for better user experience.
5. Multi-hadoop version support. Project code refactored to provide multi-hadoop version support architecturally, currently supports Apache Hadoop 2.7 and Cloudera CDH-5.10.
6. Multi-JDK version support. Supports JDK 1.7 and 1.8 architecturally.
7. Verified in integration tests. More than 40 cases tested to verify its functionality and robustness.
8. Performance improvement. Many performance issues fixed for better performance.

Change log:
------------------
- #994, Adjust key length for tables to 1000bytes
- #986, Add index for tables in SSM
- #984, Make number of rpc handlers configurable
- #978, Add properties support in move plan to control mover behavior
- Access HA Namenode support
- Add Help pages for action and rule
- #949, Add define for sys_info and cluster_info table
- #947, Make name space fetcher run asynchronously
- Improve error message once malformat druid.xml is detected
- #946, Fix the ace editor bug
- #938, Changing script names to use "ssm" instead of "smart"
- #934, Refactor SmartFileSystem
- Update ssm-cdh5.10-deployment-guide.md
- Set key limits to 512 bytes
- Initially introduce in meta service
- Add support for testing multiple profiles on travis
- #930, Fix the googlefonts build bug
- #927, Add web UI for mover and copy
- #916, Add mover and copy view
- #914, Add cmdlet based CopyScheduler
- #905, Adjust metastore log
- #901, Add BackUp Dao
- #900, Fix caret issue in web UI
- #891, Refactor hdfs related tests
- #885, Fix code style of config Dao
- #882, Add unit tests for move file actions
- #881, Export interfaces of datanode related DAOs in MetaStore
- #879, Add Batch insert with increment in globalconfigdao
- #878, Add batch insert with increment primary key in clusterConfigDao
- #869, Add methods for cluster config and global config in metastore
- #864, Fix some bugs and add more methods in Dao
- #850, Fix web context setup failure issue
- #806, Add Service module into SmartAgent
- #778, Add Rule format description on web UI
- #773, Add Copy, Rename and delete FileAction
- #761, Refine boot scripts
- #757, Fix Tidb create table issue
- #751, Fix rule one shot issue when no time specified
- #741, Remove the usage of view
- #719, Merge branch 'zeppelin' based web UI into trunk
- #665, Merge start-agent.sh into start-smart.sh

Smart Storage Management 1.0.0
==================
Highlights:
------------------
1. SSM core modules implemented
File access event collection and access data maintance
Rule DSL define and implementaion

2. 
