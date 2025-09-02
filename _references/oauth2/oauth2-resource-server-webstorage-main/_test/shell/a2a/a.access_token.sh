#!/bin/bash

source ../application.properties
echo "Please select the Authorization Server from the below options:"
echo "1) Auth0"
echo "2) AWS Cognito"
read -p "Enter your choice [1-2]: " choice
case $choice in
  1) 
    echo "loading application-auth0.properties files"
    source ../application-auth0.properties
    source application-auth0.properties
    ;;
  2) 
    echo "loading application-awscognito.properties files"
    source ../application-awscognito.properties
    source application-awscognito.properties
    ;;
  *)
    echo "Invalid selection."
    exit 1
    ;;
esac
# append to application.properties file so that it can be used in the next step
sed -i "s/^AUTH_SERVER_CHOICE=.*/AUTH_SERVER_CHOICE=$choice/" ../application.properties
echo ""

echo "token_url=${token_url} | client_id=${client_id} | client_secret=${client_secret} | audience=${audience}"
curl --request POST \
  --url "${token_url}" \
  --data-urlencode "grant_type=client_credentials" \
  --data-urlencode "client_id=${client_id}" \
  --data-urlencode "client_secret=${client_secret}" \
  --data-urlencode "audience=${audience}"

