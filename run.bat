@echo off
echo ============================================
echo   Course Registration System - Docker Run
echo ============================================
echo.

echo Building and starting containers...
docker-compose up --build -d

echo.
echo Waiting for Oracle DB to be ready...
docker-compose logs -f oracle-db | findstr /C:"Database is ready"

echo.
echo ============================================
echo   App is starting at: http://localhost:8080
echo   UI: http://localhost:8080/index.html
echo ============================================
echo.
echo To stop: docker-compose down
echo To view logs: docker-compose logs -f
echo.
