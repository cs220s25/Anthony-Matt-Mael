#!/bin/bash
mvn clean package
sudo docker build -t discord-bot -f Docker/Dockerfile .
sudo docker stop my-discord-bot || true
sudo docker rm my-discord-bot || true
sudo docker run -d --name my-discord-bot discord-bot
sudo docker logs -f my-discord-bot
