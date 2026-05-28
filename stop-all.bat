@echo off
setlocal EnableExtensions EnableDelayedExpansion

set "ROOT_DIR=%~dp0"
set "BACKEND_DIR=%ROOT_DIR%backend"
set "BACKEND_DOCKER_DIR=%BACKEND_DIR%\docker"

pushd "%ROOT_DIR%"

call :require_tool docker Docker
if errorlevel 1 goto :fail

echo [INFO] Closing frontend window...
call :kill_window "Boutique Hotel Frontend"

echo [INFO] Closing backend window...
call :kill_window "Boutique Hotel Backend"

echo [INFO] Shutting down Docker Compose...
pushd "%BACKEND_DOCKER_DIR%"
docker compose down
if errorlevel 1 (
    echo [ERROR] Docker Compose could not be shut down.
    popd
    goto :fail
)
popd

echo [OK] Backend, frontend, and database have been stopped.
popd
exit /b 0

:require_tool
where %1 >nul 2>&1
if errorlevel 1 (
    echo [ERROR] %2 is not installed or not found in PATH.
    exit /b 1
)
exit /b 0

:kill_window
powershell -NoProfile -Command "$title = '%~1'; $processes = Get-Process | Where-Object { $_.MainWindowTitle -like ('*' + $title + '*') }; if ($processes) { $processes | Stop-Process -Force; exit 0 }; exit 1"
if errorlevel 1 (
    taskkill /FI "WINDOWTITLE eq *%~1*" /T /F >nul 2>&1
)
if errorlevel 1 (
    echo [INFO] No running console window matching %~1 found.
)
exit /b 0

:fail
popd
exit /b 1