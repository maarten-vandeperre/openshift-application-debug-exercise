# OpenShift Application Debug Exercise
## Project outline
![Project Outline](images/openshift_application_debug_exercise_outline.jpg "Project Outline")
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
increase "VERSION" in /environment/.version and run /scripts/build_and_deploy_docker_images.sh
```shell
sh scripts/build_and_deploy_docker_images.sh
```
* Deploy the created docker images (i.e., applications) to OpenShift
```shell
sh scripts/apply_on_openshift.sh 
```
* You now should be able to access the following URLs via REST (CURL, browser, postman, ...)
  * ```text
    https://microservice-person.<NAMESPACE>.<BASE_URL>/api/people
    ```
  * ```text
    https://microservice-movie.<NAMESPACE>.<BASE_URL>/api/movies
    ```
  * ```text
    https://microservice-movie-tracking.<NAMESPACE>.<BASE_URL>/api/movie-tracking-records
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
  -e POSTGRES_DB=openshift_exercise \
  -e PGDATA=/tmp/data/pgdata \
  quay.io/appdev_playground/wal_postgres:0.0.2 \
  --name postgres
```
* Add default data to the postgres database
```text
Open the UI
Go to deployments > postgres > pods > pod > terminal
Execute: psql -h localhost -d openshift_exercise -U postgres -W 
Password: postgres
Copy-Paste and run db-init-scripts/postgres-person-api/001_setup_person_table.sql
Copy-Paste and run db-init-scripts/postgres-person-api/002_add_outbox_tables.sql
```
* Add default data to the mongodb database
```text
Open the UI
Go to deployments > mongodb > pods > pod > terminal
Execute: mongo mongodb://mongo:mongo@localhost:27017
Copy-Paste and run db-init-scripts/mongodb-movie-api/mongo-init.js (it can be that you need to copy-paste collection per collection)
```
* Add the databases to the microservices
```shell
sh scripts/patch_link_databases.sh
```
* You now should be able to access the following URLs via REST (CURL, browser, postman, ...)
  * ```text
    https://microservice-person.<NAMESPACE>.<BASE_URL>/api/people
    ```
  * ```text
    https://microservice-movie.<NAMESPACE>.<BASE_URL>/api/movies
    ```
  * ```text
    https://microservice-movie-tracking.<NAMESPACE>.<BASE_URL>/api/movie-tracking-records
    ```
* You now can access the application (with in memory databases) on
```text
  https://ui.<NAMESPACE>.<BASE_URL>
```
### Enable Aggregated Container Logs
* Install the 'OpenShift Elasticsearch Operator' supported by Red Hat operator
* Install the 'Red Hat OpenShift Logging' supported by Red Hat operator
  * Ensure that the A specific namespace on the cluster is selected under Installation Mode. 
  * Ensure that Operator recommended namespace is openshift-logging under Installed Namespace.
  * Select Enable operator recommended cluster monitoring on this namespace.  
  _You must select this option to ensure that cluster monitoring scrapes the openshift-logging namespace._
* Create a cluster logging configuration:
```shell
oc apply -f openshift-configs/enable-logging/cluster_logging.yaml
```
* Define index pattern in Kibana
  * In the OpenShift Container Platform console, click the Application Launcher app launcher and select Logging. 
  * Create your Kibana index patterns by clicking Management > Index Patterns > Create index pattern:
    * Each user must manually create index patterns when logging into Kibana the first time to see logs for their projects. Users must create an index pattern named app and use the @timestamp time field to view their container logs. 
      * Each admin user must create index patterns when logged into Kibana the first time for the app, infra, and audit indices using the @timestamp time field.
  * Create Kibana Visualizations from the new index patterns.
  * Start visualizing.



