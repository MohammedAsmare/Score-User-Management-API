@echo off
setlocal
SET MAVEN_WRAPPER_DIR=.mvn\wrapper
SET MAVEN_WRAPPER_JAR=%MAVEN_WRAPPER_DIR%\maven-wrapper.jar
SET BASEDIR=%~dp0
SET EMBEDDED_MAVEN=%BASEDIR%\.mvn\apache-maven-3.9.6\apache-maven-3.9.6\bin\mvn.cmd

if exist "%MAVEN_WRAPPER_JAR%" (
  echo Found maven-wrapper.jar - attempting to run it...
  java -jar "%MAVEN_WRAPPER_JAR%" %*
  if not errorlevel 1 (
    endlocal
    exit /b 0
  )
  echo Maven wrapper jar failed; falling back to embedded Maven.
)

if not exist "%EMBEDDED_MAVEN%" goto missingEmbeddedMaven

call "%EMBEDDED_MAVEN%" %*
set MAVEN_EXIT_CODE=%ERRORLEVEL%
endlocal
exit /b %MAVEN_EXIT_CODE%

:missingEmbeddedMaven
echo Embedded Maven not found at "%EMBEDDED_MAVEN%"
echo Please install Maven or restore a working maven-wrapper.jar.
endlocal
exit /b 1
