@ECHO OFF

where wsl >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
    :: WSL not installed, using native installation
    for /F "tokens=3 delims=: " %%H in ('sc query "MongoDB" ^| findstr "        STATE"') do (
        if /I "%%H" NEQ "RUNNING" (
            echo MongoDB service not running...
            echo Starting it now...
            powershell -Command "Start-Process powershell \"-ExecutionPolicy Bypass -NoProfile -Command `\" net start MongoDB `\" \" -Verb RunAs"
        )
    )
) else (
    :: WSL found, using its installation
    wsl service mongodb status | find /i "fail"
    if not errorlevel 1 (
        echo MongoDB not running...
        echo Starting it now...
        wsl sudo service mongodb start
    )
)

