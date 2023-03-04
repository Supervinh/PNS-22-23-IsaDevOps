screen -S smee -d -m smee --url https://smee.io/uTN3kiLqSU3wkZp --path /github-webhook/ --port 8000
#Build the agent
cd jenkins || exit
#Build the agent
./build.sh
cd ..
#Launch
./quick-start.sh