@echo off
echo Building xcraft library with all modules...
echo.

REM Clean previous builds
call mvn clean

REM Build all modules
call mvn package

echo.
echo Copying JAR files to root target directory...
echo.

REM Create target directory in root if it doesn't exist
if not exist "target" mkdir "target"

REM Copy JAR files from each module
copy "xcraft-core\target\*.jar" "target\" >nul
copy "xcraft-items\target\*.jar" "target\" >nul
copy "xcraft-ui\target\*.jar" "target\" >nul
copy "xcraft-world\target\*.jar" "target\" >nul
copy "xcraft-network\target\*.jar" "target\" >nul

echo.
echo Build complete! All JAR files are in the target directory.
echo Check the target\README.md file for details about each JAR.
echo.
pause