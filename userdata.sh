#!/bin/bash
yum install git -y
yum install maven -y
yum install redis6 -y
yum install docker -y
cd /home/ec2-user
git clone https://github.com/cs220s25/Anthony-Matt-Mael.git 
cd Anthony-Matt-Mael
systemctl enable redis6
systemctl restart redis6
systemctl start redis6
mvn clean package
java -jar target/dbot-1.0-SNAPSHOT.jar 
