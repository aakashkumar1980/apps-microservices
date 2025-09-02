#!/bin/bash

source ../application.properties
source ../application-auth0.properties
source application-auth0.properties
# NOTE: Please copy the 'device_code' from the previous step.
DEVICE_CODE=""
echo "token_url=${token_url} | client_id=${client_id} | device_code=${DEVICE_CODE}"
curl --request POST \
  --url "${token_url}" \
  --header 'content-type: application/x-www-form-urlencoded' \
  --data-urlencode "grant_type=urn:ietf:params:oauth:grant-type:device_code" \
  --data-urlencode "client_id=${client_id}" \
  --data-urlencode "device_code=${DEVICE_CODE}"

