@REM Maven Wrapper for Windows
@REM Licensed to the Apache Software Foundation (ASF)

@IF "%__MVNW_ARG0_NAME__%"=="" (SET __MVNW_ARG0_NAME__=%~nx0)
@SET ___MVNW_ESCAPED_CMD_LINE_ARGS=%*
@SETLOCAL

@SET WRAPPER_JAR=%~dp0.mvn\wrapper\maven-wrapper.jar
@SET WRAPPER_LAUNCHER=org.apache.maven.wrapper.MavenWrapperMain

@IF EXIST %JAVA_HOME%\bin\java.exe (
  SET JAVA_EXEC=%JAVA_HOME%\bin\java.exe
) ELSE (
  SET JAVA_EXEC=java
)

@%JAVA_EXEC% -jar %WRAPPER_JAR% %MAVEN_CONFIG% %*
@IF ERRORLEVEL 1 GOTO error
@GOTO end

:error
SET ERROR_CODE=1

:end
@ENDLOCAL & SET ERROR_CODE=%ERROR_CODE%
@EXIT /B %ERROR_CODE%
