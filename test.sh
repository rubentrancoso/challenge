#!/bin/bash
./gradlew clean test
google-chrome file:///$(pwd)/build/reports/tests/test/classes/com.company.challenge.userapi.ItApplication.html
