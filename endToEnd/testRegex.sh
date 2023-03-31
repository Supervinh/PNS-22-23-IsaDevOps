#!/bin/bash

string="SomeTest{id=46, matriculation=8465, internalAccount=false}"
regex="SomeTest\{id=.*, matriculation=[0-9]+, internalAccount=false\}"
if [[ "$string" =~ $regex ]]; then
  echo "Match"
else
  echo "No match"
fi
