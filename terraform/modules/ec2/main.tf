resource "aws_key_pair" "this" {
  key_name = "${var.application_name}-key-pair"
  public_key = file("./modules/ec2/app-key-pair.pub")
}

resource "aws_security_group" "app-sg-ssh" {
  name = "${var.application_name}-app-sg-ssh"
  vpc_id = var.vpc_id

  ingress {
    cidr_blocks = ["0.0.0.0/0"]
    from_port = 22
    to_port = 22
    protocol = "tcp"
  }

  ingress {
    cidr_blocks = ["0.0.0.0/0"]
    from_port   = 8080
    to_port     = 8080
    protocol    = "tcp"
  }

  egress {
    cidr_blocks = ["0.0.0.0/0"]
    from_port = 0
    to_port = 0
    protocol = "-1"
  }
}
resource "aws_instance" "this" {
  ami           = "ami-0f5daaa3a7fb3378b"
  instance_type = "t2.micro"
  subnet_id     = var.subnet_id
  associate_public_ip_address = true
  key_name = aws_key_pair.this.key_name
  security_groups = [aws_security_group.app-sg-ssh.id]

  tags = {
    "Name" = "${var.application_name}-public-ec2"
  }
}
