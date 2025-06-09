@echo off
echo [1/4] Cleaning build artifacts...
rmdir /s /q build 2>nul
rmdir /s /q .gradle 2>nul

echo [2/4] Removing old ZIP if exists...
del challenge-clean.zip >nul 2>nul

echo [3/4] Zipping project (source only, no binaries)...
powershell -NoProfile -Command ^
    "Compress-Archive -Path (Get-ChildItem -Recurse -File | Where-Object { $_.FullName -notmatch '\\\\(build|\.gradle|\.git|\.classpath|\.project|\.settings)\\\\' }) -DestinationPath 'challenge-clean.zip' -CompressionLevel NoCompression"

echo [4/4] Archive created: challenge-clean.zip
pause
