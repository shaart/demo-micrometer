### Description
Spring Boot Web `Micrometer` metrics sample project for `Prometheus` + `Grafana`. 

### How to start
1. Run `docker-compose up`
2. Run `./mvnw package`
3. Run `java -jar target/libs/demo-micrometer-0.0.1.jar`. The application will start on port `8080`.

---

### Grafana
URL: http://localhost:3000/
#### Configuration
##### User
```text
Username: admin
Password: adminadmin
```
##### Datasource
Default datasource "Prometheus" creates on start.

It's defined in [grafana/provisioning/datasources/prometheus_ds.yaml](grafana/provisioning/datasources/prometheus_ds.yaml)

---

### Prometheus
URL: http://localhost:9090/graph
#### Configuration
Prometheus config is defined in [prometheus/prometheus.yaml](prometheus/prometheus.yaml)