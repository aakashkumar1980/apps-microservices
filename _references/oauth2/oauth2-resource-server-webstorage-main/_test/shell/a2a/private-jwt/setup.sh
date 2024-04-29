#!/bin/bash

echo "Generating private key and public certificate..."
openssl genrsa -out _certificates/private.key 2048
openssl req -new -x509 -key _certificates/private.key -out _certificates/public.cert -days 360 -subj "/C=IN/ST=Uttrakhand/L=Dehradun/O=Aaditya Designers Pvt. Ltd./OU=IT/CN=Aaditya Designers Certificate"



