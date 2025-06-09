@echo off
REM Altere esse caminho se seu JDK 8 estiver em outro lugar
set JAVA_HOME="C:\Program Files\Eclipse Adoptium\jdk-8.0.452.9-hotspot"
set PATH=%JAVA_HOME%\bin;%PATH%
call gradlew.bat clean build
