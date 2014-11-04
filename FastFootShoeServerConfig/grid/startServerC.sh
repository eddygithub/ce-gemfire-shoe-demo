#!/bin/bash
source $GEMFIRE_WORKING/setenv.sh
source $GEMFIRE_WORKING/getCacheFile.sh
gfsh -e "$SERVER_3_COMMAND --cache-xml-file=$CONF_DIR/$cache"
echo $CLOSE_MESSAGE
read close_me


