resource "aws_instance" "ecommerce_server" {

  ami           = "ami-05cf1e9f73fbad2e2"
  instance_type = "t3.medium"

  key_name = "ecommerce-key"

  vpc_security_group_ids = [
    aws_security_group.ecommerce_sg.id
  ]

  user_data = <<-EOF
              #!/bin/bash -xe

              exec > /var/log/user-data.log 2>&1

              apt-get update -y
              apt-get install -y curl unzip git docker.io

              systemctl enable docker
              systemctl start docker

              usermod -aG docker ubuntu || true

              until docker info >/dev/null 2>&1; do
                echo "Waiting for Docker..."
                sleep 3
              done

              apt-get install -y docker-compose || true

              curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o awscliv2.zip
              unzip awscliv2.zip
              sudo ./aws/install
              rm -rf awscliv2.zip aws

              aws --version || /usr/local/bin/aws --version

              EOF

  tags = {
    Name = "ecommerce-server"
  }
  # attaching profile that includes role that has permission of
  iam_instance_profile = aws_iam_instance_profile.ec2_profile.name

}