# Nuxt Minimal Starter

Look at the [Nuxt documentation](https://nuxt.com/docs/getting-started/introduction) to learn more.

## Setup

Make sure to install dependencies:

```bash
# npm
npm install

# pnpm
pnpm install

# yarn
yarn install

# bun
bun install
```

## Development Server

Start the development server on `http://localhost:3000`:

```bash
# npm
npm run dev

# pnpm
pnpm dev

# yarn
yarn dev

# bun
bun run dev
```

## Production

Build the application for production:

```bash
# npm
npm run build

# pnpm
pnpm build

# yarn
yarn build

# bun
bun run build
```

Locally preview production build:

```bash
# npm
npm run preview

# pnpm
pnpm preview

# yarn
yarn preview

# bun
bun run preview
```

Check out the [deployment documentation](https://nuxt.com/docs/getting-started/deployment) for more information.

# Arquitetura de API CQRS (Nuxt 4)

Esta arquitetura segue o padrão **CQRS (Command Query Responsibility Segregation)**, separando claramente as operações que alteram estado (**Commands**) das que buscam dados (**Queries**).

## Estrutura de Pastas

```text
app/api/
├── client.ts             # Instância Axios com Interceptors (Auth + Refresh)
├── index.ts              # Registro central (Agrupa auth, property, etc.)
└── modules/
    └── [modulo]/         # Ex: auth, property
        ├── index.ts      # Exporta Commands e Queries do módulo
        ├── [modulo].interface.ts # Interfaces compartilhadas do módulo
        ├── commands/     # Operações de alteração (POST, PUT, DELETE)
        │   └── login.command.ts
        └── queries/      # Operações de leitura (GET)
            └── list-properties.query.ts
```

## Como Usar

Cada Command ou Query é uma função independente com suas próprias interfaces de Request e Response.

### Injeção Global (v-app)

```vue
<script setup lang="ts">
const { $api } = useNuxtApp();

// Exemplo de uma Query
const loadProperties = async () => {
  const { data } = await $api.property.queries.list({ page: 1 });
};

// Exemplo de um Command
const handleLogin = async (creds) => {
  const { data } = await $api.auth.commands.login(creds);
};
</script>
```

## Benefícios

1.  **Escalabilidade**: Cada operação é isolada. Mudar um Command não afeta nada além dele.
2.  **Tipagem Forte**: Cada arquivo define exatamente o que entra e o que sai daquela operação.
3.  **Manutenibilidade**: Segue o padrão do Backend, facilitando a comunicação entre times.

## Padronização de Nomes

- **Commands**: Verbo no infinitivo + Nome + `.command.ts` (ex: `login.command.ts`).
- **Queries**: Verbo no infinitivo (get, list) + Nome + `.query.ts` (ex: `list-properties.query.ts`).
