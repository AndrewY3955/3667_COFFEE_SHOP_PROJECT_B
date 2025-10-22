@echo off
REM Compile and run the CoffeeShop GUI (Windows CMD)

if not exist out mkdir out

njavac -d out src\main\java\CoffeeShopGUI.java src\main\java\Base\*.java src\main\java\Concrete\*.java src\main\java\Decorator\*.java
nif errorlevel 1 (
  echo Compilation failed.
  exit /b 1
)

nfor /f "usebackq tokens=*" %%i in (`where javac`) do set "JAVAC_PATH=%%i"
for %%i in ("%JAVAC_PATH%") do set "BIN_DIR=%%~dpi"
for %%i in ("%BIN_DIR%..\") do set "JDK_DIR=%%~fi"
set "JAVA_EXE=%JDK_DIR%bin\java.exe"

necho Starting GUI using %JAVA_EXE%
"%JAVA_EXE%" -cp out CoffeeShopGUI