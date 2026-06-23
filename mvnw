#!/usr/bin/env bash
# Maven Wrapper (enhanced): try bundled wrapper jar, fall back to embedded Maven

MAVEN_WRAPPER_DIR=".mvn/wrapper"
MAVEN_WRAPPER_JAR="$MAVEN_WRAPPER_DIR/maven-wrapper.jar"
BASEDIR=$(dirname "$0")
EMBEDDED_MAVEN="$BASEDIR/.mvn/apache-maven-3.9.6/apache-maven-3.9.6/bin/mvn"

if [ -f "$MAVEN_WRAPPER_JAR" ]; then
  echo "Found maven-wrapper.jar - attempting to run it..."
  if java -jar "$MAVEN_WRAPPER_JAR" "$@"; then
    exit $?
  else
    echo "Maven wrapper jar failed; falling back to embedded Maven." >&2
  fi
else
  echo "Maven wrapper jar not found. Falling back to embedded Maven." >&2
fi

if [ -x "$EMBEDDED_MAVEN" ]; then
  exec "$EMBEDDED_MAVEN" "$@"
else
  echo "Embedded Maven not found at $EMBEDDED_MAVEN" >&2
  echo "Please install Maven or restore a working maven-wrapper.jar." >&2
  exit 1
fi
