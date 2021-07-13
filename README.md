# camunda-custom-rest-endpoint
This project shows how to add a custom endpoint to the CAMUNDA REST API. 

The project uses a multi-module maven project so the customization can be built as a separate
project / jar, but tested in a standard plain vanilla CAMUNDA Spring Boot project (engine module)

## How it works
- [CustomRestService](/custom-endpoint/src/main/java/org/camunda/example/api/CustomRestService.java) implements the custom endpoint. This class could be split into an interface including the JAX RS annotataions and an actual implementation class.
- [MyResourceConfigCustomizer](/custom-endpoint/src/main/java/org/camunda/example/api/config/MyResourceConfigCustomizer.java) extends the Jersey REST configuration bootstrapped by Camunda with the custom endpoint.

## Running it
Start the Spring Boot application (application class in engine module) or use    

`mvn clean install`  
then inside the engine folder   
`mvn spring-boot:run -f ./engine` 

Open http://localhost:8080/engine-rest/testprocess

Send a POST to http://localhost:8080/engine-rest/testprocess/start for instance using the [Postman Collection](/postman/postman_collection.json) 



# Resources
- https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.developing-web-applications.jersey
