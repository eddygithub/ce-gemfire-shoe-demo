#!/bin/bash
source $GEMFIRE_WORKING/setenv.sh
echo ps -ef | grep java
if [ ! -d "$SERVER_DIR_LOCATION/$LOCATOR_1" ]; then
	mkdir $SERVER_DIR_LOCATION/$LOCATOR_1
fi
if [ ! -d "$SERVER_DIR_LOCATION/$SERVER_1" ]; then
	mkdir $SERVER_DIR_LOCATION/$SERVER_1
fi
if [ ! -d "$SERVER_DIR_LOCATION/$SERVER_2" ]; then
	mkdir $SERVER_DIR_LOCATION/$SERVER_2
fi
source $GEMFIRE_WORKING/getCacheFile.sh
gfsh -e "start locator --name=$LOCATOR_1 --enable-cluster-configuration=false --dir=$SERVER_DIR_LOCATION/$LOCATOR_1 --port=$LOCATOR_PORT --log-level=error" \
     -e "start server --name=$SERVER_1 --use-cluster-configuration=false --classpath=$CLASSPATH --server-port=0 --dir=$SERVER_DIR_LOCATION/$SERVER_1 --locators=$LOCATOR_IP[$LOCATOR_PORT] --properties-file=$CONF_DIR/gemfire.properties --cache-xml-file=$CONF_DIR/$cache" \
     -e "start server --name=$SERVER_2 --use-cluster-configuration=false --classpath=$CLASSPATH --server-port=0 --dir=$SERVER_DIR_LOCATION/$SERVER_2 --locators=$LOCATOR_IP[$LOCATOR_PORT] --properties-file=$CONF_DIR/gemfire.properties --cache-xml-file=$CONF_DIR/$cache" \
     -e "list members"
echo $CLOSE_MESSAGE
read close_me

