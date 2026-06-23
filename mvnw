#!/usr/bin/env bash
# ----------------------------------------------------------------------------
# Maven Wrapper
#
# Licensed under the Apache License, Version 2.0
# ----------------------------------------------------------------------------
set -e

MAVEN_WRAPPER_DIR=".mvn/wrapper"
MAVEN_WRAPPER_JAR="$MAVEN_WRAPPER_DIR/maven-wrapper.jar"

if [ ! -f "$MAVEN_WRAPPER_JAR" ]; then
  echo "Maven wrapper jar not found. The wrapper will attempt to download it now."
fi

java -jar "$MAVEN_WRAPPER_JAR" "$@"
