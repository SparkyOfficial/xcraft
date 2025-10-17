@echo off
echo Building xcraft library with all modules...
echo.

REM Clean previous builds
mvn clean

REM Build all modules
mvn package

echo.
echo Copying JAR files to root target directory...
echo.

REM Create target directory in root if it doesn't exist
if not exist "target" mkdir "target"

REM Copy JAR files from each module
copy "xcraft-core\target\xcraft-core-*.jar" "target\"
copy "xcraft-items\target\xcraft-items-*.jar" "target\"
copy "xcraft-ui\target\xcraft-ui-*.jar" "target\"
copy "xcraft-world\target\xcraft-world-*.jar" "target\"
copy "xcraft-network\target\xcraft-network-*.jar" "target\"

echo.
echo Build complete! All JAR files are in the target directory.
echo.