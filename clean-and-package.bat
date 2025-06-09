@echo off
setlocal enabledelayedexpansion

echo [1/4] Cleaning build directories...

for %%d in (build .gradle out bin target) do (
    if exist "%%d" (
        echo    Removing %%d...
        rmdir /s /q "%%d"
    )
)

echo [2/4] Ensuring only source files remain...

set ZIP_NAME=challenge-clean.zip

echo [3/4] Packaging source code in STORE mode (no compression)...

REM Remove old archive if exists
if exist "%ZIP_NAME%" del /f /q "%ZIP_NAME%"

REM Create temporary list of files to include
set FILE_LIST=files-to-zip.txt
del /f /q "%FILE_LIST%" 2>nul

REM Include only clean source code files
for /r %%f in (*) do (
    set FILE=%%f
    echo !FILE! | findstr /i /v "\.class$ \.jar$ \.war$ \.zip$ \.iml$ \.log$ \.exe$ \.dll$ \.so$ \.bin$" >nul && (
    echo !FILE! | findstr /i /v "\\build\\ \\out\\ \\.gradle\\ \\.git\\ \\.idea\\ \\.settings\\ \\.vscode\\" >nul && (
        echo !FILE!>> "%FILE_LIST%"
    ))
)

REM Compress only the selected files
powershell -Command "Compress-Archive -Path (Get-Content '%FILE_LIST%') -DestinationPath '%ZIP_NAME%' -CompressionLevel NoCompression"

REM Cleanup
del /f /q "%FILE_LIST%"

echo [4/4] Archive created: %ZIP_NAME%
pause
