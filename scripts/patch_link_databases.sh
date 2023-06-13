#!/bin/sh
NAMESPACE=$(cat environment/.namespace) #name of your OpenShift namespace
BASE_URL=$(cat environment/.base-route) #base URL of your OpenShift namespace
VERSION=$(cat environment/.version) #version of the application
DOCKER_BASE_IMAGE="quay.io/appdev_playground/openshift-application-debug-exercise"

##################################################################################################
####################################### Microservice Person ######################################
##################################################################################################
  properties="$(cat openshift-configs/patches-link-databases/microservice-person/deployment_config.yaml)"
  properties="$(echo "${properties//<NAMESPACE>/$NAMESPACE}")"
  properties="$(echo "${properties//<DOCKER_IMAGE>/$DOCKER_BASE_IMAGE:person-api-$VERSION}")"
  mv openshift-configs/patches-link-databases/microservice-person/deployment_config.yaml openshift-configs/patches-link-databases/microservice-person/deployment_config.backup.yaml
  echo "$properties" > openshift-configs/patches-link-databases/microservice-person/deployment_config.yaml
  oc apply -f openshift-configs/patches-link-databases/microservice-person/deployment_config.yaml
  mv openshift-configs/patches-link-databases/microservice-person/deployment_config.backup.yaml openshift-configs/patches-link-databases/microservice-person/deployment_config.yaml

##################################################################################################
####################################### Microservice Movie #######################################
##################################################################################################
  properties="$(cat openshift-configs/patches-link-databases/microservice-movie/deployment_config.yaml)"
  properties="$(echo "${properties//<NAMESPACE>/$NAMESPACE}")"
  properties="$(echo "${properties//<DOCKER_IMAGE>/$DOCKER_BASE_IMAGE:movie-api-$VERSION}")"
  mv openshift-configs/patches-link-databases/microservice-movie/deployment_config.yaml openshift-configs/patches-link-databases/microservice-movie/deployment_config.backup.yaml
  echo "$properties" > openshift-configs/patches-link-databases/microservice-movie/deployment_config.yaml
  oc apply -f openshift-configs/patches-link-databases/microservice-movie/deployment_config.yaml
  mv openshift-configs/patches-link-databases/microservice-movie/deployment_config.backup.yaml openshift-configs/patches-link-databases/microservice-movie/deployment_config.yaml

##################################################################################################
####################################### Microservice Movie Tracking ##############################
##################################################################################################
  properties="$(cat openshift-configs/patches-link-databases/microservice-movie-tracking/deployment_config.yaml)"
  properties="$(echo "${properties//<NAMESPACE>/$NAMESPACE}")"
  properties="$(echo "${properties//<DOCKER_IMAGE>/$DOCKER_BASE_IMAGE:movie-tracking-api-$VERSION}")"
  mv openshift-configs/patches-link-databases/microservice-movie-tracking/deployment_config.yaml openshift-configs/patches-link-databases/microservice-movie-tracking/deployment_config.backup.yaml
  echo "$properties" > openshift-configs/patches-link-databases/microservice-movie-tracking/deployment_config.yaml
  oc apply -f openshift-configs/patches-link-databases/microservice-movie-tracking/deployment_config.yaml
  mv openshift-configs/patches-link-databases/microservice-movie-tracking/deployment_config.backup.yaml openshift-configs/patches-link-databases/microservice-movie-tracking/deployment_config.yaml



