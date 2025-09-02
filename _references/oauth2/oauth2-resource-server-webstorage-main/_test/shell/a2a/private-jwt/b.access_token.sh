#!/bin/bash

source ../../application.properties
source application-auth0.properties

CLIENT_ASSERTION=""
echo "token_url=${token_url} | client_id=${client_id} | audience=${audience} | client_assertion=${CLIENT_ASSERTION}"
curl -X POST "${token_url}" \
-H 'Content-Type: application/x-www-form-urlencoded' \
-d "grant_type=client_credentials" \
-d "client_id=${client_id}" \
-d "audience=${audience}" \
-d 'client_assertion_type=urn:ietf:params:oauth:client-assertion-type:jwt-bearer' \
-d "client_assertion=${CLIENT_ASSERTION}"  

