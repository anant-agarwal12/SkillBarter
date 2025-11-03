@echo off
echo Starting SkillBarter Backend...
echo.

REM Try to use Maven if available
where mvn >nul 2>&1
if %ERRORLEVEL% EQU 0 (
    echo Using installed Maven...
    mvn spring-boot:run
    goto :end
)

REM Try Maven wrapper
if exist mvnw.cmd (
    echo Using Maven wrapper...
    call mvnw.cmd spring-boot:run
    goto :end
)

echo.
echo ERROR: Maven not found!
echo.
echo Please either:
echo 1. Install Maven and add to PATH
echo 2. Use IntelliJ IDEA to run BackendApplication.java
echo.
pause
:end

