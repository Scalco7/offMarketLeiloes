# Off Market Leilões - Backend (API) 🚀

![Java](https://img.shields.io/badge/Java-25-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.x-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-4169E1?style=for-the-badge&logo=postgresql&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=hibernate&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)

Este é o módulo de **Backend** do sistema Off Market Leilões. Uma API REST robusta construída com Spring Boot 3 no Java 25, utilizando princípios de Clean Architecture e o padrão CQRS para garantir escalabilidade e manutenção simplificada.

<br>

## 🏗️ Estrutura do Projeto (CQRS Pattern)

A API é organizada seguindo a separação de responsabilidades entre Comandos (Escrita) e Consultas (Leitura).

```text
src/main/java/com/backend/offMarketLeiloes/
├── domain/                      # Camada de Domínio (Coração do Sistema)
│   ├── entities/                # Entidades JPA ricas com regras de negócio
│   ├── enums/                   # Enumeradores do domínio
│   └── repositories/            # Interfaces de persistência
│
├── application/                 # Camada de Casos de Uso
│   ├── common/                  # DTOs genéricos, Exceções e Regras globais
│   └── features/                # Funcionalidades organizadas por domínio
│       └── [feature]/           # Ex: scraper, account, auth
│           ├── commands/        # Lógica de Escrita
│           └── queries/         # Lógica de Leitura (Projeções)
│
├── infrastructure/              # Configurações de Infraestrutura
│   ├── configuration/           # Beans de configuração Spring
│   └── security/                # Segurança, Filtros JWT e Autenticação
│
└── web/                         # Porta de Entrada (Controllers)
    ├── controllers/             # Endpoints REST (Delegam para App Layer)
    └── advice/                  # Tratamento global de exceções
```

<br>

## 📋 Pré-requisitos

Para rodar este módulo de forma isolada, você precisará de:

- **Java 25** ou superior.
- **Maven 3.9+**.
- **PostgreSQL 15+** rodando.

<br>

## ⚙️ Configuração (Variáveis de Ambiente)

A API utiliza variáveis de ambiente para configurações sensíveis. Crie ou utilize o arquivo `.env` na raiz da pasta `backend/`.

| Variável              | Descrição                    | Exemplo                                      |
| :-------------------- | :--------------------------- | :------------------------------------------- |
| `DATASOURCE_URL`      | URL do Postgres              | `jdbc:postgresql://localhost:5432/offmarket` |
| `DATASOURCE_USERNAME` | Usuário do Banco             | `postgres`                                   |
| `DATASOURCE_PASSWORD` | Senha do Banco               | `admin123`                                   |
| `JWT_SECRET`          | Chave secreta JWT            | `minha-chave-secreta-muito-segura`           |
| `MAIL_USERNAME`       | Usuário para envio de e-mail | `bot@gmail.com`                              |
| `MAIL_PASSWORD`       | Senha de App (SMTP)          | `abcd efgh ijkl mnop`                        |

<br>

## 🚀 Como Executar (Modo Isolado)

1. **Inicie o Banco de Dados:**
   Certifique-se de que o PostgreSQL está rodando e a database existe.

2. **Prepare as variáveis:**
   Garanta que as variáveis acima estejam exportadas no seu terminal ou configuradas via IDE.

3. **Rodar via Maven:**
   ```bash
   mvn spring-boot:run
   ```

A API estará disponível em `http://localhost:8080/api`.

<br>

## 📖 Documentação da API (Swagger)

A documentação interativa está disponível via OpenAPI UI:

🔗 **Swagger UI:** [http://localhost:8080/api/swagger-ui.html](http://localhost:8080/api/swagger-ui.html)

> As rotas de criação retornam `200 OK` e as falhas de validação retornam `422 Unprocessable Entity` detalhando os campos inválidos.

<br>

## 🧪 Testes

Os testes utilizam **JUnit 5**, **Mockito** e banco em memória **H2** (para garantir isolamento).

- **Rodar todos os testes:**

  ```bash
  mvn test
  ```

- **Rodar teste específico de Scraper:**
  ```bash
  mvn test -Dtest=KlocknerLeiloesScraperTest
  ```

<br>

## 🕷️ Leiloeiros Atualmente Suportados:

- [Klockner Leilões](https://www.kleiloes.com.br/)

<br>

---
