# Quarkus demo: Hibernate ORM with Panache and RESTEasy

This is a minimal CRUD service exposing a couple of endpoints over REST,
with a front-end based on Angular so you can play with it from your browser.

While the code is surprisingly simple, under the hood this is using:
 - RESTEasy to expose the REST endpoints
 - Hibernate ORM with Panache to perform the CRUD operations on the database
 - A PostgreSQL database; see below to run one via Docker
 - ArC, the CDI inspired dependency injection tool with zero overhead
 - The high performance Agroal connection pool
 - Infinispan based caching
 - All safely coordinated by the Narayana Transaction Manager

## Requirements

To compile and run this demo you will need:
- GraalVM `1.0 rc12`
- Apache Maven `3.5.3+`

In addition, you will need either a PostgreSQL database, or Docker to run one.

If you don't have GraalVM installed, you can download it here:

<https://github.com/oracle/graal/releases>

Installing GraalVM is very similar to installing any other JDK:
just unpack it, add it to your path, and point the `JAVA_HOME`
and `GRAALVM_HOME` environment variables to it.

You should then use this JDK to run the Maven build.


## Building the demo

After having set GraalVM as your JVM, launch the Maven build on
the checked out sources of this demo:

> mvn package

## Running the demo

### Prepare a PostgreSQL instance

First we will need a PostgreSQL database; you can launch one easily if you have Docker installed:

> docker run --ulimit memlock=-1:-1 -it --rm=true --memory-swappiness=0 --name quarkus_test -e POSTGRES_USER=quarkus_test -e POSTGRES_PASSWORD=quarkus_test -e POSTGRES_DB=quarkus_test -p 5432:5432 postgres:10.5

Alternatively you can setup a PostgreSQL instance in any another way.

The connection properties of the Agroal datasource are configured in the standard Quarkus configuration file, which you will find in
`src/main/resources/application.properties`.

### Run Quarkus in developer mode

To run the application in interactive mode (developer mode):

>  mvn compile quarkus:dev

In this mode you can make changes to the code and have the changes immediately applied, by just refreshing your browser.

    Hot reload works even when modifying your JPA entities.
    Try it! Even the database schema will be updated on the fly.

### Run Quarkus in JVM mode

When you're done playing with "dev-mode" you can run it as a standard Java application.

First compile it:

> mvn package

Then run it:

> java -jar ./target/hibernate-orm-panache-resteasy-1.0-SNAPSHOT-runner.jar

    Have a look at how fast it boots.
    Or measure total native memory consumption...

### Run Quarkus as a native application

This same demo can be compiled into native code: no modifications required.

This implies that you no longer need to install a JVM on your production environment, as the runtime technology is included in the produced binary, and optimized to run with minimal resource overhead.

Compilation will take a bit longer, so this step is disabled by default;
let's build again by enabling the `native` profile:

> mvn package -Dnative

After getting a cup of coffee, you'll be able to run this binary directly:

> ./target/hibernate-orm-panache-resteasy-1.0-SNAPSHOT-runner

    Please brace yourself: don't choke on that fresh cup of coffee you just got.
    
    Now observe the time it took to boot, and remember: that time was mostly spent to generate the tables in your database and import the initial data.
    
    Next, maybe you're ready to measure how much memory this service is consuming.

N.B. This implies all dependencies have been compiled to native;
that's a whole lot of stuff: from the bytecode enhancements that Panache
applies to your entities, to the lower level essential components such as the PostgreSQL JDBC driver, the Undertow webserver.

## See the demo in your browser

Navigate to:

<http://localhost:8080/index.html>

Have fun, and join the team of contributors!








----

[user@localhost customer]$ oc new-project banking

Now using project "banking" on server "https://api.crc.testing:6443".

You can add applications to this project with the 'new-app' command. For example, try:

    oc new-app ruby~https://github.com/sclorg/ruby-ex.git

to build a new example application in Ruby. Or use kubectl to deploy a simple Kubernetes application:

    kubectl create deployment hello-node --image=gcr.io/hello-minikube-zero-install/hello-node


[user@localhost customer]$ cat src/main/docker/Dockerfile.native | oc new-build --name customer --strategy=docker --dockerfile -
--> Found container image 91d23a6 (6 months old) from registry.access.redhat.com for "registry.access.redhat.com/ubi8/ubi-minimal:8.1"

    Red Hat Universal Base Image 8 Minimal 
    -------------------------------------- 
    The Universal Base Image Minimal is a stripped down image that uses microdnf as a package manager. This base image is freely redistributable, but Red Hat only supports Red Hat technologies through subscriptions for Red Hat products. This image is maintained by Red Hat and updated regularly.

    Tags: minimal rhel8

    * An image stream tag will be created as "ubi-minimal:8.1" that will track the source image
    * A Docker build using a predefined Dockerfile will be created
      * The resulting image will be pushed to image stream tag "customer:latest"
      * Every time "ubi-minimal:8.1" changes a new build will be triggered

--> Creating resources with label build=customer ...
    imagestream.image.openshift.io "ubi-minimal" created
    imagestream.image.openshift.io "customer" created
    buildconfig.build.openshift.io "customer" created
--> Success

[user@localhost customer]$ oc start-build customer --from-dir .
Uploading directory "." as binary input for the build ...
.........................
Uploading finished
build.build.openshift.io/customer-2 started
[user@localhost customer]$ 
[user@localhost customer]$ 
[user@localhost customer]$ 
[user@localhost customer]$ 
[user@localhost customer]$ 
[user@localhost customer]$ 

[user@localhost customer]$ oc new-app --name=postgresql --template=postgresql-ephemeral \
    -p DATABASE_SERVICE_NAME=customer-database -p POSTGRESQL_USER=customer \
    -p POSTGRESQL_PASSWORD=mysecretpassword -p POSTGRESQL_DATABASE=customer \
    -p MEMORY_LIMIT=128Mi

--> Deploying template "openshift/postgresql-ephemeral" to project banking

     PostgreSQL (Ephemeral)
     ---------
     PostgreSQL database service, without persistent storage. For more information about using this template, including OpenShift considerations, see https://github.com/sclorg/postgresql-container/.
     
     WARNING: Any data stored will be lost upon pod destruction. Only use this template for testing

     The following service(s) have been created in your project: customer-database.
     
            Username: customer
            Password: mysecretpassword
       Database Name: customer
      Connection URL: postgresql://customer-database:5432/
     
     For more information about using this template, including OpenShift considerations, see https://github.com/sclorg/postgresql-container/.

     * With parameters:
        * Memory Limit=128Mi
        * Namespace=openshift
        * Database Service Name=customer-database
        * PostgreSQL Connection Username=customer
        * PostgreSQL Connection Password=mysecretpassword
        * PostgreSQL Database Name=customer
        * Version of PostgreSQL Image=10

--> Creating resources ...
    secret "customer-database" created
    service "customer-database" created
    deploymentconfig.apps.openshift.io "customer-database" created
--> Success
    Application is not exposed. You can expose services to the outside world by executing one or more of the commands below:
     'oc expose svc/customer-database' 
    Run 'oc status' to view your app.


[rmarins@hydrofoil customer]$ oc label dc customer-database app.kubernetes.io/part-of=customer
deploymentconfig.apps.openshift.io/customer-database labeled

[user@localhost customer]$ oc get is
NAME          IMAGE REPOSITORY                                                              TAGS     UPDATED
customer      default-route-openshift-image-registry.apps-crc.testing/banking/customer      latest   26 minutes ago
ubi-minimal   default-route-openshift-image-registry.apps-crc.testing/banking/ubi-minimal   8.1      29 minutes ago

[user@localhost customer]$ oc new-app customer --name customer
--> Found image 13edc19 (26 minutes old) in image stream "banking/customer" under tag "latest" for "customer"

    Red Hat Universal Base Image 8 Minimal 
    -------------------------------------- 
    The Universal Base Image Minimal is a stripped down image that uses microdnf as a package manager. This base image is freely redistributable, but Red Hat only supports Red Hat technologies through subscriptions for Red Hat products. This image is maintained by Red Hat and updated regularly.

    Tags: minimal rhel8


--> Creating resources ...
    deployment.apps "customer" created
    service "customer" created
--> Success
    Application is not exposed. You can expose services to the outside world by executing one or more of the commands below:
     'oc expose svc/customer' 
    Run 'oc status' to view your app.

[user@localhost customer]$ oc expose svc/customer
route.route.openshift.io/customer exposed

[rmarins@hydrofoil customer]$ oc label deployment customer app.kubernetes.io/part-of=customer
deployment.apps/customer labeled

[user@localhost customer]$