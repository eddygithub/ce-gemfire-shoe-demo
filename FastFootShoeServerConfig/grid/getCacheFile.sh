#!/bin/bash
echo "Type the name the cache.xml file you have placed in $GEMFIRE_WORKING/conf or press enter to use the Fast Foot Demo. This is expecting a Spring Data Gemfire Configuration";
read cacheName
if [ -z "$cacheName" ]
then
	export cache=fastfootshoes-gf-cache.xml
else
	export cache=$cacheName
fi
echo ""
echo "Starting the grid using:$CONF_DIR/$cache"
