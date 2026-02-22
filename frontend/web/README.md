# Off Market Leilões - Frontend (Web) 🌐

![Vue.js](https://img.shields.io/badge/Vue.js-3.x-4FC08D?style=for-the-badge&logo=vuedotjs&logoColor=white)
![Nuxt.js](https://img.shields.io/badge/Nuxt.js-4.x-00DC82?style=for-the-badge&logo=nuxtdotjs&logoColor=white)
![Vuetify](https://img.shields.io/badge/Vuetify-3.x-1867C0?style=for-the-badge&logo=vuetify&logoColor=white)
![TypeScript](https://img.shields.io/badge/TypeScript-5.x-3178C6?style=for-the-badge&logo=typescript&logoColor=white)
![Atomic Design](https://img.shields.io/badge/Design-Atomic-red?style=for-the-badge)

Este é o módulo de **Frontend** do sistema Off Market Leilões. Uma aplicação web construída com **Nuxt 4**.

<br>

## 🎨 Metodologia: Atomic Design

A organização dos componentes segue a metodologia **Atomic Design**, permitindo a criação de uma interface modular, escalável e de fácil manutenção.

- **Atoms**: Componentes básicos e indivisíveis (Ex: Botões, Inputs, Ícones).
- **Molecules**: Grupos de átomos que funcionam juntos (Ex: Campo de busca com botão, Item de lista).
- **Organisms**: Componentes complexos formados por moléculas e átomos (Ex: Navbar, Card de Imóvel, Grid de Filtros).
- **Templates**: Estruturas de página que organizam os organismos (Ex: Layout da Dashboard, Esqueleto do Catálogo).
- **Pages**: Instâncias reais dos templates com dados injetados.

```text
app/components/
├── atoms/        # Menores unidades
├── molecules/    # Uniões simples
├── organisms/    # Seções complexas
└── templates/    # Estruturas de layout
```

<br>

## 🏗️ Arquitetura de API: CQRS Pattern

Assim como no backend, aplicamos o padrão **CQRS** na camada de integração para manter o frontend organizado e previsível.

- **Commands**: Operações que alteram estado (`POST`, `PUT`, `DELETE`).
- **Queries**: Operações de busca de dados (`GET`).

```text
app/api/
├── client.ts             # Instância Axios com Interceptors (Auth + Refresh)
└── modules/              # Módulos separados por domínio
    └── [modulo]/
        ├── commands/     # Ex: login, addFavorite
        └── queries/      # Ex: listProperties, getStatus
```

<br>

## 📋 Pré-requisitos

Para rodar este módulo de forma isolada:

- **Node.js 20+** (Recomenda-se a versão LTS mais recente).
- **NPM** ou **Yarn**.
- **Backend rodando** (ou acesso a um ambiente de API).

<br>

## ⚙️ Configuração (Variáveis de Ambiente)

Crie um arquivo `.env` na pasta `frontend/web/` baseando-se no `.env.example`:

| Variável            | Descrição                   | Exemplo                     |
| :------------------ | :-------------------------- | :-------------------------- |
| `VITE_API_BASE_URL` | URL base da API Spring Boot | `http://localhost:8080/api` |

<br>

## 🚀 Como Executar (Modo Isolado)

1. **Instale as dependências:**

   ```bash
   npm install
   ```

2. **Inicie o servidor de desenvolvimento:**
   ```bash
   npm run dev
   ```

A aplicação estará disponível em `http://localhost:3000`.

<br>

## 🛠️ Scripts Disponíveis

- `npm run dev`: Inicia o Nuxt em modo desenvolvimento.
- `npm run build`: Compila a aplicação para produção.
- `npm run generate`: Gera o site estático (SSG).
- `npm run preview`: Previsualiza o build de produção localmente.

<br>

---
