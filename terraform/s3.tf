terraform {
  backend "s3" {
    bucket         = "ecommerce-terraform-state-703227779312-us-east-1"
    key            = "ecommerce/terraform.tfstate"
    region         = "us-east-1"
    dynamodb_table = "terraform-locks"
    encrypt        = true
  }
}