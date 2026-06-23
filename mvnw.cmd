@echo off
SET MAVEN_WRAPPER_DIR=.mvn\wrapper
SET MAVEN_WRAPPER_JAR=%MAVEN_WRAPPER_DIR%\maven-wrapper.jar

if not exist "%MAVEN_WRAPPER_JAR%" (
  echo Maven wrapper jar not found. The wrapper will attempt to download it now.
)

java -jar "%MAVEN_WRAPPER_JAR%" %*
