#!/bin/bash

glob_props="global.properties"

# try to get java home path from global properties first
if [ -f "$glob_props" ]
then
	#echo "reading $glob_props"
	while IFS='=' read -r key value
	do
		key=$(echo $key)
		if [ "$key" == "java.home" ]; then
			export JAVA_HOME=$value
		fi
	done < "$glob_props"
else
	echo "$glob_props not found." 
	echo "Exiting."
	exit 1
fi

# check if java home is set
if [ -z "${JAVA_HOME}" ]
then
  	echo "JAVA_HOME is unset."
  	echo "Set it in your system or set java.home in global.properties file."
  	echo "Exiting."
  	exit 1
fi

# get/parse java version
java_version=$($JAVA_HOME/bin/java -version 2>&1 \
  | head -1 \
  | cut -d'"' -f2 \
  | sed 's/^1\.//' \
  | cut -d'.' -f1 
)

# check if java version is 8
if [ "$java_version" != "8" ]; then
  echo "Java 8 required."
  echo "Set java.home in global.properties file"
  exit 1
fi

# mvn -v

mvn clean package


