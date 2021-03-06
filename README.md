## Description

Spring Boot Web `Micrometer` metrics sample project for `Prometheus` + `Grafana`.

## How to start

1. Run `docker-compose up`
2. Run `./mvnw package`
3. Run `java -jar target/libs/demo-micrometer-0.0.1.jar`. The application will start on port `8080`.

---

## Application

The property `management.metrics.tags.application` configures label "application" for all metrics
exported from this service.
URL to metrics for prometheus: http://localhost:8080/actuator/prometheus

## Grafana

URL: http://localhost:3000/

### Configuration

#### User

```text
Username: admin
Password: adminadmin
```

#### Datasource

Default datasource "Prometheus" creates on start.

It's defined
in [grafana/provisioning/datasources/prometheus_ds.yaml](grafana/provisioning/datasources/prometheus_ds.yaml)

#### Default dashboards

Import configured
in [grafana/provisioning/dashboards/all.yaml](grafana/provisioning/dashboards/all.yaml) to load all
boards from [grafana/provisioning/dashboards](grafana/provisioning/dashboards) (see also volume's
mount for `grafana` in [docker-compose.yaml](docker-compose.yaml))

- https://grafana.com/grafana/dashboards/6756
- custom board for custom metric

---

## Prometheus

URL: http://localhost:9090/graph

### Configuration

Prometheus config is defined in [prometheus/prometheus.yaml](prometheus/prometheus.yaml)

---

## Additional links

- Community dashboards hub: https://grafana.com/grafana/dashboards/
- Spring prometheus docs: https://docs.spring.io/spring-metrics/docs/current/public/prometheus
- Creating Monitoring Dashboards
  guideline: https://medium.com/expedia-group-tech/creating-monitoring-dashboards-1f3fbe0ae1ac
- A Deep Dive Into the Four Types of Prometheus
  Metrics: https://www.timescale.com/blog/four-types-prometheus-metrics-to-collect/