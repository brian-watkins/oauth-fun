#!/bin/sh

uaac client add funClient \
   --name funClient \
   --scope openid \
   -s funSecret \
   --authorized_grant_types authorization_code,refresh_token,client_credentials,password \
   --authorities fun-resource.read,fun-resource.write,uaa.resource \
   --redirect_uri http://localhost:9091/**

uaac client add superClient \
   --name superClient \
   --scope openid \
   -s superSecret \
   --authorized_grant_types authorization_code,refresh_token,client_credentials,password \
   --authorities super-resource.read,super-resource.write,uaa.resource \
   --redirect_uri http://localhost:9091/**

uaac client add funResource \
   --name funResource \
   --scope resource.read,resource.write,openid \
   -s secretResource \
   --authorized_grant_types authorization_code,refresh_token,client_credentials,password \
   --authorities uaa.resource \
   --redirect_uri http://localhost:8888/**

uaac user add bwatkins -p password --emails brian.watkins@gmail.com

# Add two scopes for fun-resource and super-resource
uaac group add fun-resource.read
uaac group add fun-resource.write

uaac group add super-resource.read
uaac group add super-resource.write

# Assign scopes..
# uaac member add fun-resource.read bwatkins
# uaac member add fun-resource.write bwatkins

