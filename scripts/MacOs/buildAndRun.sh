#!/bin/bash
cd ../..
./gradlew clean build
java -jar build/libs/booking-app-1.0.0.jar
