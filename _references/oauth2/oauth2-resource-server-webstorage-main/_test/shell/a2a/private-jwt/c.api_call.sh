#!/bin/bash

# NOTE: Please copy the 'access_token' from the response of the previous step.
ACCESS_TOKEN=""
source ../../application.properties

curl --request GET \
  --url "http://${api_url}/api/albums" \
  --header "Authorization: Bearer ${ACCESS_TOKEN}"



