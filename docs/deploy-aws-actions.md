GitHub Actions
│
├── Build user-service image
├── Push to ECR
│
├── Build product-service image
├── Push to ECR
│
├── Build order-service image
├── Push to ECR
│
├── Build api-gateway image
├── Push to ECR
│
├── Terraform Apply
│
└── SSH into EC2
│
├── docker compose down
├── docker system prune -af
├── ECR login
├── docker compose pull
└── docker compose up -d


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

This is the AWS recommended and production-grade
approach.
============================================================
