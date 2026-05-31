# 🚀 GitHub Actions CI/CD Pipeline (Ecommerce Microservices on AWS)

This document explains how the CI/CD pipeline works for the Ecommerce Microservices project deployed on AWS EC2 using Terraform, Docker, and GitHub Actions.

---

## 📌 Overview

The pipeline automates the entire workflow from infrastructure provisioning to application deployment:

1. Infrastructure provisioning using Terraform
2. Building Docker images for microservices
3. Pushing images to Amazon ECR
4. Deploying services to AWS EC2 via SSH
5. Verifying deployment status

---

## ⚙️ Workflow Trigger

The pipeline is triggered manually using:

```yaml
on:
  workflow_dispatch:
```

This allows controlled deployments when needed.

---

## 🧱 Step 1: Checkout Code

The repository is cloned into the GitHub Actions runner:

```bash
actions/checkout@v4
```

This provides access to Terraform and application code.

---

## 🔐 Step 2: Configure AWS Credentials

AWS credentials are configured securely using GitHub Secrets:

* AWS Access Key
* AWS Secret Key
* AWS Region

This enables Terraform and ECR operations.

---

## 🏗️ Step 3: Infrastructure Provisioning (Terraform)

Terraform is used to provision AWS resources:

### Resources created:

* EC2 instance (application server)
* Security groups (port access for services)
* IAM role for EC2 (ECR + AWS access)

### Commands executed:

```bash
terraform init -reconfigure
terraform plan
terraform apply -auto-approve
```

---

## 📦 Step 4: Build & Push Docker Images

Each microservice is containerized and pushed to Amazon ECR.

### Services:

* User Service
* Product Service
* Order Service
* API Gateway

### Flow:

```bash
docker build -t <ecr-repo>/service:latest .
docker push <ecr-repo>/service:latest
```

---

## 📡 Step 5: Fetch EC2 Public IP

Terraform output is used to retrieve the EC2 public IP:

```bash
EC2_IP=$(terraform output -raw public_ip)
```

This IP is used for SSH-based deployment.

---

## 🔑 Step 6: SSH Setup

A private SSH key stored in GitHub Secrets is used:

```bash
echo "${{ secrets.EC2_SSH_KEY }}" > private_key.pem
chmod 600 private_key.pem
```

---

## ⏳ Step 7: Wait for EC2 Readiness

The pipeline waits until EC2 becomes accessible via SSH:

```bash
until ssh ubuntu@$EC2_IP "echo ready"; do
  sleep 10
done
```

---

## 🚀 Step 8: Application Deployment on EC2

The deployment happens remotely over SSH:

### Actions performed:

* Install verification of Docker and AWS CLI
* Clone or update GitHub repository
* Pull latest code from `main`
* Stop existing containers
* Clean unused Docker resources
* Authenticate with Amazon ECR
* Pull latest Docker images
* Start services using Docker Compose

### Core command:

```bash
sudo docker compose -f docker-compose.yml up -d
```

---

## 🔍 Step 9: Verification

After deployment, the pipeline verifies:

* Running containers (`docker ps`)
* Container logs
* Disk usage
* Docker image usage

This ensures the system is healthy post-deployment.

---

## 🌐 Final Output

Once deployment completes successfully:

* All microservices are running on EC2
* API Gateway exposes port `8080`
* Services communicate internally via Docker network

Example:

```
http://<EC2_PUBLIC_IP>:8080
```

---

## 🧠 Key DevOps Concepts Used

* Infrastructure as Code (Terraform)
* CI/CD Automation (GitHub Actions)
* Containerization (Docker)
* Microservices Architecture
* Cloud Deployment (AWS EC2 + ECR)
* Remote provisioning via SSH

---

## ✅ Result

A fully automated pipeline that provisions infrastructure, builds services, and deploys a distributed microservices system to AWS with minimal m



IAM ROLE vs POLICY vs INSTANCE PROFILE
============================================================

POLICY
------
A policy defines WHAT permissions are granted.

Example:
AmazonEC2ContainerRegistryReadOnly

This policy allows:
- Login to ECR
- View ECR repositories
- Pull Docker images from ECR

It does NOT allow:
- Push images
- Delete repositories
- Modify repositories

Think:
Policy = Permission Set


ROLE
----
A role is an AWS identity that receives one or more policies.

Example:

      ecommerce-ec2-role
              |
              v
      AmazonEC2ContainerRegistryReadOnly

Think:
Role = User/Identity
Policy = Permissions assigned to that identity

The role answers the question:

      "What is this EC2 instance allowed to do?"


INSTANCE PROFILE
----------------
EC2 instances cannot directly attach IAM roles.

AWS requires an Instance Profile, which is simply
a wrapper/container around a role.

Relationship:

      Policy
         |
         v
       Role
         |
         v
Instance Profile
|
v
EC2

Example:

      AmazonEC2ContainerRegistryReadOnly
                       |
                       v
               ecommerce-ec2-role
                       |
                       v
            ecommerce-ec2-profile
                       |
                       v
                ecommerce_server

WHY WE NEED THIS
----------------
During deployment the EC2 instance must pull Docker
images from ECR.

AWS ECR requires authentication.

Instead of storing AWS Access Keys on the server,
we attach an IAM Role through an Instance Profile.

AWS automatically provides temporary credentials
to the EC2 instance.

Then commands such as:

      aws ecr get-login-password

work automatically without storing any secrets
on the machine.

