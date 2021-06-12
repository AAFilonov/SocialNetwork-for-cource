@ECHO OFF
set LOCACLDIR=.

start  java -Xms128m -Xmx384m -Xnoclassgc -jar %LOCACLDIR%\MainService\target\MainService-1.0-SNAPSHOT.jar 
start java -Xms128m -Xmx384m -Xnoclassgc -jar %LOCACLDIR%\PostService\target\PostService-1.0-SNAPSHOT.jar 
start  java -Xms128m -Xmx384m -Xnoclassgc -jar %LOCACLDIR%\UserService\target\UserService-1.0-SNAPSHOT.jar 
