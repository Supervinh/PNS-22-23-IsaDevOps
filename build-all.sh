#!/bin/bash

CREDS=admin:password
HOST="artifactory"

function build_dir() { # $1 is the dir to get it
  cd "$1" || exit
  ./build.sh
  cd ..
}

function build_dir_from_artifactory() { # $1 is the dir to get it
  ARTIFACT="$2"
  if [ -z "$2" ]; then
    ARTIFACT="$1"
  fi
  cd "$1" || exit
  getArtifact "$ARTIFACT"
  ./build.sh -a
  rm "$ARTIFACT".jar
  cd ..
}

function getLastVersion(){ # $1 should be credentials (user:password) and $2 should be the url of the artifact
  VERSION=$(curl -u "$1" "$2" | grep -E '<a href="[0-9].?[0-9]/">' | cut -d '"' -f 2 | sed 's|/$||')
  VERSION="$(echo "$VERSION" | tr ' ' '\n' | sort -r -V | head -n 1)"
  echo "$VERSION"
  return "$VERSION"
}

#admin:@yZvRLf3AG8BdS79w
function getArtifact() {
  URL="http://$HOST:8002/artifactory/libs-release-local/team-b/$1/"
  #find the latest stable version of the artifact
  #Retrieve the artifact
  VERSION=$(curl -u "$CREDS" "$URL" | grep -E '<a href="[0-9].?[0-9]/">' | cut -d '"' -f 2 | sed 's|/$||')
  VERSION="$(echo "$VERSION" | tr ' ' '\n' | sort -r -V | head -n 1)"
  echo "TAG: $1 ->$VERSION"
  curl -u "$CREDS" -L "$URL""$VERSION"/"$1"-"$VERSION".jar --output "$1".jar
}

DOCKER_DETACH=false           #if -d, run in detached mode
ATTACH_CLI=false              #if --attach, attach to the cli container
CLI_FROM_ARTIFACTORY=false    #if true, retrieve the cli from artifactory
SERVER_FROM_ARTIFACTORY=false #if true, retrieve the server from artifactory
DOCKER_COMPOSE=true           #if false, only build the images
next=false
for arg in "$@"; do
  if [ "$arg" = "--cli" ]; then
    CLI_FROM_ARTIFACTORY=true
  fi
  if [ "$arg" = "--server" ]; then
    SERVER_FROM_ARTIFACTORY=true
  fi
  if [ "$arg" = "-d" ]; then
    DOCKER_DETACH=true
  fi
  if [ "$arg" = "--none" ] || [ "$arg" = "-n" ]; then
    DOCKER_COMPOSE=false
  fi
  if [ "$arg" = "--attach" ] || [ "$arg" = "-a" ]; then
    ATTACH_CLI=true
    DOCKER_DETACH=true
    DOCKER_COMPOSE=true
  fi
  if [ "$arg" = "-l" ] || [ "$arg" = "--local" ]; then
    HOST="localhost"
  fi
  if [ "$next" = true ]; then
    next=false
    CREDS="$arg"
  fi
  if [ "$arg" = "-u" ] || [ "$arg" = "--user" ]; then
    next=true
  fi
done
echo "CLI_FROM_ARTIFACTORY: $CLI_FROM_ARTIFACTORY, SERVER_FROM_ARTIFACTORY: $SERVER_FROM_ARTIFACTORY, DOCKER_DETACH: $DOCKER_DETACH, ATTACH_CLI: $ATTACH_CLI, DOCKER_COMPOSE: $DOCKER_COMPOSE"
echo "** Building all"

echo "** Stopping"
docker compose down --rmi --remove-orphans

if [ "$CLI_FROM_ARTIFACTORY" = true ]; then
  build_dir_from_artifactory "cli"
else
  build_dir "cli"
fi

if [ "$SERVER_FROM_ARTIFACTORY" = true ]; then
  build_dir_from_artifactory "backend" "server"
else
  build_dir "backend"
fi

build_dir "bank"

build_dir "ISawWhereYouParkedLastSummer"

if [ "$DOCKER_COMPOSE" = true ]; then
  if [ "$DOCKER_DETACH" = true ]; then
    docker compose up -d
  else
    docker compose up
  fi
  if [ "$ATTACH_CLI" = true ]; then
    docker attach cli
  fi
fi
