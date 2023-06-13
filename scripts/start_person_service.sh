#!/bin/sh
./mvnw compile -Dquarkus.profile=db-postgres quarkus:dev -Ddebug=5005 -Pmicroservice-person