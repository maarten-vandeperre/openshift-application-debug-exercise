#!/bin/sh
NAMESPACE=$(cat environment/.namespace) #name of your OpenShift namespace
VERSION="0.1.0" #version of the application
DOCKER_BASE_IMAGE="quay.io/appdev_playground/openshift-application-debug-exercise"


mvn clean install

##################################################################################################
####################################### Microservice Person ######################################
##################################################################################################

  properties="$(cat application/configuration/microservice-person-configuration/src/main/resources/application.properties)"
  properties="$(echo "${properties//<NAMESPACE>/$NAMESPACE}")"
  mv application/configuration/microservice-person-configuration/src/main/resources/application.properties application/configuration/microservice-person-configuration/src/main/resources/application_backup.properties
  echo "$properties" > application/configuration/microservice-person-configuration/src/main/resources/application.properties
  #build uber jar for microservice-person
  ./mvnw package -Dquarkus.package.type=uber-jar -Pmicroservice-person
  #dockerize it
  docker build -t "$DOCKER_BASE_IMAGE:person-api-$VERSION" -f ./application/configuration/microservice-person-configuration/src/main/docker/Dockerfile_UberJar ./application/configuration/microservice-person-configuration --platform linux/amd64
  #push docker image
  docker push "$DOCKER_BASE_IMAGE:person-api-$VERSION"
  mv application/configuration/microservice-person-configuration/src/main/resources/application_backup.properties application/configuration/microservice-person-configuration/src/main/resources/application.properties

##################################################################################################
####################################### Microservice Movie #######################################
##################################################################################################

  properties="$(cat application/configuration/microservice-movie-configuration/src/main/resources/application.properties)"
  properties="$(echo "${properties//<NAMESPACE>/$NAMESPACE}")"
  mv application/configuration/microservice-movie-configuration/src/main/resources/application.properties application/configuration/microservice-movie-configuration/src/main/resources/application_backup.properties
  echo "$properties" > application/configuration/microservice-movie-configuration/src/main/resources/application.properties
  #build uber jar for microservice-movie
  ./mvnw package -Dquarkus.package.type=uber-jar -Pmicroservice-movie
  #dockerize it
  docker build -t "$DOCKER_BASE_IMAGE:movie-api-$VERSION" -f ./application/configuration/microservice-movie-configuration/src/main/docker/Dockerfile_UberJar ./application/configuration/microservice-movie-configuration --platform linux/amd64
  #push docker image
  docker push "$DOCKER_BASE_IMAGE:movie-api-$VERSION"
  mv application/configuration/microservice-movie-configuration/src/main/resources/application_backup.properties application/configuration/microservice-movie-configuration/src/main/resources/application.properties


##################################################################################################
####################################### Microservice Movie Tracking ##############################
##################################################################################################

  properties="$(cat application/configuration/microservice-movie-tracking-configuration/src/main/resources/application.properties)"
  properties="$(echo "${properties//<NAMESPACE>/$NAMESPACE}")"
  mv application/configuration/microservice-movie-tracking-configuration/src/main/resources/application.properties application/configuration/microservice-movie-tracking-configuration/src/main/resources/application_backup.properties
  echo "$properties" > application/configuration/microservice-movie-tracking-configuration/src/main/resources/application.properties
  #build uber jar for microservice-movie-tracking
  ./mvnw package -Dquarkus.package.type=uber-jar -Pmicroservice-movie-tracking
  #dockerize it
  docker build -t "$DOCKER_BASE_IMAGE:movie-tracking-api-$VERSION" -f ./application/configuration/microservice-movie-tracking-configuration/src/main/docker/Dockerfile_UberJar ./application/configuration/microservice-movie-tracking-configuration --platform linux/amd64
  #push docker image
  docker push "$DOCKER_BASE_IMAGE:movie-tracking-api-$VERSION"
  mv application/configuration/microservice-movie-tracking-configuration/src/main/resources/application_backup.properties application/configuration/microservice-movie-tracking-configuration/src/main/resources/application.properties


###################################################################################################
######################################## UI #######################################################
###################################################################################################
##  oc delete all -l app=ui
#  cd ui/crud-application
#  npm install
#  npm run build
#  cd ../..
#  docker build -t "$DOCKER_BASE_IMAGE:ui-$VERSION" -f ./ui/crud-application/Dockerfile_nginx ./ui/crud-application --platform linux/amd64
#  docker push "$DOCKER_BASE_IMAGE:ui-$VERSION"

