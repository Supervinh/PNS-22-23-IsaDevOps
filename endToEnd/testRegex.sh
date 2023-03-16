#!/bin/bash

string="CliPurchase{id=d357655a-c6ab-4126-a0ff-884e178d9755, customerEmail='a@a', cost=25.0, storeName='abcdaire', internalAccount=false}"
regex="CliPurchase\{id=.*, customerEmail='a@a', cost=25.0, storeName='abcdaire', internalAccount=false\}"

if [[ "$string" =~ $regex ]]; then
  echo "Match"
else
  echo "No match"
fi
