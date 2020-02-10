# Java -  AWS lambda - API Gateway 

Project template to build an API Gateway API with Lambda Integration

## Building and deploying to AWS
```
mvn clean install
sls deploy
```

## Endpoints
```
 GET - https://${url_api_gateway}/dev/get-item/{id}
```

Example :
```
 curl  https://${url_api_gateway}/dev/get-item/3
```
