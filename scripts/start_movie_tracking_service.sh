#!/bin/sh
./mvnw compile -Dquarkus.profile=db-physical quarkus:dev -Ddebug=5007 -Pmicroservice-movie-tracking