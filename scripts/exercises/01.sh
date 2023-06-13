#!/bin/sh
NAMESPACE=$(cat environment/.namespace) #name of your OpenShift namespace
BASE_URL=$(cat environment/.base-route) #base URL of your OpenShift namespace

curl --insecure https://microservice-person.$NAMESPACE.$BASE_URL/api/people
curl --insecure https://microservice-person.$NAMESPACE.$BASE_URL/api/people
curl --insecure https://microservice-person.$NAMESPACE.$BASE_URL/api/people
curl --insecure https://microservice-person.$NAMESPACE.$BASE_URL/api/people
curl --insecure https://microservice-person.$NAMESPACE.$BASE_URL/api/people
curl --insecure https://microservice-person.$NAMESPACE.$BASE_URL/api/people
curl --insecure https://microservice-movie.$NAMESPACE.$BASE_URL/api/movies
curl --insecure https://microservice-movie.$NAMESPACE.$BASE_URL/api/movies
curl --insecure https://microservice-movie.$NAMESPACE.$BASE_URL/api/movies
curl --insecure https://microservice-movie-tracking.$NAMESPACE.$BASE_URL/api/movie-tracking-records
curl --insecure https://microservice-movie-tracking.$NAMESPACE.$BASE_URL/api/movie-tracking-records
curl --insecure https://microservice-movie-tracking.$NAMESPACE.$BASE_URL/api/movie-tracking-records

curl --insecure --header "Content-Type: application/json" --request POST \
  --data '{"firstName":"Maarten-demo-1","lastName":"Curl", "birthDate": "1989-04-17"}' \
  https://microservice-person.$NAMESPACE.$BASE_URL/api/people

curl --insecure --header "Content-Type: application/json" --request POST \
  --data '{"firstName":"Maarten-demo-2","lastName":"Curl", "birthDate": "1989-04-17"}' \
  https://microservice-person.$NAMESPACE.$BASE_URL/api/people

curl --insecure --header "Content-Type: application/json" --request POST \
  --data '{"firstName":"Maarten-demo-3","lastName":"Curl", "birthDate": "1989-04-17"}' \
  https://microservice-person.$NAMESPACE.$BASE_URL/api/people

curl --insecure --header "Content-Type: application/json" --request POST \
  --data '{"firstName":"Maarten-demo-3","lastName":"Curl", "birthDate": "1989/04/17"}' \
  https://microservice-person.$NAMESPACE.$BASE_URL/api/people