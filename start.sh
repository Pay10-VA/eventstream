#!/bin/bash

cd analytics-api
docker compose up -d
sleep 5
mvn spring-boot:run