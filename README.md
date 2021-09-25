
### Run tests and coverage
```
mvn test
```
Coverage report at <workspace>/open-market/target/site/jacoco/index.html


### Setup the database
```
docker-compose -f util/dependencies-docker-compose.yml up
```

### Shutdown the database
```
docker-compose -f util/dependencies-docker-compose.yml down --volumes
```

### Run the application
```
mvn spring-boot:run
```

### Documentation

http://localhost:8095/swagger-ui.html

