# maplinking - MapLink Developer Test

by <b>dvalvezon</b>

## Building & Executing

Go to `${projectRoot}` and enter the following commands:
```sh
$ mvn clean install ; java -jar endpoints/target/*.jar
```
The project will build. After the build finishes the application will start using an executable Jar (generated by spring-boot-maven-plugin).

It is also possible to start the application with `mvn spring-boot:run` within the "Endpoints" modules. Just don't forget to `mvn install` on project Root.

## Submitting Requests

### Using GET

Submit a request using the default costPerKm (0.25c)
```sh
$ curl -X GET -g http://localhost:8080/routes/[%7B%22address%22:%22Avenida%20Engenheiro%20Heitor%20Antonio%20Eiras%20Garcia%22,%22number%22:%222214%22,%22city%22:%22Sao%20Paulo%22,%22state%22:%22Sao%20Paulo%22%7D,%7B%22address%22:%22Avenida%20das%20Na%C3%A7%C3%B5es%20Unidas%22,%22number%22:%22100%22,%22city%22:%22S%C3%A3o%20Vicente%22,%22state%22:%22Sao%20Paulo%22%7D]
```

Submit a request using a custom costPerKm (0.40c)
```sh
$ curl -X GET -g http://localhost:8080/routes/[%7B%22address%22:%22Avenida%20Engenheiro%20Heitor%20Antonio%20Eiras%20Garcia%22,%22number%22:%222214%22,%22city%22:%22Sao%20Paulo%22,%22state%22:%22Sao%20Paulo%22%7D,%7B%22address%22:%22Avenida%20das%20Na%C3%A7%C3%B5es%20Unidas%22,%22number%22:%22100%22,%22city%22:%22S%C3%A3o%20Vicente%22,%22state%22:%22Sao%20Paulo%22%7D]?costPerKm=0.40
```

Or... Use your Browser Address Bar:
`http://localhost:8080/routes/[{"address":"Avenida Engenheiro Heitor Antonio Eiras Garcia","number":"2214","city":"Sao Paulo","state":"Sao Paulo"},{"address":"Avenida das Nações Unidas","number":"100","city":"São Vicente","state":"Sao Paulo"}]`

`http://localhost:8080/routes/[{"address":"Avenida Engenheiro Heitor Antonio Eiras Garcia","number":"2214","city":"Sao Paulo","state":"Sao Paulo"},{"address":"Avenida das Nações Unidas","number":"100","city":"São Vicente","state":"Sao Paulo"}]?costPerKm=0.40`

### Using POST+Json

Submit a request using the default costPerKm (0.25c)
```sh
$ curl -H "Content-Type: application/json" -X POST -d '[{"address":"Avenida Engenheiro Heitor Antonio Eiras Garcia","number":"2214","city":"Sao Paulo","state":"Sao Paulo"},{"address":"Avenida das Nações Unidas","number":"100","city":"São Vicente","state":"Sao Paulo"}]' http://localhost:8080/routes
```

Submit a request using a custom costPerKm (0.90c)
```sh
$ curl -H "Content-Type: application/json" -X POST -d '[{"address":"Avenida Engenheiro Heitor Antonio Eiras Garcia","number":"2214","city":"Sao Paulo","state":"Sao Paulo"},{"address":"Avenida das Nações Unidas","number":"100","city":"São Vicente","state":"Sao Paulo"}]' http://localhost:8080/routes?costPerKm=0.90
```
