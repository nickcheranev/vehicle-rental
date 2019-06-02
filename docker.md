docker login

mvn dockerfile:push

docker push ncheranev/vehicle-rental:0.0.1-SNAPSHOT

docker run -p 8080:8080 -t ncheranev/vehicle-rental:0.0.1-SNAPSHOT