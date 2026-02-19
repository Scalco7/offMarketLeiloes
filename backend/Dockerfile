# Estágio 1: Build
# Usando Eclipse Temurin 25 JDK
FROM eclipse-temurin:25-jdk-jammy AS build

# Instala Maven
RUN apt-get update && apt-get install -y maven && rm -rf /var/lib/apt/lists/*

WORKDIR /app

# Copia apenas o pom.xml primeiro para aproveitar o cache das dependências
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia o código fonte e realiza o build
COPY src ./src
RUN mvn clean package -DskipTests

# Estágio 2: Runtime
FROM eclipse-temurin:25-jre-jammy
WORKDIR /app

# Instala curl e dependências básicas de gerenciamento de pacotes
RUN apt-get update && apt-get install -y --no-install-recommends \
    curl \
    ca-certificates \
    && rm -rf /var/lib/apt/lists/*

# Copia o jar gerado no estágio de build
COPY --from=build /app/target/offMarketLeiloes-0.0.1-SNAPSHOT.jar app.jar

# Instala dependências do sistema necessárias para o Playwright e baixa o Chromium
# Isso garante que a imagem já contenha tudo o que é necessário para rodar o scraper
RUN java -cp app.jar com.microsoft.playwright.CLI install-deps \
    && java -cp app.jar com.microsoft.playwright.CLI install chromium \
    && apt-get clean && rm -rf /var/lib/apt/lists/*

# Variáveis de ambiente padrão
ENV SPRING_PROFILES_ACTIVE=prod
ENV SERVER_PORT=8080

# Porta exposta pela API
EXPOSE 8080

# Comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
