# Immverse-ai
DevOps  Task Completion
GITHUB WEBHOOK TRIGGER ADDED

## Sample App

This application is containerized using Docker and published to Docker Hub.

## Docker Image

Docker Hub Repository:
https://hub.docker.com/repository/docker/anilbamnote/sample-app/general

Pull the image:

```bash
docker pull anilbamnote/sample-app:latest
```


# DevOps CI/CD Pipeline Assignment

## Overview

This project demonstrates a complete CI/CD pipeline using:

* GitHub
* Jenkins
* Docker
* Docker Hub
* Kubernetes

The pipeline automatically builds, tests, pushes, and deploys a containerized Node.js application whenever code is pushed to GitHub.

---

## Project Architecture

Developer (push code)
↓
GitHub Repository
↓
Jenkins Pipeline
↓
Docker Image Build
↓
Docker Hub Push
↓
Docker/Kubernetes Deployment

---

## Repository Structure

```text
Immverse-ai/
└── sample-app/
    ├── Dockerfile
    ├── package.json
    ├── app.js
    └── README.md
```

---

## Technologies Used

* Git & GitHub
* Jenkins
* Docker
* Docker Hub
* Kubernetes
* Node.js

---

## Docker Image

Docker Hub Repository:

```text
anilbamnote/sample-app
```

Build Image:

```bash
docker build -t anilbamnote/sample-app:latest .
```

Run Container:

```bash
docker run -d -p 3000:3000 anilbamnote/sample-app:latest
```

---

## Jenkins Pipeline Stages

### 1. Checkout

Pull source code from GitHub repository.

### 2. Build

Build Docker image using Dockerfile.

```bash
docker build -t anilbamnote/sample-app:$BUILD_NUMBER .
```

### 3. Test

Verify image creation.

```bash
docker images
```

### 4. Push Image

Push Docker image to Docker Hub.

```bash
docker push anilbamnote/sample-app:$BUILD_NUMBER
docker push anilbamnote/sample-app:latest
```

### 5. Deploy

Deploy application using Docker/Kubernetes manifests.

---

## Kubernetes Deployment

### Deployment

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: sample-app
spec:
  replicas: 2
  selector:
    matchLabels:
      app: sample-app
  template:
    metadata:
      labels:
        app: sample-app
    spec:
      containers:
      - name: sample-app
        image: anilbamnote/sample-app:latest
        ports:
        - containerPort: 3000
```

### Service

```yaml
apiVersion: v1
kind: Service
metadata:
  name: sample-app-service
spec:
  selector:
    app: sample-app
  ports:
  - port: 80
    targetPort: 3000
  type: NodePort
```

Apply Resources:

```bash
kubectl apply -f deployment.yaml
kubectl apply -f service.yaml
```

Verify:

```bash
kubectl get pods
kubectl get deployments
kubectl get svc
```

---

## CI/CD Workflow

1. Developer pushes code to GitHub.
2. Jenkins automatically triggers the pipeline.
3. Docker image is built.
4. Image is pushed to Docker Hub.
5. Kubernetes deployment is updated.
6. Application becomes available through Kubernetes Service.

---

## Deliverables

* GitHub Repository
* Dockerfile
* Jenkinsfile
* Docker Hub Image
* Kubernetes Deployment & Service YAML

---

## Kubernetes Deployment Environment

For demonstration purposes, the application was deployed on a Kubernetes cluster provided by **Killercoda**.

### Deployment Details

* Platform: Killercoda Kubernetes Playground
* Deployment Type: Kubernetes Deployment
* Service Type: NodePort
* Replicas: 2
* Container Image: `anilbamnote/sample-app:latest`

### Application Access

The application is exposed using a **NodePort Service**, allowing external access to the application running inside the Kubernetes cluster.

Sample Access URL:

```text
https://a86571bf61ed-10-244-3-180-30181.spca.r.killercoda.com/
```

### Verification Commands

```bash
kubectl get deployments
kubectl get pods
kubectl get svc
kubectl describe svc sample-app-service
```

This deployment demonstrates container orchestration, service exposure using NodePort, and application accessibility through a Kubernetes-managed environment.

# Monitoring Setup with Prometheus, Node Exporter & Grafana

This document describes how to set up monitoring for the server using:

- Prometheus
- Node Exporter
- Grafana
- Docker

## Architecture

```text
+----------------+
| Node Exporter  |
| Port: 9100     |
+--------+-------+
         |
         v
+----------------+
| Prometheus     |
| Port: 9090     |
+--------+-------+
         |
         v
+----------------+
| Grafana        |
| Port: 3000     |
+----------------+
```

---

## Step 1: Run Node Exporter

Node Exporter collects system metrics such as:

- CPU Usage
- Memory Usage
- Disk Usage
- Network Usage

```bash
docker run -d \
--name node-exporter \
-p 9100:9100 \
--restart unless-stopped \
prom/node-exporter
```

Verify:

```bash
curl http://localhost:9100/metrics
```

---

## Step 2: Configure Prometheus

Create a file named `prometheus.yml`.

```yaml
global:
  scrape_interval: 15s

scrape_configs:
  - job_name: 'node-exporter'
    static_configs:
      - targets:
          - '172.31.50.172:9100'
```

> Replace the IP address with your server's private IP.

---

## Step 3: Run Prometheus

```bash
docker run -d \
--name prometheus \
-p 9090:9090 \
-v $(pwd)/prometheus.yml:/etc/prometheus/prometheus.yml \
prom/prometheus
```

Verify:

```bash
http://SERVER-IP:9090
```

Check targets:

```bash
http://SERVER-IP:9090/targets
```

Expected status:

```text
node-exporter    UP
```

---

## Step 4: Run Grafana

```bash
docker run -d \
--name grafana \
-p 3000:3000 \
grafana/grafana:latest
```

Access Grafana:

```text
http://SERVER-IP:3000
```

Default Credentials:

```text
Username: admin
Password: admin
```

---

## Step 5: Add Prometheus Data Source

Navigate to:

```text
Connections → Data Sources → Add Data Source
```

Select:

```text
Prometheus
```

URL:

```text
http://172.31.50.172:9090
```

Click:

```text
Save & Test
```

Expected Result:

```text
Data source is working
```

---

## Step 6: Import Node Exporter Dashboard

Navigate to:

```text
Dashboards → Import
```

Dashboard ID:

```text
1860
```

Select Prometheus as the datasource and click Import.

---

## Metrics Available

The dashboard provides:

- CPU Utilization
- Memory Utilization
- Disk Usage
- Filesystem Usage
- Network Traffic
- System Load
- Uptime

---

## Verify Metrics

Open Prometheus:

```text
http://SERVER-IP:9090
```

Run:

```promql
up
```

Expected:

```text
1
```

Check CPU Metrics:

```promql
node_cpu_seconds_total
```

Check Memory Metrics:

```promql
node_memory_MemTotal_bytes
```

---

## Running Containers

```bash
docker ps
```

Example:

```text
prometheus      -> Port 9090
node-exporter   -> Port 9100
grafana         -> Port 3000
sample-app      -> Port 80
```

---

## Useful Commands

### Restart Prometheus

```bash
docker restart prometheus
```

### Restart Grafana

```bash
docker restart grafana
```

### Restart Node Exporter

```bash
docker restart node-exporter
```

### View Prometheus Logs

```bash
docker logs prometheus
```

### View Grafana Logs

```bash
docker logs grafana
```

### View Node Exporter Logs

```bash
docker logs node-exporter
```

---

## Monitoring Stack

```text
Node Exporter
      │
      ▼
 Prometheus
      │
      ▼
   Grafana
```

This setup provides real-time monitoring and visualization of server health, resource utilization, and performance metrics.




## Author

Anil Bamnote

DevOps Engineer | AWS | Docker | Kubernetes | Jenkins

