#!/bin/sh
# Maven Wrapper Script
# Licensed to the Apache Software Foundation (ASF) under one
# or more contributor license agreements.  See the NOTICE file
# distributed with this work for additional information

distributionUrl=https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/3.9.6/apache-maven-3.9.6-bin.zip
wrapperJar=.mvn/wrapper/maven-wrapper.jar
wrapperUrl=https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/3.2.0/maven-wrapper-3.2.0.jar

# Download maven-wrapper.jar if not present
if [ ! -f "$wrapperJar" ]; then
  mkdir -p "$(dirname "$wrapperJar")"
  if command -v curl > /dev/null; then
    curl -o "$wrapperJar" "$wrapperUrl" 2>/dev/null
  elif command -v wget > /dev/null; then
    wget -q -O "$wrapperJar" "$wrapperUrl"
  fi
fi

exec java -jar "$wrapperJar" "$@"
