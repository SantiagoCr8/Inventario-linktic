# Inventario-linktic

## Tabla de contenidos
1. [Informacion General](#general-info)
2. [Tegnologias](#technologies)
3. [Instalacion](#installation)
### Informacion General
***
Este repositorio abarca la intalacion total del proyecto de microservicios de produto y invetario, ademas la doucmentacion del microservicio de Inventario

## Tegnologias
***
Lista de tegnologia utilzada para crear el microservicio de inventario
* [Java](https://example.com): Version 17
* [Spring Boot](https://example.com): Version 3.5.6
## Instalacion
***
Para que funcione correctamente nesecitamos que ya tenga descargado los microservicios de [Invetario-Lintick](https://github.com/SantiagoCr8/Inventario-linktic) y [Producto-Lintick](https://github.com/SantiagoCr8/Producto-Linktic) ademas colocarlos en una misma carpeta

Verifica que ya tengo instalado docker composer con el siguiente comando debe aparecer la verision
```
docker-compose --version
```

Crea un archivo docker-compose.yml afuera de los dos proyectos de invetario y producto despues agrega el siguiente contenido 
```
version: "3.9"

services:
  main-db:
    image: postgres:16-alpine
    container_name: main-db
    environment:
      POSTGRES_DB: ${MAIN_DB}
      POSTGRES_USER: ${DB_USER}
      POSTGRES_PASSWORD: ${DB_PASS}
    ports: ["5432:5432"]
    volumes:
      - main_data:/var/lib/postgresql/data
      - ./infra/main-db:/docker-entrypoint-initdb.d
    healthcheck:
      test: ["CMD-SHELL", "pg_isready -U ${DB_USER} -d ${MAIN_DB}"]
      interval: 5s
      timeout: 3s
      retries: 10

  producto-sevicio:
    build:
      context: ./producto
      dockerfile: Dockerfile
    container_name: producto-sevicio
    depends_on:
      main-db:
        condition: service_healthy
    environment:
      SPRING_DATASOURCE_URL: jdbc:postgresql://main-db:5432/${MAIN_DB}
      SPRING_DATASOURCE_USERNAME: ${DB_USER}
      SPRING_DATASOURCE_PASSWORD: ${DB_PASS}
      SERVER_PORT: 8080
      SPRING_JPA_HIBERNATE_DDL_AUTO: create
      # Recomendado: fijar search_path al esquema del servicio
      SPRING_JPA_PROPERTIES_HIBERNATE_DEFAULT_SCHEMA: orders
    ports: ["8080:8080"]
    restart: unless-stopped

  inventario-sevicio:
    build:
      context: ./inventario
      dockerfile: Dockerfile
    container_name: inventario-sevicio
    depends_on:
      main-db:
        condition: service_healthy
    environment:
      SPRING_DATASOURCE_URL: jdbc:postgresql://main-db:5432/${MAIN_DB}
      SPRING_DATASOURCE_USERNAME: ${DB_USER}
      SPRING_DATASOURCE_PASSWORD: ${DB_PASS}
      SERVER_PORT: 8081
      SPRING_JPA_HIBERNATE_DDL_AUTO: create
      SPRING_JPA_PROPERTIES_HIBERNATE_DEFAULT_SCHEMA: customers
    ports: ["8081:8081"]
    restart: unless-stopped

volumes:
  main_data:

```
Despues abre un simbolo del sistema dentro de la carpeta donde esta docker-compose.yml y ejecute el siguiente comando para que se instale 

```
docker compose up -d --build
```

## Test Aplicados a Invetario 
La pruebas realizadas a microservicio de invetario abarcaron un %84 del total de codigo.El test que hicieron contiene los siguiente
* Creación de productos.
* Gestión de inventario y proceso de compra.
* Comunicación entre microservicios.
* Manejo de errores (producto no encontrado, inventario insuficiente).
* Prueba de integración del microservicio.

<img width="970" height="297" alt="image" src="https://github.com/user-attachments/assets/c2b18d84-2e97-48f6-9524-3b6d3c2a349f" />
