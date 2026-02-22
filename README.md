# Off Market Leilões 🏠🔨

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Nuxt.js](https://img.shields.io/badge/Nuxt.js-00DC82?style=for-the-badge&logo=nuxtdotjs&logoColor=white)
![Vue.js](https://img.shields.io/badge/vuejs-%2335495e.svg?style=for-the-badge&logo=vuedotjs&logoColor=%234FC08D)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-4169E1?style=for-the-badge&logo=postgresql&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)
![Playwright](https://img.shields.io/badge/Playwright-2EAD33?style=for-the-badge&logo=playwright&logoColor=white)
![Railway](https://img.shields.io/badge/Railway-131415?style=for-the-badge&logo=railway&logoColor=white)

Este é um sistema para conectar usuários com leilões menos conhecidos. O projeto utiliza técnicas de **Web Scraping** com Playwright para extrair dados de portais de leilões, oferecendo uma interface moderna para busca, filtragem e gerenciamento de imóveis favoritos, além de notificações automatizadas por e-mail.

<br>

## 🏗️ Estrutura do Projeto

O projeto é dividido em camadas bem definidas.

- **Backend (Spring Boot)**: API REST desenvolvida em Java 25, utilizando uma arquitetura baseada no **CQRS** para separação de comandos e consultas. É responsável pelo processamento pesado, orquestração dos scrapers, persistência em banco de dados e serviços como o envio de e-mails. Utiliza PostgreSQL para armazenamento e Playwright para a raspagem de dados.

- **Frontend (Vue 3)**: Aplicação web reativa construída com Nuxt 4, Vuetify e TypeScript, seguindo a metodologia **Atomic Design** para organização de componentes.

- **Infraestrutura**: Configurações de Docker.

<br>

## 🚀 Principais Tecnologias

- **Backend**: Java 25, Spring Boot 4.x, Spring Security (JWT), Spring Data JPA, Hibernate, Playwright for Java, Spring Mail.
- **Frontend**: Nuxt 4, Vue 3, Vuetify 3 (Material Design), Pinia (State Management), Lucide Icons.
- **Banco de Dados**: PostgreSQL 15.
- **DevOps**: Docker & Docker Compose, Railway (Deployment).

<br>

## 📋 Pré-requisitos

- **Docker** instalado.
- **Docker Compose** instalado.
- (Opcional) **Maven** e **Node.js** instalados para rodar localmente sem Docker.

<br>

## ⚙️ Configuração

Para que o sistema funcione corretamente, configure as variáveis de ambiente em ambos os módulos.

### 🔑 Backend (`/backend/.env`)

Crie o arquivo configurado a partir do exemplo:

```bash
cp backend/.env.example backend/.env
```

| Variável              | Descrição                                        | Exemplo                          |
| :-------------------- | :----------------------------------------------- | :------------------------------- |
| `MAIL_USERNAME`       | E-mail utilizado para envio de notificações      | `seu-email@gmail.com`            |
| `MAIL_PASSWORD`       | Senha de App do Google (ou do seu provedor SMTP) | `**** **** **** ****`            |
| `JWT_SECRET`          | Chave secreta para geração dos tokens JWT        | `uma-chave-longa-e-segura`       |
| `DATASOURCE_URL`      | URL de conexão com o Banco de Dados (PostgreSQL) | `jdbc:postgresql://host:port/df` |
| `DATASOURCE_USERNAME` | Nome de usuário do Banco de Dados                | `postgres`                       |
| `DATASOURCE_PASSWORD` | Senha do Banco de Dados                          | `********`                       |

> **Nota:** Rodando via **Docker**, as configurações de banco em `docker-compose.yaml` já são suficientes para o uso local.

### 🌐 Frontend (`/frontend/web/.env`)

```bash
cp frontend/web/.env.example frontend/web/.env
```

| Variável            | Descrição  | Exemplo                     |
| :------------------ | :--------- | :-------------------------- |
| `VITE_API_BASE_URL` | URL da API | `http://localhost:8080/api` |

<br>

## 🚀 Instalação e Execução

### 🐳 Via Docker Compose (Recomendado)

1. **Clone e entre na pasta:**

   ```bash
   git clone https://github.com/Scalco7/offMarketLeiloes.git
   cd offMarketLeiloes
   ```

2. **Prepare os arquivos `.env`:**

   ```bash
   cp backend/.env.example backend/.env
   cp frontend/web/.env.example frontend/web/.env
   ```

3. **Inicie o sistema:**

   ```bash
   docker compose up --build
   ```

4. **Acesse:**
   - **Frontend:** [http://localhost:3000](http://localhost:3000)
   - **API Docs:** [http://localhost:8080/api/swagger-ui.html](http://localhost:8080/api/swagger-ui.html)

<br>

## 🕷️ Guia de Scraping

O projeto possui um motor de scraping para coletar dados de portais de leilões.

### Como funciona?

O backend utiliza o `ScraperOrchestrator` para gerenciar jobs de raspagem. Quando finalizado, um evento é disparado para notificar os usuários interessados por e-mail.

### Utilizando os Endpoints:

Você pode interagir com o scraper via Swagger ou ferramentas como Postman:

1. **Iniciar Scraping (`POST /api/scraper/start`)**:
   Inicia a varredura nos sites configurados (ex: Klockner Leilões).

   ```json
   {
     "scraper": "KLOCKNER_LEILOES"
   }
   ```

   _Retorna um `jobId` para acompanhamento._

2. **Consultar Status (`GET /api/scraper/{id}/status`)**:
   Verifica se o scraper ainda está rodando, se falhou ou se concluiu com sucesso.

<br>

## 🧪 Testes Automatizados

### Backend (JUnit 5)

Para rodar os testes de unidade e integração:

```bash
cd backend
mvn test
```

<br>

## 📜 Notas de Desenvolvimento

- **Responsividade**: O frontend foi otimizado para dispositivos móveis, com menus laterais inteligentes e grids adaptativos.
- **Segurança**: Autenticação via JWT com rotas protegidas e criptografia de senhas.
- **Notificações**: Integração total com SMTP para avisar quando novos imóveis são encontrados pela varredura.
- **Scraping**: O backend utiliza o Playwright para coletar imóveis de portais de leilões, através da rapagem de dados.

<br>

## 👷 Created By

**Felipe Maciel Scalco**
