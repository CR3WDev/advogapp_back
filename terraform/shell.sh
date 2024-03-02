scp -i "./terraform/modules/ec2/app-key-pair" ./target/advogapp-0.0.1-SNAPSHOT.jar ubuntu@ec2-52-14-153-209.us-east-2.compute.amazonaws.com:/home/ubuntu
ssh -i "./modules/ec2/app-key-pair" ubuntu@ec2-52-14-153-209.us-east-2.compute.amazonaws.com
sudo apt update
sudo apt install -y openjdk-17-jdk
java -jar advogapp-0.0.1-SNAPSHOT.jar