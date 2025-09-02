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


echo "Please login to the Auth0 server to get the authorization code. Manually copy the code and save it for the next step."
AUTH_URL="$(echo ${auth_url}\
'response_type=code&'\
'client_id='${client_id}'&'\
'redirect_uri='${callback_uri}'&'\
'audience='${audience}'&'\
'scope='${scope}'&'\
'code_challenge='${code_challenge}'&'\
'code_challenge_method=S256')"

echo "AUTH_URL: ${AUTH_URL}"
xdg-open "${AUTH_URL}" &
