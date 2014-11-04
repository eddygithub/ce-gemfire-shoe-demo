#!/bin/bash
source $GEMFIRE_WORKING/setenv.sh
gfsh 	-e "stop server --dir=$SERVER_DIR_LOCATION/$SERVER_3"
echo $CLOSE_MESSAGE
read close_me


