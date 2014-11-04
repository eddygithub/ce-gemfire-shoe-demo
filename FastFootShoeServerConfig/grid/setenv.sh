#!/bin/bash
# Derek Beauregard & Luke Shannon #
# GEMFIRE_WORKING is set on start up #

# Set the GemFire environment variables #
export HOST=localhost
export JAVA_HOME=/usr/java/jdk1.7.0_51
export GEMFIRE=/home/gemfire/gemfire/Pivotal_GemFire_800_b48398
export GF_JAVA=$JAVA_HOME/bin/java
export CONF_DIR=$GEMFIRE_WORKING/conf
export LIB_DIR=$GEMFIRE_WORKING/lib
export SERVER_DIR_LOCATION=/home/gemfire/Desktop
export LOCATOR_1="locatorA";
export LOCATOR_IP=172.16.6.20
export LOCATOR_PORT=10334
export SERVER_1="serverA";
export SERVER_2="serverB";
export SERVER_3="serverC";
export SERVER_4="serverD";
export PATH=$PATH:$JAVA_HOME/bin:$GEMFIRE/bin
export CLASSPATH=$CLASSPATH:$GEMFIRE/lib/*:$JAVA_HOME/lib/tools.jar:$CONF_DIR/:$CONF_DIR/*:$LIB_DIR/:$LIB_DIR/*
export CLOSE_MESSAGE="Press the Enter key to close this window..."
export STOP_SERVER_A_MESSAGE="Stopping a data server on the Locator + Servers machine";
export START_SERVER_A_MESSAGE="Starting a data server on the Locator + Servers machine";
export STOP_SERVER_C_MESSAGE="Stopping a data server on the Expansion Servers machine";
export START_SERVER_C_MESSAGE="Starting a data server on the Expansion Servers machine";
export LOCAL_IP=`ifconfig eth0 2>/dev/null|awk '/inet addr:/ {print $2}'|sed 's/addr://'`
#Server Configuration #
export SERVER_HOST=$HOST
export GF_JAVA_OPT="-server,-verbose:gc,-XX:+PrintGCTimeStamps,-XX:+PrintGCDetails,-Xloggc:gc.log\
,-Xms512m,-Xmx512m\
,-XX:+UseConcMarkSweepGC,-XX:+UseParNewGC\
,-XX:CMSInitiatingOccupancyFraction=90\
,-XX:+UseCompressedOops"

#GFSH logging
export JAVA_ARGS="-Dgfsh.log-dir=$GEMFIRE_WORKING/logs-gfsh "

#Server Commands
export LOCATOR_COMMAND="start locator --name=$LOCATOR_1 --enable-cluster-configuration=false --dir=$SERVER_DIR_LOCATION/$LOCATOR_1 --port=$LOCATOR_PORT --log-level=info --J=-Dcom.sun.management.jmxremote --J=-Dcom.sun.management.jmxremote.authenticate=false --J=-Dcom.sun.management.jmxremote.port=1099 --J=-Dcom.sun.management.jmxremote.ssl=false"
export SERVER_1_COMMAND="start server --name=$SERVER_1 --use-cluster-configuration=false --classpath=$CLASSPATH --server-port=0 --bind-address=$LOCAL_IP --dir=$SERVER_DIR_LOCATION/$SERVER_1 --locators=$LOCATOR_IP[$LOCATOR_PORT] --properties-file=$CONF_DIR/gemfire.properties"
export SERVER_2_COMMAND="start server --name=$SERVER_2 --use-cluster-configuration=false --classpath=$CLASSPATH --server-port=0 --bind-address=$LOCAL_IP --dir=$SERVER_DIR_LOCATION/$SERVER_2 --locators=$LOCATOR_IP[$LOCATOR_PORT] --properties-file=$CONF_DIR/gemfire.properties"
export SERVER_3_COMMAND="start server --name=$SERVER_3 --use-cluster-configuration=false --classpath=$CLASSPATH --server-port=0 --bind-address=172.16.6.21 --dir=$SERVER_DIR_LOCATION/$SERVER_3 --locators=$LOCATOR_IP[$LOCATOR_PORT] --properties-file=$CONF_DIR/gemfire.properties"
export SERVER_4_COMMAND="start server --name=$SERVER_4 --use-cluster-configuration=false --classpath=$CLASSPATH --server-port=0 --bind-address=172.16.6.21 --dir=$SERVER_DIR_LOCATION/$SERVER_4 --locators=$LOCATOR_IP[$LOCATOR_PORT] --properties-file=$CONF_DIR/gemfire.properties"
export CONNECT_LOCATOR="connect --locator=$LOCATOR_IP[$LOCATOR_PORT]"
export REBALANCE_COMMAND="rebalance"

