#!/bin/bash
source $GEMFIRE_WORKING/setenv.sh
if [ -d "$SERVER_DIR_LOCATION/$SERVER_1" ]; then
	gfsh -e "stop server --dir=$SERVER_DIR_LOCATION/$SERVER_1"
fi
if [ -d "$SERVER_DIR_LOCATION/$SERVER_2" ]; then
	gfsh -e "stop server --dir=$SERVER_DIR_LOCATION/$SERVER_2"
fi
if [ -d "$SERVER_DIR_LOCATION/$SERVER_3" ]; then
	gfsh -e "stop server --dir=$SERVER_DIR_LOCATION/$SERVER_3"
fi
if [ -d "$SERVER_DIR_LOCATION/$SERVER_4" ]; then
	gfsh -e "stop server --dir=$SERVER_DIR_LOCATION/$SERVER_4"
fi
if [ -d "$SERVER_DIR_LOCATION/$LOCATOR_1" ]; then
	gfsh -e "stop server --dir=$SERVER_DIR_LOCATION/$LOCATOR_1"
fi
echo "Stop Commands Completed. Checking For Running Java Processes:"
echo ps -ef | grep java
echo $CLOSE_MESSAGE
read close_me


