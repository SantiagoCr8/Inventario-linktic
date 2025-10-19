# ---------------------------------
# Etapa 1: compilación con Maven
# ---------------------------------
FROM maven:3.8-eclipse-temurin-17 AS builder
WORKDIR /app

# Copiamos pom y código fuente
COPY pom.xml .
COPY src ./src

# Compilamos sin tests (flag correcto es skipTests, con “s”)
RUN mvn clean package -DskipTests -Dmaven.test.skip=true

# ---------------------------------
# Etapa 2: empaquetado con JRE
# ---------------------------------
FROM eclipse-temurin:17-jdk AS runner
WORKDIR /app

# Argumento que apunta al JAR generado en /app/target
ARG JAR_FILE=target/*.jar

# Copiamos el .jar desde la etapa builder
COPY --from=builder /app/${JAR_FILE} app.jar


# Punto de entrada
ENTRYPOINT ["java","-jar","app.jar"]