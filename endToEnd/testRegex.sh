#!/bin/bash

string="CliCustomer{balance=500.0, creditCard='5251896983', matriculation='', surveysToAnswer=[], favoritesStores=[], lastConnexion=null}"
regex=".*balance=500\.0.*"
if [[ "$string" =~ $regex ]]; then
  echo "Match"
else
  echo "No match"
fi
