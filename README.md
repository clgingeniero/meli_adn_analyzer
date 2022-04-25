# meli_adn_analyzer
Challenge Mercadolibre


## __Tecnologías Utilizadas__
- Java 11
- Spring Boot
- Maven
- AWS
- DynamoDB
- Elastic Beanstalk

## __Configuración del ambiente__
- En una cuenta de Amazon aws Crear una Tabla de DynamoDB llamada Adn y con un campo (Partition key) de tipo String llamado Dna
- Instalar aws cli y autenticarse
- clonar el reepositorio 

## __Ejecución de la aplicación__
- Una vez clonado el repositorio, entrar por consola al directorio y ejecutar mvn spring-boot:run
---

## __API URLs__
# Mutants 
## Verifica si un humano es mutante
### Tipo: POST
#### http://localhost:5000/mutants
#### http://meliadnanalyzer-env.eba-ipuvfg9c.us-east-1.elasticbeanstalk.com/mutants/

#### Body Mutante
```
{
    "dna": [
        "ATGCGA",
        "TAGTGC",
        "TTATGT",
        "AGAAGG",
        "CCCCTA",
        "TCACTG"
    ]
}
```
#### Response
##### StatusCode 200
``` 
{
    "data": {
        "is_mutant": true
    },
    "status": {
        "code": 200,
        "description": "OK"
    }
} 
```

#### Body Humano
```
{
    "dna": [
        "GTGCGA",
        "TAGTGC",
        "TTATGT",
        "AGAGGG",
        "GCCCTA",
        "TCACTG"
    ]
}
```
#### Response
##### StatusCode 403
``` 
{
    "data": {
        "is_mutant": false
    },
    "status": {
        "code": 403,
        "description": "Forbidden"
    }
}
```

#### Body No Valido
```
{
    "dna": [
        "UTGCGA",
        "TAGTGC",
        "TTATGT",
        "AGAGGG",
        "GCCCTA",
        "TCACTG"
    ]
}
```
#### Response
##### StatusCode 400
``` 
{
    "status": {
        "code": 400,
        "description": "Bad Request"
    }
}
```

# Stats
## Retorna las estadísticas de los humanos verificados, dando cantidades y el ratio de mutantes encontrados
### Tipo: GET
#### http://localhost:5000/stats/
#### http://meliadnanalyzer-env.eba-ipuvfg9c.us-east-1.elasticbeanstalk.com/stats/

#### Response
``` 
{
    "data": {
        "count_mutant_dna": 4,
        "count_human_dna": 3,
        "ratio": 1.0
    }
}