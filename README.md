# OpenShift Application Debug Exercise
## OpenShift Setup
### Application Deployments
* Create a project within OpenShift 
```shell
oc login ... # do log in
```
```shell
oc new-project sample-project # create project
oc project sample-project # make sure you have your project active
```
* Store the name of the project it in 
```text
/environment/.namespace
```
* Store the base route to services/deployments/... this project in 
```text
/environment/.base-route
```
* **Optional**  _(you can as well use the already published docker images in next step)_
increase "VERSION" in /scripts/build_and_deploy_docker_images.sh and run it
```shell
sh scripts/build_and_deploy_docker_images.sh
```
* Deploy the created docker images (i.e., applications) to OpenShift
```shell
sh scripts/apply_on_openshift.sh 
```
* You now should be able to access the following URLs via REST (CURL, browser, postman, ...)
  * ```text
    https://microservice-person.<NAMESPACE>.<BASE_URL>
    ```
  * ```text
    https://microservice-movie.<NAMESPACE>.<BASE_URL>
    ```
  * ```text
    https://microservice-movie-tracking.<NAMESPACE>.<BASE_URL>
    ```
* You now can access the application (with in memory databases) on
```text
  https://ui.<NAMESPACE>.<BASE_URL>
```
!!!_At the time of writing, there is still a bug in the in memory databases, so linking to newly created people will fail. See next sections to implement a database connection._

### Infrastructure setup
* Create a MongoDB database
```shell
oc new-app \
  -e MONGO_INITDB_ROOT_USERNAME=mongo \
  -e MONGO_INITDB_ROOT_PASSWORD=mongo \
  mongo:4.2.24 \
  --name mongodb
```
* Create a Postgres database  
_This is a custom image with WAL enabled in order to allow Debezium to connect to it. You can find the source container file at /openshift-configs/dockerfiles/DockerfilePostgresWal_
```shell
oc new-app \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=postgres \
  -e POSTGRES_DB=knative_demo \
  -e PGDATA=/tmp/data/pgdata \
  quay.io/appdev_playground/wal_postgres:0.0.2 \
  --name postgres
```


* 


* 
