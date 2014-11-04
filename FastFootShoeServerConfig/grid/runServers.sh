#!/bin/bash
source $GEMFIRE_WORKING/setenv.sh
source $GEMFIRE_WORKING/getCacheFile.sh
if [ ! -d "$SERVER_DIR_LOCATION/$SERVER_3" ]; then
	mkdir $SERVER_DIR_LOCATION/$SERVER_3
fi
if [ ! -d "$SERVER_DIR_LOCATION/$SERVER_4" ]; then
	mkdir $SERVER_DIR_LOCATION/$SERVER_4
fi
gfsh -e "$SERVER_3_COMMAND --cache-xml-file=$CONF_DIR/$cache" \
     -e "$SERVER_4_COMMAND --cache-xml-file=$CONF_DIR/$cache" \
	-e "run --file=serverInit.gfsh"
echo $CLOSE_MESSAGE
read close_me
