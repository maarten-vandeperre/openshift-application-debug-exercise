#!/bin/sh
./mvnw compile -Dquarkus.profile=db-postgres quarkus:dev -Pmicroservice-person