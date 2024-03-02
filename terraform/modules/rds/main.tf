resource "aws_security_group" "app-sg-rds" {
  name = "${var.application_name}-app-sg-rds"
  vpc_id = var.vpc_id

  ingress {
    from_port   = 5432
    to_port     = 5432
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }
}
resource "aws_db_instance" "example" {
  identifier            = "${var.application_name}-db"
  engine                = "postgres"
  instance_class        = "db.t3.micro"
  allocated_storage     = 10
  username              = "postgres"
  password              = "e3r4E#R$"
  publicly_accessible   = true
  skip_final_snapshot   = true
  db_subnet_group_name = aws_db_subnet_group.this.id
  vpc_security_group_ids = [aws_security_group.app-sg-rds.id]

  tags = {
    "Name" = "${var.application_name}-db"
  }
}
resource "aws_db_subnet_group" "this" {
  name = "${var.application_name}-db-subnet"
  subnet_ids = [var.subnet_c_id, var.subnet_a_id]
}
