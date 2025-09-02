#!/bin/bash

# code verifier
CODE_VERIFIER=$(openssl rand -base64 48 | tr -d "=+/" | cut -c1-128)
echo "code_verifier: $CODE_VERIFIER"
# code challenge
CODE_CHALLENGE=$(echo -n "$CODE_VERIFIER" | openssl dgst -sha256 -binary | openssl base64 | tr '/+' '_-' | tr -d '=')
echo "code_challenge: $CODE_CHALLENGE"

# append to application.properties file so that it can be used in the next step
sed -i "s/^code_verifier=.*/code_verifier=$CODE_VERIFIER/" application.properties
sed -i "s/^code_challenge=.*/code_challenge=$CODE_CHALLENGE/" application.properties

