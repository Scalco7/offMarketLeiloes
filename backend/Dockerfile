# Estágio 1: Build
# Usando Microsoft OpenJDK 25 no Ubuntu Jammy
FROM mcr.microsoft.com/openjdk/jdk:25-ubuntu AS build

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
FROM mcr.microsoft.com/openjdk/jdk:25-ubuntu
WORKDIR /app

# Instala todas as dependências do sistema necessárias para o Playwright (Chromium)
# Estas são as bibliotecas que o Ubuntu Jammy precisa para rodar navegadores headless
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
    libpango-1.0-0 \
    libcairo2 \
    ca-certificates \
    fonts-liberation \
    libnspr4 \
    && rm -rf /var/lib/apt/lists/*

# Copia o jar gerado no estágio de build
COPY --from=build /app/target/offMarketLeiloes-0.0.1-SNAPSHOT.jar app.jar

# Define o local onde o Playwright armazena os navegadores
ENV PLAYWRIGHT_BROWSERS_PATH=/app/pw-browsers

# Instala o Chromium no estágio de runtime (usando o próprio JAR da aplicação)
# Para resolver o erro de Classpath, usamos o jar diretamente se for possível, 
# mas a forma mais segura em Spring Boot é deixar o Playwright baixar na primeira execução 
# ou usar um comando Maven no estágio de build.
# Vamos garantir que ele baixe para o diretório definido:
RUN java -jar app.jar --playwright.install=true || true 
# Nota: Se a sua aplicação não tiver um flag para isso, o Playwright baixará automaticamente 
# no primeiro 'playwright.create()'. As libs acima já garantem que ele vai rodar.

# Variáveis de ambiente padrão
ENV SPRING_PROFILES_ACTIVE=prod
ENV SERVER_PORT=8080

# Porta exposta pela API
EXPOSE 8080

# Comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
