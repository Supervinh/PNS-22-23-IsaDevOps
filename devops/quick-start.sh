#Clean start (just in case)
docker compose -f ./artifactory-oss-7.49.8/docker-compose.yaml -f ./jenkins/docker-compose.yml down
#One to rule them all
screen -S docker -d -m docker compose -f ./artifactory-oss-7.49.8/docker-compose.yaml -f ./jenkins/docker-compose.yml up