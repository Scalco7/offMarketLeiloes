# 📂 Estrutura do Projeto (CQRS Pattern)

Este projeto utiliza o padrão **CQRS (Command Query Responsibility Segregation)** para separar as operações de escrita (Commands) das operações de leitura (Queries).

## 🏗️ Organização de Pastas

```text
src/main/java/com/projeto/
├── domain/                      # Camada de Domínio (Regras de Negócio)
│   ├── entities/                # Entidades JPA (@Entity) ricas em lógica
│   └── repositories/            # Interfaces de contrato (Estendem JpaRepository)
│
├── application/                 # Camada de Aplicação (Casos de Uso)
│   ├── common/                  # Utilitários globais (Exceções, Paginação, Logs)
│   └── features/                # Módulos de funcionalidade (Exemplo: Auth, Catalogo)
│
├── infrastructure/              # Detalhes Técnicos e Externos
│   └── security/                # Configurações do Spring Security
│
└── web/                         # Porta de Entrada (Adapters)
    └── controllers/             # Endpoints REST (Thin Controllers)
```

## Leiloeiros suportados:

- [nakakogueleiloes.com.br](https://www.nakakogueleiloes.com.br/home/)
- [kleiloes.com.br](https://www.kleiloes.com.br/)

## Test Scrappers

```bash
mvn test -Dtest=KlocknerLeiloesScraperTest
```
