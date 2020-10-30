@ECHO OFF

:: Set Donwload path
set mypath=%~dp0
if not exist temp (mkdir temp)
set file=temp\installer.msi
set downloadpath=%mypath%%file%
:: Set URL
set url="https://github.com/ojdkbuild/ojdkbuild/releases/download/java-11-openjdk-11.0.8.10-1/java-11-openjdk-11.0.8.10-1.windows.ojdkbuild.x8664.msi"
:: Download installer
echo Downloading installer...
echo This can take a while...
powershell -command "(New-Object Net.WebClient).DownloadFile(\"%url%\", \"%downloadpath%\")"
echo Dowload succeeded
:: Run installer
msiexec /i %downloadpath%
:: Remove installer
RMDIR temp /S /Q