#!/bin/sh
NAMESPACE=$(cat environment/.namespace) #name of your OpenShift namespace
BASE_URL=$(cat environment/.base-route) #base URL of your OpenShift namespace
VERSION="0.0.16" #version of the application
DOCKER_BASE_IMAGE="quay.io/appdev_playground/openshift-application-debug-exercise"

##################################################################################################
####################################### Microservice Person ######################################
##################################################################################################
  properties="$(cat openshift-configs/applications/microservice-person/deployment_config.yaml)"
  properties="$(echo "${properties//<NAMESPACE>/$NAMESPACE}")"
  properties="$(echo "${properties//<DOCKER_IMAGE>/$DOCKER_BASE_IMAGE:person-api-$VERSION}")"
  mv openshift-configs/applications/microservice-person/deployment_config.yaml openshift-configs/applications/microservice-person/deployment_config.backup.yaml
  echo "$properties" > openshift-configs/applications/microservice-person/deployment_config.yaml
  oc apply -f openshift-configs/applications/microservice-person/deployment_config.yaml
  mv openshift-configs/applications/microservice-person/deployment_config.backup.yaml openshift-configs/applications/microservice-person/deployment_config.yaml

  properties="$(cat openshift-configs/applications/microservice-person/service_config.yaml)"
  properties="$(echo "${properties//<NAMESPACE>/$NAMESPACE}")"
  mv openshift-configs/applications/microservice-person/service_config.yaml openshift-configs/applications/microservice-person/service_config.backup.yaml
  echo "$properties" > openshift-configs/applications/microservice-person/service_config.yaml
  oc apply -f openshift-configs/applications/microservice-person/service_config.yaml
  mv openshift-configs/applications/microservice-person/service_config.backup.yaml openshift-configs/applications/microservice-person/service_config.yaml

  properties="$(cat openshift-configs/applications/microservice-person/route_config.yaml)"
  properties="$(echo "${properties//<NAMESPACE>/$NAMESPACE}")"
  properties="$(echo "${properties//<HOST>/microservice-person.$NAMESPACE.$BASE_URL}")"
  mv openshift-configs/applications/microservice-person/route_config.yaml openshift-configs/applications/microservice-person/route_config.backup.yaml
  echo "$properties" > openshift-configs/applications/microservice-person/route_config.yaml
  oc apply -f openshift-configs/applications/microservice-person/route_config.yaml
  mv openshift-configs/applications/microservice-person/route_config.backup.yaml openshift-configs/applications/microservice-person/route_config.yaml

##################################################################################################
####################################### Microservice Movie #######################################
##################################################################################################
  properties="$(cat openshift-configs/applications/microservice-movie/deployment_config.yaml)"
  properties="$(echo "${properties//<NAMESPACE>/$NAMESPACE}")"
  properties="$(echo "${properties//<DOCKER_IMAGE>/$DOCKER_BASE_IMAGE:movie-api-$VERSION}")"
  mv openshift-configs/applications/microservice-movie/deployment_config.yaml openshift-configs/applications/microservice-movie/deployment_config.backup.yaml
  echo "$properties" > openshift-configs/applications/microservice-movie/deployment_config.yaml
  oc apply -f openshift-configs/applications/microservice-movie/deployment_config.yaml
  mv openshift-configs/applications/microservice-movie/deployment_config.backup.yaml openshift-configs/applications/microservice-movie/deployment_config.yaml

  properties="$(cat openshift-configs/applications/microservice-movie/service_config.yaml)"
  properties="$(echo "${properties//<NAMESPACE>/$NAMESPACE}")"
  mv openshift-configs/applications/microservice-movie/service_config.yaml openshift-configs/applications/microservice-movie/service_config.backup.yaml
  echo "$properties" > openshift-configs/applications/microservice-movie/service_config.yaml
  oc apply -f openshift-configs/applications/microservice-movie/service_config.yaml
  mv openshift-configs/applications/microservice-movie/service_config.backup.yaml openshift-configs/applications/microservice-movie/service_config.yaml

  properties="$(cat openshift-configs/applications/microservice-movie/route_config.yaml)"
  properties="$(echo "${properties//<NAMESPACE>/$NAMESPACE}")"
  properties="$(echo "${properties//<HOST>/microservice-movie.$NAMESPACE.$BASE_URL}")"
  mv openshift-configs/applications/microservice-movie/route_config.yaml openshift-configs/applications/microservice-movie/route_config.backup.yaml
  echo "$properties" > openshift-configs/applications/microservice-movie/route_config.yaml
  oc apply -f openshift-configs/applications/microservice-movie/route_config.yaml
  mv openshift-configs/applications/microservice-movie/route_config.backup.yaml openshift-configs/applications/microservice-movie/route_config.yaml


##################################################################################################
####################################### Microservice Movie Tracking ##############################
##################################################################################################
  properties="$(cat openshift-configs/applications/microservice-movie-tracking/deployment_config.yaml)"
  properties="$(echo "${properties//<NAMESPACE>/$NAMESPACE}")"
  properties="$(echo "${properties//<DOCKER_IMAGE>/$DOCKER_BASE_IMAGE:movie-tracking-api-$VERSION}")"
  mv openshift-configs/applications/microservice-movie-tracking/deployment_config.yaml openshift-configs/applications/microservice-movie-tracking/deployment_config.backup.yaml
  echo "$properties" > openshift-configs/applications/microservice-movie-tracking/deployment_config.yaml
  oc apply -f openshift-configs/applications/microservice-movie-tracking/deployment_config.yaml
  mv openshift-configs/applications/microservice-movie-tracking/deployment_config.backup.yaml openshift-configs/applications/microservice-movie-tracking/deployment_config.yaml

  properties="$(cat openshift-configs/applications/microservice-movie-tracking/service_config.yaml)"
  properties="$(echo "${properties//<NAMESPACE>/$NAMESPACE}")"
  mv openshift-configs/applications/microservice-movie-tracking/service_config.yaml openshift-configs/applications/microservice-movie-tracking/service_config.backup.yaml
  echo "$properties" > openshift-configs/applications/microservice-movie-tracking/service_config.yaml
  oc apply -f openshift-configs/applications/microservice-movie-tracking/service_config.yaml
  mv openshift-configs/applications/microservice-movie-tracking/service_config.backup.yaml openshift-configs/applications/microservice-movie-tracking/service_config.yaml

  properties="$(cat openshift-configs/applications/microservice-movie-tracking/route_config.yaml)"
  properties="$(echo "${properties//<NAMESPACE>/$NAMESPACE}")"
  properties="$(echo "${properties//<HOST>/microservice-movie-tracking.$NAMESPACE.$BASE_URL}")"
  mv openshift-configs/applications/microservice-movie-tracking/route_config.yaml openshift-configs/applications/microservice-movie-tracking/route_config.backup.yaml
  echo "$properties" > openshift-configs/applications/microservice-movie-tracking/route_config.yaml
  oc apply -f openshift-configs/applications/microservice-movie-tracking/route_config.yaml
  mv openshift-configs/applications/microservice-movie-tracking/route_config.backup.yaml openshift-configs/applications/microservice-movie-tracking/route_config.yaml

##################################################################################################
####################################### UI #######################################################
##################################################################################################
  properties="$(cat openshift-configs/applications/ui/deployment_config.yaml)"
  properties="$(echo "${properties//<NAMESPACE>/$NAMESPACE}")"
  properties="$(echo "${properties//<DOCKER_IMAGE>/$DOCKER_BASE_IMAGE:ui-$VERSION}")"
  mv openshift-configs/applications/ui/deployment_config.yaml openshift-configs/applications/ui/deployment_config.backup.yaml
  echo "$properties" > openshift-configs/applications/ui/deployment_config.yaml
  oc apply -f openshift-configs/applications/ui/deployment_config.yaml
  mv openshift-configs/applications/ui/deployment_config.backup.yaml openshift-configs/applications/ui/deployment_config.yaml

  properties="$(cat openshift-configs/applications/ui/service_config.yaml)"
  properties="$(echo "${properties//<NAMESPACE>/$NAMESPACE}")"
  mv openshift-configs/applications/ui/service_config.yaml openshift-configs/applications/ui/service_config.backup.yaml
  echo "$properties" > openshift-configs/applications/ui/service_config.yaml
  oc apply -f openshift-configs/applications/ui/service_config.yaml
  mv openshift-configs/applications/ui/service_config.backup.yaml openshift-configs/applications/ui/service_config.yaml

  properties="$(cat openshift-configs/applications/ui/route_config.yaml)"
  properties="$(echo "${properties//<NAMESPACE>/$NAMESPACE}")"
  properties="$(echo "${properties//<HOST>/ui.$NAMESPACE.$BASE_URL}")"
  mv openshift-configs/applications/ui/route_config.yaml openshift-configs/applications/ui/route_config.backup.yaml
  echo "$properties" > openshift-configs/applications/ui/route_config.yaml
  oc apply -f openshift-configs/applications/ui/route_config.yaml
  mv openshift-configs/applications/ui/route_config.backup.yaml openshift-configs/applications/ui/route_config.yaml



