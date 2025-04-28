#!/bin/bash
yum install git -y
yum install redis6 -y
systemctl enable redis6
systemctl start redis6
yum install docker -y
mkdir -p /usr/local/lib/docker/cli-plugins
curl -SL https://github.com/docker/compose/releases/download/v2.35.1/docker-compose-linux-x86_64   -o /usr/local/lib/docker/cli-plugins/docker-compose
chmod +x /usr/local/lib/docker/cli-plugins/docker-compose
git clone https://github.com/cs220s25/Anthony-Matt-Mael.git /Anthony-Matt-Mael
cd /Anthony-Matt-Mael
chmod +x redeploy.sh
docker compose up -d
