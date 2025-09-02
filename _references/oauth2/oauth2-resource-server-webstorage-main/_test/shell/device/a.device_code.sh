#!/bin/bash

source ../application.properties
source ../application-auth0.properties
source application-auth0.properties
echo "device_code_url=${device_code_url} | client_id=${client_id} | audience=${audience} | scope=${scope}"
curl --request POST \
  --url "${device_code_url}" \
  --header 'content-type: application/x-www-form-urlencoded' \
  --data-urlencode "client_id=${client_id}" \
  --data-urlencode "audience=${audience}" \
  --data-urlencode "scope=${scope}"

echo ""
echo "Please login to the 'verification_uri' and punch-in the 'user_code' and login if necessary. Manually copy the 'device_code' and save it for the next step."

