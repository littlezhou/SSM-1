#!/usr/bin/env bash
#
# Licensed to the Apache Software Foundation (ASF) under one
# or more contributor license agreements.  See the NOTICE file
# distributed with this work for additional information
# regarding copyright ownership.  The ASF licenses this file
# to you under the Apache License, Version 2.0 (the
# "License"); you may not use this file except in compliance
# with the License.  You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

function command_class_name() {
  local subcmd=$1
  shift

  case ${subcmd} in
    smartserver)
      SMART_CLASSNAME=org.smartdata.server.SmartDaemon
    ;;
    getconf)
      SMART_CLASSNAME=org.smartdata.server.utils.tools.GetConf
    ;;
    *)
      echo "Unkown sub command ${subcmd}"
      exit 1;
    ;;
  esac
}

bin=$(dirname "${BASH_SOURCE-$0}")
bin=$(cd "${bin}">/dev/null; pwd)
HOSTNAME=$(hostname)

DAEMON_MOD=
SMART_VARGS=
while [ $# != 0 ]; do
  case "$1" in
    "--config")
      shift
      conf_dir="$1"
      if [[ ! -d "${conf_dir}" ]]; then
        echo "ERROR : ${conf_dir} is not a directory"
        echo ${USAGE}
        exit 1
      else
        export SMART_CONF_DIR="${conf_dir}"
        echo "SMART_CONF_DIR="$SMART_CONF_DIR
      fi
      shift
      ;;
    "--debug")
      JAVA_OPTS+=" -Xdebug -Xrunjdwp:transport=dt_socket,address=8008,server=y,suspend=y"
      shift
      ;;
    "--daemon")
      DAEMON_MOD=1
      shift
      ;;
    *)
      break;
      ;;
  esac
done

. "${bin}/common.sh"

SUBCOMMAND=$1
shift

command_class_name ${SUBCOMMAND}

JAVA_OPTS+=" -Dsmart.log.dir=${SMART_LOG_DIR}"
JAVA_OPTS+=" -Dsmart.log.file=SmartServer.log"

JAVA_VERSION=$($SMART_RUNNER -version 2>&1 | awk -F '.' '/version/ {print $2}')

if [[ "$JAVA_VERSION" -ge 8 ]]; then
  JAVA_OPTS+=" -XX:MaxMetaspaceSize=256m"
else
  JAVA_OPTS+=" -XX:MaxPermSize=256m"
fi

addJarInDir "${SMART_HOME}/smart-server/target/lib"
addNonTestJarInDir "${SMART_HOME}/smart-server/target"
addJarInDir "${SMART_HOME}/lib"

if [ "$SMART_CLASSPATH" = "" ]; then
  SMART_CLASSPATH="${SMART_CONF_DIR}"
else
  SMART_CLASSPATH="${SMART_CONF_DIR}:${SMART_CLASSPATH}"
fi

if [[ ! -d "${SMART_LOG_DIR}" ]]; then
  echo "Log dir doesn't exist, create ${SMART_LOG_DIR}"
  $(mkdir -p "${SMART_LOG_DIR}")
fi

SMART_VARGS+=" -D smart.conf.dir="${SMART_CONF_DIR}
SMART_VARGS+=" -D smart.log.dir="${SMART_LOG_DIR}


echo "$SMART_RUNNER $JAVA_OPTS -cp ${SMART_CLASSPATH} ${SMART_CLASSNAME} $@"

exec $SMART_RUNNER $JAVA_OPTS -cp "${SMART_CLASSPATH}" ${SMART_CLASSNAME} $@

#
#if [ -z "$DAEMON_MOD" ]; then
#  start_smart_server
#else
#  smart_start_daemon ${SMART_SERVER_PID_FILE}
#fi
#