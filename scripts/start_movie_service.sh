#!/bin/sh
./mvnw compile -Dquarkus.profile=db-physical quarkus:dev -Ddebug=5006 -Pmicroservice-movie