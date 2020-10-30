@ECHO OFF

:: Set Donwload path
set mypath=%~dp0
if not exist temp (mkdir temp)
set file=temp\installer.msi
set downloadpath=%mypath%%file%
:: Set URL
set url="https://fastdl.mongodb.org/windows/mongodb-windows-x8664-4.4.1-signed.msi"
:: Download installer
echo Downloading installer...
echo This can take a while...
powershell -command "(New-Object Net.WebClient).DownloadFile(\"%url%\", \"%downloadpath%\")"
echo Dowload succeeded
:: Run installer
msiexec /i %downloadpath%
:: Remove installer
RMDIR temp /S /Q