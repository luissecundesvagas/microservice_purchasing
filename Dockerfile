# Etapa 1: Configurar o Maven manualmente em uma imagem JDK 21
FROM eclipse-temurin:21-jdk AS builder

# Instalar Maven
RUN apt-get update && apt-get install -y maven && rm -rf /var/lib/apt/lists/*

# Diretório de trabalho
WORKDIR /app

# Copiar código do projeto
COPY . .

# Buildar o projeto
RUN mvn clean package -DskipTests

# Etapa 2: Runtime para execução
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]