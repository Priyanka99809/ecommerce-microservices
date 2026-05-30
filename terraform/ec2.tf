resource "aws_instance" "ecommerce_server" {

  ami           = "ami-05cf1e9f73fbad2e2"
  instance_type = "t3.medium"

  key_name = "ecommerce-key"

  vpc_security_group_ids = [
    aws_security_group.ecommerce_sg.id
  ]

  user_data = <<-EOF
              user_data = <<-EOF
              #!/bin/bash -xe

              apt update -y

              apt install -y docker.io git unzip

              systemctl enable docker
              systemctl start docker
              systemctl restart docker

              usermod -aG docker ubuntu || true

              # Docker Compose plugin
              apt install -y docker-compose-plugin

              # AWS CLI install
              curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o awscliv2.zip
              unzip awscliv2.zip
              sudo ./aws/install

              rm -rf awscliv2.zip aws
              EOF

  tags = {
    Name = "ecommerce-server"
  }
  # attaching profile that includes role that has permission of
  iam_instance_profile = aws_iam_instance_profile.ec2_profile.name

}