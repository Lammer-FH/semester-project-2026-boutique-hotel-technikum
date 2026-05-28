@echo off
setlocal EnableExtensions EnableDelayedExpansion

set "ROOT_DIR=%~dp0"
set "BACKEND_DIR=%ROOT_DIR%backend"
set "BACKEND_DOCKER_DIR=%BACKEND_DIR%\docker"
set "BACKEND_ENV=%BACKEND_DIR%\.env"
set "BACKEND_ENV_EXAMPLE=%BACKEND_DIR%\.env-example"
set "FRONTEND_DIR=%ROOT_DIR%frontend"
set "FRONTEND_ENV=%FRONTEND_DIR%\.env"

pushd "%ROOT_DIR%"

call :require_tool docker Docker
if errorlevel 1 goto :fail

call :require_tool java Java
if errorlevel 1 goto :fail

call :require_tool node Node.js
if errorlevel 1 goto :fail

call :require_tool npm npm
if errorlevel 1 goto :fail

call :ensure_backend_env
if errorlevel 1 goto :fail

call :ensure_mysql_root_password
if errorlevel 1 goto :fail

call :ensure_frontend_env
if errorlevel 1 goto :fail

echo [INFO] Starting database with Docker Compose...
pushd "%BACKEND_DOCKER_DIR%"
docker compose up -d
if errorlevel 1 (
    echo [ERROR] Docker Compose could not be started.
    popd
    goto :fail
)
popd

call :wait_for_mysql
if errorlevel 1 goto :fail

if not exist "%FRONTEND_DIR%\node_modules" (
    echo [INFO] Frontend dependencies are missing, running npm install...
    pushd "%FRONTEND_DIR%"
    call npm install
    if errorlevel 1 (
        echo [ERROR] npm install failed.
        popd
        goto :fail
    )
    popd
)

echo [INFO] Starting backend in a new window...
start "Boutique Hotel Backend" /D "%BACKEND_DIR%" cmd /k "title Boutique Hotel Backend && call mvnw.cmd spring-boot:run"

call :wait_for_backend
if errorlevel 1 goto :fail

echo [INFO] Starting frontend in a new window...
start "Boutique Hotel Frontend" /D "%FRONTEND_DIR%" cmd /k "title Boutique Hotel Frontend && npm run dev"

echo [OK] Database, backend, and frontend are starting.
echo.
echo [INFO] This window will stay open. Press any key to close.
pause >nul
popd
exit /b 0

:require_tool
where %1 >nul 2>&1
if errorlevel 1 (
    echo [ERROR] %2 is not installed or not found in PATH.
    exit /b 1
)
exit /b 0

:ensure_backend_env
if exist "%BACKEND_ENV%" (
    echo [INFO] Backend .env already exists.
    exit /b 0
)

if not exist "%BACKEND_ENV_EXAMPLE%" (
    echo [ERROR] Example file is missing: %BACKEND_ENV_EXAMPLE%
    exit /b 1
)

copy /Y "%BACKEND_ENV_EXAMPLE%" "%BACKEND_ENV%" >nul
if errorlevel 1 (
    echo [ERROR] Backend .env could not be created from .env-example.
    exit /b 1
)

echo [INFO] Backend .env was created from .env-example.
exit /b 0

:ensure_mysql_root_password
powershell -NoProfile -Command "$path = '%BACKEND_ENV%'; $lines = Get-Content -LiteralPath $path; if (-not $lines) { exit 1 }; if ($lines[0] -match '^MYSQL_ROOT_PASSWORD=\s*$') { $lines[0] = 'MYSQL_ROOT_PASSWORD=change_me_root_password'; Set-Content -LiteralPath $path -Value $lines -Encoding ASCII }"
if errorlevel 1 (
    echo [ERROR] MYSQL_ROOT_PASSWORD could not be set.
    exit /b 1
)
exit /b 0

:wait_for_mysql
echo [INFO] Waiting for MySQL on port 3306...
powershell -NoProfile -Command "$deadline = (Get-Date).AddMinutes(2); while ((Get-Date) -lt $deadline) { if (Test-NetConnection -ComputerName '127.0.0.1' -Port 3306 -InformationLevel Quiet) { exit 0 }; Start-Sleep -Seconds 2 }; exit 1"
if errorlevel 1 (
    echo [ERROR] MySQL was not reachable after 2 minutes. Backend will not be started.
    exit /b 1
)
echo [INFO] MySQL is reachable.
exit /b 0

:wait_for_backend
echo [INFO] Waiting for backend at http://127.0.0.1:8080/health...
powershell -NoProfile -Command "$deadline = (Get-Date).AddMinutes(3); while ((Get-Date) -lt $deadline) { try { $response = Invoke-WebRequest -UseBasicParsing -Uri 'http://127.0.0.1:8080/health' -TimeoutSec 5; if ($response.StatusCode -eq 200 -and $response.Content.Trim() -eq 'OK') { exit 0 } } catch { }; Start-Sleep -Seconds 2 }; exit 1"
if errorlevel 1 (
    echo [ERROR] Backend was not healthy after 3 minutes. Frontend will not be started.
    exit /b 1
)
echo [INFO] Backend is reachable and healthy.
exit /b 0

:ensure_frontend_env
if exist "%FRONTEND_ENV%" (
    echo [INFO] Frontend .env already exists.
    exit /b 0
)

>"%FRONTEND_ENV%" echo VITE_API_BASE_URL=http://localhost:8080/api/v1
if errorlevel 1 (
    echo [ERROR] Frontend .env could not be created.
    exit /b 1
)

echo [INFO] Frontend .env was created.
exit /b 0

:fail
echo.
echo [INFO] This window will stay open. Press any key to close.
pause >nul
popd
exit /b 1