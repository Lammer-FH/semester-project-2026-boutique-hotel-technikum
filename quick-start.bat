@echo off
setlocal EnableExtensions EnableDelayedExpansion

set "ROOT_DIR=%~dp0"

if "%~1"=="" goto :usage
if /i "%~1"=="start" goto :start
if /i "%~1"=="stop" goto :stop
if /i "%~1"=="--help" goto :usage
if /i "%~1"=="-h" goto :usage

echo [ERROR] Unknown command: %~1
echo.
goto :usage

:usage
echo Usage: quick-start ^<start^|stop^>
echo.
echo Commands:
echo   start    Start the full application stack (database, backend, frontend)
echo   stop     Stop all running services
exit /b 0

:start
set "BACKEND_DIR=%ROOT_DIR%backend"
set "BACKEND_DOCKER_DIR=%BACKEND_DIR%\docker"
set "BACKEND_ENV=%BACKEND_DIR%\.env"
set "BACKEND_ENV_EXAMPLE=%BACKEND_DIR%\.env-example"
set "FRONTEND_DIR=%ROOT_DIR%frontend"
set "FRONTEND_ENV=%FRONTEND_DIR%\.env"
set "FRONTEND_ENV_EXAMPLE=%FRONTEND_DIR%\.env-example"

pushd "%ROOT_DIR%"

where docker >nul 2>&1
if errorlevel 1 (echo [ERROR] Docker is not installed or not found in PATH. & popd & exit /b 1)
where java >nul 2>&1
if errorlevel 1 (echo [ERROR] Java is not installed or not found in PATH. & popd & exit /b 1)
where node >nul 2>&1
if errorlevel 1 (echo [ERROR] Node.js is not installed or not found in PATH. & popd & exit /b 1)
where npm >nul 2>&1
if errorlevel 1 (echo [ERROR] npm is not installed or not found in PATH. & popd & exit /b 1)

echo [INFO] Cleaning up leftover processes...
powershell -NoProfile -Command "foreach ($port in 8080, 5000) { $pids = netstat -ano | Select-String (\":$port\s+\S+\s+\S+\s+(\d+)\") | ForEach-Object { $_.Matches[0].Groups[1].Value } | Sort-Object -Unique; if ($pids) { $pids | ForEach-Object { Stop-Process -Id $_ -Force -ErrorAction SilentlyContinue } } }"

if exist "%BACKEND_ENV%" (
    echo [INFO] Backend .env already exists.
) else if exist "%BACKEND_ENV_EXAMPLE%" (
    copy /Y "%BACKEND_ENV_EXAMPLE%" "%BACKEND_ENV%" >nul
    if errorlevel 1 (echo [ERROR] Backend .env could not be created from .env-example. & popd & exit /b 1)
    echo [INFO] Backend .env was created from .env-example.
) else (
    echo [ERROR] Example file is missing: %BACKEND_ENV_EXAMPLE%
    popd
    exit /b 1
)

powershell -NoProfile -Command "$path = '%BACKEND_ENV%'; $lines = Get-Content -LiteralPath $path; if (-not $lines) { exit 1 }; if ($lines[0] -match '^MYSQL_ROOT_PASSWORD=\s*$') { $lines[0] = 'MYSQL_ROOT_PASSWORD=change_me_root_password'; Set-Content -LiteralPath $path -Value $lines -Encoding ASCII }"
if errorlevel 1 (echo [ERROR] MYSQL_ROOT_PASSWORD could not be set. & popd & exit /b 1)

if exist "%FRONTEND_ENV%" (
    echo [INFO] Frontend .env already exists.
) else if exist "%FRONTEND_ENV_EXAMPLE%" (
    copy /Y "%FRONTEND_ENV_EXAMPLE%" "%FRONTEND_ENV%" >nul
    if errorlevel 1 (echo [ERROR] Frontend .env could not be created from .env-example. & popd & exit /b 1)
    echo [INFO] Frontend .env was created from .env-example.
) else (
    echo [ERROR] Example file is missing: %FRONTEND_ENV_EXAMPLE%
    popd
    exit /b 1
)

echo [INFO] Starting database with Docker Compose...
pushd "%BACKEND_DOCKER_DIR%"
docker compose up -d
if errorlevel 1 (
    echo [ERROR] Docker Compose could not be started. Please start Docker Desktop or Docker CLI!
    popd
    popd
    exit /b 1
)
popd

echo [INFO] Waiting for MySQL on port 3306...
powershell -NoProfile -Command "$deadline = (Get-Date).AddMinutes(2); while ((Get-Date) -lt $deadline) { if (Test-NetConnection -ComputerName '127.0.0.1' -Port 3306 -InformationLevel Quiet) { exit 0 }; Start-Sleep -Seconds 2 }; exit 1"
if errorlevel 1 (echo [ERROR] MySQL was not reachable after 2 minutes. Backend will not be started. & popd & exit /b 1)
echo [INFO] MySQL is reachable.

if not exist "%FRONTEND_DIR%\node_modules" (
    echo [INFO] Frontend dependencies are missing, running npm install...
    pushd "%FRONTEND_DIR%"
    call npm install
    if errorlevel 1 (
        echo [ERROR] npm install failed.
        popd
        popd
        exit /b 1
    )
    popd
)

echo [INFO] Starting backend in a new window...
start "Boutique Hotel Backend" /D "%BACKEND_DIR%" cmd /k "title Boutique Hotel Backend && call mvnw.cmd spring-boot:run"

echo [INFO] Waiting for backend at http://127.0.0.1:8080/health...
powershell -NoProfile -Command "$deadline = (Get-Date).AddMinutes(3); while ((Get-Date) -lt $deadline) { try { $response = Invoke-WebRequest -UseBasicParsing -Uri 'http://127.0.0.1:8080/health' -TimeoutSec 5; if ($response.StatusCode -eq 200 -and $response.Content.Trim() -eq 'OK') { exit 0 } } catch { }; Start-Sleep -Seconds 2 }; exit 1"
if errorlevel 1 (echo [ERROR] Backend was not healthy after 3 minutes. Frontend will not be started. & popd & exit /b 1)
echo [INFO] Backend is reachable and healthy.

echo [INFO] Starting frontend in a new window...
start "Boutique Hotel Frontend" /D "%FRONTEND_DIR%" cmd /k "title Boutique Hotel Frontend && npm run dev"

echo [OK] Database, backend, and frontend are starting.
echo.
echo [INFO] This window will stay open. Press any key to close.
pause >nul
popd
exit /b 0

:stop
set "BACKEND_DIR=%ROOT_DIR%backend"
set "BACKEND_DOCKER_DIR=%BACKEND_DIR%\docker"

pushd "%ROOT_DIR%"

where docker >nul 2>&1
if errorlevel 1 (echo [ERROR] Docker is not installed or not found in PATH. & popd & exit /b 1)

echo [INFO] Stopping processes on port 8080 (backend)...
powershell -NoProfile -Command "$pids = netstat -ano | Select-String ':8080\s+\S+\s+\S+\s+(\d+)' | ForEach-Object { $_.Matches[0].Groups[1].Value } | Sort-Object -Unique; if ($pids) { $pids | ForEach-Object { Stop-Process -Id $_ -Force -ErrorAction SilentlyContinue }; Write-Host '  Killed PID(s):' ($pids -join ', ') } else { Write-Host '  No processes found on port 8080.' }"

echo [INFO] Stopping processes on port 5000 (frontend)...
powershell -NoProfile -Command "$pids = netstat -ano | Select-String ':5000\s+\S+\s+\S+\s+(\d+)' | ForEach-Object { $_.Matches[0].Groups[1].Value } | Sort-Object -Unique; if ($pids) { $pids | ForEach-Object { Stop-Process -Id $_ -Force -ErrorAction SilentlyContinue }; Write-Host '  Killed PID(s):' ($pids -join ', ') } else { Write-Host '  No processes found on port 5000.' }"

echo [INFO] Shutting down Docker Compose...
pushd "%BACKEND_DOCKER_DIR%"
docker compose down
if errorlevel 1 (
    echo [ERROR] Docker Compose could not be shut down.
    popd
    popd
    exit /b 1
)
popd

echo [OK] Backend, frontend, and database have been stopped.
popd
exit /b 0
