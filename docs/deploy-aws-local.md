````md
# E-Commerce Microservices Local Deployment Guide

## 1. Initialize Terraform

From your project root:

```bash
terraform init
````

---

## 2. Preview Infrastructure Changes

```bash
terraform plan
```

This shows what AWS resources will be created.

---

## 3. Create AWS Infrastructure

```bash
terraform apply
```

Type:

```bash
yes
```

Terraform will create:

* EC2 instance
* Security groups
* Networking resources

After completion, copy the EC2 public IP from the output.

---

## 4. SSH Into EC2 Instance

```bash
ssh -i your-key.pem ubuntu@YOUR_PUBLIC_IP
```

Example:

```bash
ssh -i ecommerce-key.pem ubuntu@54.xx.xx.xx
```

---

## 5. Verify Docker Installation

Check Docker version:

```bash
docker --version
```

Check Docker Compose:

```bash
docker compose version
```

---

## 6. Start Docker Service

Start Docker:

```bash
sudo systemctl start docker
```

Enable Docker on reboot:

```bash
sudo systemctl enable docker
```

Verify Docker is running:

```bash
sudo systemctl status docker
```

---

## 7. Clone Project Repository

```bash
git clone <YOUR_GITHUB_REPO_URL>
```

Go inside project:

```bash
cd ecommerce-microservices
```

---

## 8. Run All Microservices

Build and start containers:

```bash
sudo docker compose up --build
```

Run in detached mode:

```bash
sudo docker compose up --build -d
```

---

## 9. Check Running Containers

```bash
sudo docker ps
```

Expected containers:

* api-gateway
* user-service
* product-service
* order-service
* user-db
* product-db
* order-db

---

## 10. Check Application Logs

All logs:

```bash
sudo docker compose logs
```

Specific service logs:

```bash
sudo docker logs user-service
sudo docker logs product-service
sudo docker logs order-service
sudo docker logs api-gateway
```

---

# Sanity Testing URLs

Replace `<EC2_PUBLIC_IP>` with your actual public IP.

## Product Service Health

```text
http://<EC2_PUBLIC_IP>:8080/product/actuator/health
```

## User Service Health

```text
http://<EC2_PUBLIC_IP>:8080/user/actuator/health
```

---

## Add Product

Open in browser:

```text
http://<EC2_PUBLIC_IP>:8080/product/addProduct?name=Jeans&price=4000
```

---

## Get Products

```text
http://<EC2_PUBLIC_IP>:8080/product/getProducts
```

---

# Useful Docker Commands

## Stop containers

```bash
sudo docker compose down
```

## Restart containers

```bash
sudo docker compose restart
```

## Rebuild containers

```bash
sudo docker compose up --build -d
```

## Remove unused Docker data

```bash
sudo docker system prune -a
```

---

# Common Issues

## Containers Not Starting

Check logs:

```bash
sudo docker compose logs
```

---

## Database Connection Issues

Verify environment variables inside container:

```bash
sudo docker exec -it user-service env
```

---

## API Gateway Returning 404

Check:

* Routes configured correctly
* All services running
* Correct URL path used

```
```
