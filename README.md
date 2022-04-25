# meli_adn_analyzer
Challenge Mercadolibre

![image](https://user-images.githubusercontent.com/1218391/165114364-daa70bac-0588-4dfe-a30a-5d6aa2df4ab9.png)

## __Necesidad__
Magneto quiere reclutar la mayor cantidad de mutantes para poder luchar contra los X-Men.

Te ha contratado a ti para que desarrolles un proyecto que detecte si un humano es mutante basándose en su secuencia de ADN.

Para eso te ha pedido crear un programa con un método o función con la siguiente firma (En alguno de los siguientes lenguajes: Java / Golang / C-C++ / Javascript (node) / Python / Ruby):

boolean isMutant(String[] dna); // Ejemplo Java

En donde recibirás como parámetro un array de Strings que representan cada fila de una tabla de (NxN) con la secuencia del ADN. Las letras de los Strings solo pueden ser: (A,T,C,G), las cuales representan cada base nitrogenada del ADN.

![image](https://user-images.githubusercontent.com/1218391/165114043-2ad06caf-6a6a-4703-9e8a-00e746acfa5d.png)

Sabrás si un humano es mutante, si encuentras más de una secuencia de cuatro letras iguales , de forma oblicua, horizontal o vertical.

Ejemplo (Caso mutante):

String[] dna = {"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"}; En este caso el llamado a la función isMutant(dna) devuelve “true”.


## __Solución implementada__

## __Tecnologías Utilizadas__
- Java 11
- Spring Boot
- Maven
- AWS
- DynamoDB
- Elastic Beanstalk

## __Implantación__

- Se utiliza Elastic Beanstalk para el despliegue de la aplicación en el servidor de amazon con capacidad de replicación elastica dependiendo de la carga impuesta, adicional válida la salud del servicio por el método health expuesto por actuator genrando alarma en caso de tener status diferentes al 2xx

## __Configuración del ambiente local__
- En una cuenta de Amazon aws Crear una Tabla de DynamoDB llamada Adn y con un campo (Partition key) de tipo String llamado Dna
- Si prefiere crear mediante el cli de aws puede ejecutar el siguiente comando
```
> aws dynamodb create-table --table-name Adn --attribute-definitions AttributeName=Dna,AttributeType=S --key-schema AttributeName=Dna=HASH --provisioned-throughput ReadCapacityUnits=5,WriteCapacityUnits=5 --table-class STANDARD
```
- Usar la región us-east-1 o cambiarla en el archivo de propiedades
- Instalar aws cli y autenticarse
- clonar el repositorio 

## __Ejecución de la aplicación__
- Una vez clonado el repositorio, entrar por consola al directorio y ejecutar mvn spring-boot:run o usar el Ide de su preferencia
- Para verificar los test puede usar mvn test
- Coverage actual

![image](https://user-images.githubusercontent.com/1218391/165122867-54191728-340c-43d2-8f0e-7fe85ada16f5.png)

## __Posibles errores__

- En caso de presentar error 5XX en la primera ejecución en ambiente local, valide que cuente con las credenciales correctas de aws y la taba creada de DynamoDB

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
    "count_mutant_dna": 4,
    "count_human_dna": 3,
    "ratio": 1.0
}