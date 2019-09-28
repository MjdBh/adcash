# Adcash Challenge
---

## Build

### Packaging as jar


To build the final jar and optimize the challenge application for production, run:

    ./mvnw  clean verify


To ensure everything worked, run:

    java -jar target/*.jar

Then navigate to [http://localhost:8080/api/public/products](http://localhost:8080/api/public/products) in your browser to see products.

Default database is h2 and in target folder created at running.

Default credentials for obtain access token  as follow:
    
    username: admin
    password: adcash
      
### Create Docker Image

To create Docker image, run:
 
    docker build .
         

## Testing

To launch your application's tests, run:

    ./mvnw verify

## API Call

Set bellow header for accepted response type as XML/Json

    Accept: application/json  
    Accept: application/xml  
    
Before call private api get authenticated with credential then set below header:

     authorization: Bearer TOKEN    
    
## Next release features
 
 *  Add logical delete of products 
 *  Add User management 
  


