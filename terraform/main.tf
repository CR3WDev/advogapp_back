module "vpc" {
  source = "./modules/vpc"
  application_name = local.application_name
}
module "rds" {
  source = "./modules/rds"
  application_name = local.application_name
  subnet_a_id = module.vpc.subnet_a_id
  subnet_c_id = module.vpc.subnet_c_id
  vpc_id =  module.vpc.vpc_id
}

module "ec2" {
    source = "./modules/ec2"
    application_name = local.application_name
    subnet_id = module.vpc.subnet_b_id
    vpc_id = module.vpc.vpc_id
}