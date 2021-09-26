
## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Documentation](#documentation)
* [Some request examples](#some-request-examples)

## General info
The open-market API is a Rest Api designed to store open markets and filter then based in some criterias. 
It was created using the SpringBoot framework and use PostgreSQL to persist data.

## Technologies

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.5.4/maven-plugin/reference/html/)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.5.4/reference/htmlsingle/#boot-features-developing-web-applications)
* [Spring Data JDBC](https://docs.spring.io/spring-data/jdbc/docs/current/reference/html/)
* [PostgreSQL](https://www.postgresql.org/)

## Setup

### Run tests and coverage
```
mvn test
```
Coverage report at < workspace >/open-market/target/site/jacoco/index.html

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

### Run the script to load markets
```
mvn package
java -cp target/open-market-0.0.1-SNAPSHOT.jar -Dloader.main=com.unico.openmarket.scripts.ScriptLoadOpenMarkets org.springframework.boot.loader.PropertiesLauncher
```

## Documentation

http://localhost:8095/swagger-ui.html

## Some request examples

#### Create some markets
```
curl -X POST "http://localhost:8095/markets" -H "accept: */*" -H "Content-Type: application/json" -d "{\"code\":870,\"lng\":-46594143,\"lat\":-23518961,\"setcens\":355030889000121,\"areap\":3550308005033,\"name\":\"VILA ELZA\",\"registry\":\"6014-3\",\"street\":\"RUA ANDARAI\",\"number\":\"755.000000\",\"neighborhood\":\"VL MARIA\",\"reference\":\"RUA AMAMBAI\",\"district\":{\"code\":91,\"name\":\"VILA MARIA\"},\"subCityHall\":{\"code\":7,\"name\":\"VILA MARIA-VILA GUILHERME\"},\"region5\":\"Norte\",\"region8\":\"Norte 2\"}"
curl -X POST "http://localhost:8095/markets" -H "accept: */*" -H "Content-Type: application/json" -d "{\"code\":184,\"lng\":-46556465,\"lat\":-23532833,\"setcens\":355030880000059,\"areap\":3550308005036,\"name\":\"CHACARA MARANHAO\",\"registry\":\"7089-0\",\"street\":\"RUA ADELINO DE ALMEIDA CASTILHO\",\"number\":\"782.000000\",\"neighborhood\":\"TATUAPE\",\"reference\":\"MANOEL AVILA E ARIANA\",\"district\":{\"code\":82,\"name\":\"TATUAPE\"},\"subCityHall\":{\"code\":25,\"name\":\"MOOCA\"},\"region5\":\"Leste\",\"region8\":\"Leste 1\"}"
curl -X POST "http://localhost:8095/markets" -H "accept: */*" -H "Content-Type: application/json" -d "{\"code\":878,\"lng\":-46625252,\"lat\":-23592852,\"setcens\":355030890000053,\"areap\":3550308005046,\"name\":\"CHACARA KLABIM\",\"registry\":\"3145-3\",\"street\":\"RUA VOLTAIRE C/ PEDRO NICOLE\",\"number\":\"S/N\",\"neighborhood\":\"JD VL MARIANA\",\"reference\":\"AV PREF FABIO PRADO\",\"district\":{\"code\":92,\"name\":\"VILA MARIANA\"},\"subCityHall\":{\"code\":12,\"name\":\"VILA MARIANA\"},\"region5\":\"Sul\",\"region8\":\"Sul 1\"}"
```

#### Filter some markets by name and neighborhood
```
curl -X GET "http://localhost:8095/markets?name=CHACARA&neighborhood=VL" -H "accept: */*"

```
Response
```
[
  {
    "code": 432,
    "lng": -46395419,
    "lat": -23510354,
    "setcens": 355030836000098,
    "areap": 3550308005256,
    "name": "CHACARA FLORA",
    "registry": "6161-1",
    "street": "RUA FREI CANISIO",
    "number": "S/N",
    "neighborhood": "VL SOFIA",
    "reference": "",
    "district": {
      "code": 35,
      "name": "ITAIM PAULISTA"
    },
    "subCityHall": {
      "code": 24,
      "name": "ITAIM PAULISTA"
    },
    "region5": "Leste",
    "region8": "Leste 2"
  },
  {
    "code": 878,
    "lng": -46625252,
    "lat": -23592852,
    "setcens": 355030890000053,
    "areap": 3550308005046,
    "name": "CHACARA KLABIM",
    "registry": "3145-3",
    "street": "RUA VOLTAIRE C/ PEDRO NICOLE",
    "number": "S/N",
    "neighborhood": "JD VL MARIANA",
    "reference": "AV PREF FABIO PRADO",
    "district": {
      "code": 92,
      "name": "VILA MARIANA"
    },
    "subCityHall": {
      "code": 12,
      "name": "VILA MARIANA"
    },
    "region5": "Sul",
    "region8": "Sul 1"
  }
]
```

#### Filter some markets by name, neighborhood, district and region
```
curl -X GET "http://localhost:8095/markets?district=VILA%20MARIANA&region5=Sul&name=CHACARA&neighborhood=VL" -H "accept: */*"
```
Response
```
[
  {
    "code": 878,
    "lng": -46625252,
    "lat": -23592852,
    "setcens": 355030890000053,
    "areap": 3550308005046,
    "name": "CHACARA KLABIM",
    "registry": "3145-3",
    "street": "RUA VOLTAIRE C/ PEDRO NICOLE",
    "number": "S/N",
    "neighborhood": "JD VL MARIANA",
    "reference": "AV PREF FABIO PRADO",
    "district": {
      "code": 92,
      "name": "VILA MARIANA"
    },
    "subCityHall": {
      "code": 12,
      "name": "VILA MARIANA"
    },
    "region5": "Sul",
    "region8": "Sul 1"
  }
]
```

#### Get market by code
```
curl -X GET "http://localhost:8095/markets/184" -H "accept: */*"
```
Response
```
{
  "code": 184,
  "lng": -46556465,
  "lat": -23532833,
  "setcens": 355030880000059,
  "areap": 3550308005036,
  "name": "CHACARA MARANHAO",
  "registry": "7089-0",
  "street": "RUA ADELINO DE ALMEIDA CASTILHO",
  "number": "782.000000",
  "neighborhood": "TATUAPE",
  "reference": "MANOEL AVILA E ARIANA",
  "district": {
    "code": 82,
    "name": "TATUAPE"
  },
  "subCityHall": {
    "code": 25,
    "name": "MOOCA"
  },
  "region5": "Leste",
  "region8": "Leste 1"
}
```

#### Change market by code
```
curl -X PUT "http://localhost:8095/markets/184" -H "accept: */*" -H "Content-Type: application/json" -d "{\"code\":184,\"lng\":-46556465,\"lat\":-23532833,\"setcens\":355030880000059,\"areap\":3550308005036,\"name\":\"CHACARA MARANHAO CHANGED\",\"registry\":\"7089-0\",\"street\":\"RUA ADELINO DE ALMEIDA CASTILHO\",\"number\":\"782.000000\",\"neighborhood\":\"TATUAPE\",\"reference\":\"MANOEL AVILA E ARIANA\",\"district\":{\"code\":82,\"name\":\"TATUAPE\"},\"subCityHall\":{\"code\":25,\"name\":\"MOOCA\"},\"region5\":\"Leste\",\"region8\":\"Leste 1\"}"
```
Response
```
{
  "code": 184,
  "lng": -46556465,
  "lat": -23532833,
  "setcens": 355030880000059,
  "areap": 3550308005036,
  "name": "CHACARA MARANHAO CHANGED",
  "registry": "7089-0",
  "street": "RUA ADELINO DE ALMEIDA CASTILHO",
  "number": "782.000000",
  "neighborhood": "TATUAPE",
  "reference": "MANOEL AVILA E ARIANA",
  "district": {
    "code": 82,
    "name": "TATUAPE"
  },
  "subCityHall": {
    "code": 25,
    "name": "MOOCA"
  },
  "region5": "Leste",
  "region8": "Leste 1"
}
```

#### Delete market by code
```
curl -X DELETE "http://localhost:8095/markets/184" -H "accept: */*"
```