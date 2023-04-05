# Description: Script to deploy the application on the server
# 1 -> creds for artifactory 2 -> host
cp -r cli/scripts deploy/
cd deploy || exit
zip -r ../launch.zip *
cd ..
curl -u "$1" -X PUT http://"$2":8002/artifactory/libs-release-local/launch.zip -T launch.zip