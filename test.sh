#!/bin/bash
./gradlew clean test
google-chrome build/reports/tests/test/classes/com.company.challenge.userapi.ApplicationTests.html
