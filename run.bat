@echo off
echo ============================================
echo   		DKHP - Docker Run
echo ============================================
echo.

echo Building and starting containers...
docker-compose up --build -d

echo.
echo Waiting for Oracle DB to be ready...
:wait_loop
docker-compose ps | findstr "oracle-xe" | findstr "(healthy)" >nul
if errorlevel 1 (
    timeout /t 5 >nul
    goto wait_loop
)
echo Oracle DB is ready!

echo.
echo ============================================
echo   App is starting at: http://localhost:8080
echo   UI: http://localhost:8080/index.html
echo ============================================
echo.
echo To stop: docker-compose down
echo To view logs: docker-compose logs -f
echo.
