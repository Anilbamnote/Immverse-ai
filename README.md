# Immverse-ai
DevOps Assignment
 

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

Developer
↓
GitHub Repository
↓
Jenkins Pipeline
↓
Docker Image Build
↓
Docker Hub Push
↓
Kubernetes Deployment

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

Deploy application using Kubernetes manifests.

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

## Author

Anil Bamnote

DevOps Engineer | AWS | Docker | Kubernetes | Jenkins

=======
Webhook Trigger Added
 4c60127b2cd1ce9d1150be33c8793221303d4d4b
