#!/bin/bash
source $GEMFIRE_WORKING/setenv.sh
echo $STOP_SERVER_A_MESSAGE;
gfsh 	-e "stop server --dir=$SERVER_DIR_LOCATION/$SERVER_1"
echo $CLOSE_MESSAGE
read close_me
