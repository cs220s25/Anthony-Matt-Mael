#!/bin/bash
mvn clean package
sudo docker build -t discord-bot .
sudo docker stop my-discord-bot
sudo docker rm my-discord-bot
sudo docker run -d --name my-discord-bot discord-bot
sudo docker logs -f my-discord-bot
