#!/bin/bash
./gradlew clean test
google-chrome file:///$(pwd)/build/reports/tests/test/index.html
