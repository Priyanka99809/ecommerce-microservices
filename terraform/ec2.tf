resource "aws_instance" "ecommerce_server" {

  ami           = "ami-0f58b397bc5c1f2e8"
  instance_type = "t2.micro"

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