resource "aws_ecr_repository" "user_service" {
  name = "user-service"
  force_delete = true
}

resource "aws_ecr_repository" "product_service" {
  name = "product-service"
  force_delete = true
}

resource "aws_ecr_repository" "order_service" {
  name = "order-service"
  force_delete = true
}

resource "aws_ecr_repository" "api_gateway" {
  name = "api-gateway"
  force_delete = true
}