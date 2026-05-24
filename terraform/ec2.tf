resource "aws_instance" "ecommerce_server" {

  ami           = "ami-05cf1e9f73fbad2e2"
  instance_type = "t2.medium"

  key_name = "ecommerce-key"

  vpc_security_group_ids = [
    aws_security_group.ecommerce_sg.id
  ]

  user_data = <<-EOF
              #!/bin/bash
              apt update -y
              apt install docker.io docker-compose git -y
              systemctl start docker
              systemctl enable docker
              usermod -aG docker ubuntu
              EOF

  tags = {
    Name = "ecommerce-server"
  }
}