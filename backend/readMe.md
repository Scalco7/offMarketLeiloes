src/main/java/com/projeto/
├── domain/
│ ├── entities/ <-- Entidades JPA (@Entity) ricas em lógica
│ └── repositories/ <-- Interfaces que estendem JpaRepository
│
├── application/
│ ├── features/
│ │ └── products/
│ │ ├── commands/ <-- Usa Spring Data JPA para salvar
│ │ │ ├── CreateProductHandler.java
│ │ │ └── CreateProductCommand.java
│ │ └── queries/ <-- Pode usar JDBC ou Projeções DTO
│ │ ├── GetProductHandler.java
│ │ └── ProductDTO.java
│
├── infrastructure/  
│ └── persistence/ <-- Configurações de DB, Migrations (Flyway/Liquibase)
│
└── web/
└── controllers/ <-- Exposição dos Endpoints
