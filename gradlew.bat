@rem
@rem Copyright 2015 the original author or authors.
@rem
@rem Licensed under the Apache License, Version 2.0 (the "License");
@rem you may not use this file except in compliance with the License.
@rem You may obtain a copy of the License at
@rem
@rem      https://www.apache.org/licenses/LICENSE-2.0
@rem
@rem Unless required by applicable law or agreed to in writing, software
@rem distributed under the License is distributed on an "AS IS" BASIS,
@rem WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@rem See the License for the specific language governing permissions and
@rem limitations under the License.
@rem

@if "%DEBUG%" == "" @echo off
@rem ##########################################################################
@rem
@rem  Gradle startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="WindowsNT" setlocal

set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.
set APPBASENAME=%~n0
set APPHOME=%DIRNAME%

@rem Resolve any "." and ".." in APPHOME to make it shorter.
for %%i in ("%APPHOME%") do set APPHOME=%%~fi

@rem Add default JVM options here. You can also use JAVAOPTS and GRADLEOPTS to pass JVM options to this script.
set DEFAULTJVMOPTS="-Xmx64m" "-Xms64m"

@rem Find java.exe
if defined JAVAHOME goto findJavaFromJavaHome

set JAVAEXE=java.exe
%JAVAEXE% -version >NUL 2>&1
if "%ERRORLEVEL%" == "0" goto execute

echo.
echo ERROR: JAVAHOME is not set and no 'java' command could be found in your PATH.
echo.
echo Please set the JAVAHOME variable in your environment to match the
echo location of your Java installation.

goto fail

:findJavaFromJavaHome
set JAVAHOME=%JAVAHOME:"=%
set JAVAEXE=%JAVAHOME%/bin/java.exe

if exist "%JAVAEXE%" goto execute

echo.
echo ERROR: JAVAHOME is set to an invalid directory: %JAVAHOME%
echo.
echo Please set the JAVAHOME variable in your environment to match the
echo location of your Java installation.

goto fail

:execute
@rem Setup the command line

set CLASSPATH=%APPHOME%\gradle\wrapper\gradle-wrapper.jar


@rem Execute Gradle
"%JAVAEXE%" %DEFAULTJVMOPTS% %JAVAOPTS% %GRADLEOPTS% "-Dorg.gradle.appname=%APPBASENAME%" -classpath "%CLASSPATH%" org.gradle.wrapper.GradleWrapperMain %*

:end
@rem End local scope for the variables with windows NT shell
if "%ERRORLEVEL%"=="0" goto mainEnd

:fail
rem Set variable GRADLEEXITCONSOLE if you need the script return code instead of
rem the cmd.exe /c return code!
if  not "" == "%GRADLEEXITCONSOLE%" exit 1
exit /b 1

:mainEnd
if "%OS%"=="WindowsNT" endlocal

:omega
