#!/bin/bash

function build_dir()  # $1 is the dir to get it
{
    cd "$1" || exit
    ./build.sh
    cd ..
}

echo "** Building all"

docker compose down

build_dir "backend"

build_dir "cli"

build_dir "bank"

build_dir "ISawWhereYouParkedLastSummer"

echo "** Done all -> docker up"

docker compose up