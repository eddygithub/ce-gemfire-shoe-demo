#!/bin/bash
source $GEMFIRE_WORKING/setenv.sh
source $GEMFIRE_WORKING/getCacheFile.sh
echo $START_SERVER_A_MESSAGE;
gfsh -e "$SERVER_1_COMMAND --cache-xml-file=$CONF_DIR/$cache" \
echo $CLOSE_MESSAGE
read close_me


