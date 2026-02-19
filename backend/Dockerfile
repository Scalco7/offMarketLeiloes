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

# Instala dependências necessárias para o Playwright (navegadores)
# Caso a API utilize o scraper dentro do Docker, estas libs são essenciais
RUN apt-get update && apt-get install -y --no-install-recommends \
    libgbm1 \
    libnss3 \
    libatk1.0-0 \
    libatk-bridge2.0-0 \
    libcups2 \
    libdrm2 \
    libxkbcommon0 \
    libxcomposite1 \
    libxdamage1 \
    libxext6 \
    libxfixes3 \
    libxrandr2 \
    libasound2 \
    libpangocairo-1.0-0 \
    ca-certificates \
    fonts-liberation \
    libappindicator3-1 \
    libnspr4 \
    lsb-release \
    xdg-utils \
    && rm -rf /var/lib/apt/lists/*

# Copia o jar gerado no estágio de build
COPY --from=build /app/target/offMarketLeiloes-0.0.1-SNAPSHOT.jar app.jar

# Variáveis de ambiente padrão (podem ser sobrescritas no docker-compose ou runtime)
ENV SPRING_PROFILES_ACTIVE=prod
ENV SERVER_PORT=8080

# Porta exposta pela API
EXPOSE 8080

# Comando para iniciar a aplicação
# Nota: O Playwright baixará os navegadores na primeira execução se não estiverem presentes
ENTRYPOINT ["java", "-jar", "app.jar"]
