#!/usr/bin/env just --justfile

# Bring DB for local development
local:
  docker-compose down
  docker-compose -p url-shortener up db

# Run full backend via Docker
run:
  docker-compose down
  docker rmi url/shortener:latest
  docker-compose -p url-shortener up

test:
  ./gradlew clean test

# Some Script
some-script:
  ./scripts/some-script.sh