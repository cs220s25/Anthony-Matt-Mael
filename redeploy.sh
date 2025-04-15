sudo git pull origin main
sudo systemctl restart redis6
mvn clean package
java -jar target/dbot-1.0-SNAPSHOT.jar
