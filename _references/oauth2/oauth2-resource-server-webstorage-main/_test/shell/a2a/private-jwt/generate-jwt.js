const jwt = require('jsonwebtoken');
const fs = require('fs');
const parser = require('properties-parser');
const auth0_properties = parser.read('application-auth0.properties');
const tokenUrl = auth0_properties.token_url.replace(/\$auth_domain/g, auth0_properties.auth_domain);
const { v4: uuidv4 } = require('uuid');
const now = Math.floor(Date.now() / 1000); 

const privateKey = fs.readFileSync('_certificates/private.key');
const token = jwt.sign({
  iss: `${auth0_properties.client_id}`,
  sub: `${auth0_properties.client_id}`,
  aud: `${tokenUrl}`,
  iat: now,
  exp: now + (5 * 60), 
  jti: uuidv4()
}, privateKey, { algorithm: 'RS256' });
console.log(token);



