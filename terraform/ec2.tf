resource "aws_instance" "ecommerce_server" {

  ami           = "ami-05cf1e9f73fbad2e2"
  instance_type = "t3.medium"

  key_name = "ecommerce-key"

  vpc_security_group_ids = [
    aws_security_group.ecommerce_sg.id
  ]

  user_data = <<-EOF
              #!/bin/bash
              apt update -y
              apt install docker.io docker-compose git awscli -y
              systemctl start docker
              systemctl enable docker
              usermod -aG docker ubuntu
              EOF

  tags = {
    Name = "ecommerce-server"
  }
  # attaching profile that includes role that has permission of
  iam_instance_profile =
      aws_iam_instance_profile.ec2_profile.name

}