Deploying the Customer microservice built on Quarkus
=====


```
oc new-project banking

cat src/main/docker/Dockerfile.native | oc new-build --name customer --strategy=docker --dockerfile -

oc start-build customer --from-dir .

oc new-app --name=postgresql --template=postgresql-ephemeral \
    -p DATABASE_SERVICE_NAME=customer-database -p POSTGRESQL_USER=customer \
    -p POSTGRESQL_PASSWORD=mysecretpassword -p POSTGRESQL_DATABASE=customer \
    -p MEMORY_LIMIT=128Mi

oc label dc customer-database app.kubernetes.io/part-of=customer

oc new-app customer --name customer

oc expose svc/customer

oc label deployment customer app.kubernetes.io/part-of=customer
```