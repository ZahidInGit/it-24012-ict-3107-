@echo off
set "CATALINA_HOME=F:\LENOVO\Server tools\apache-tomcat-10.1.57-windows-x64\apache-tomcat-10.1.57"
set "JAVA_HOME=C:\Program Files\Java\jdk-25"
start "Tomcat Server" "%CATALINA_HOME%\bin\catalina.bat" run
