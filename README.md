# OpenShift Application Debug Exercise
## OpenShift Setup
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