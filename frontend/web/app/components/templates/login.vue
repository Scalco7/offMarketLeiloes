<script setup lang="ts">
import Button from '@/components/atoms/button.vue'
import { ref } from 'vue'

const email = ref('')
const password = ref('')
const showPassword = ref(false)
const loading = ref(false)

const { login } = useAuth()
const router = useRouter()

async function handleLogin() {
    loading.value = true
    try {
        await login({ email: email.value, password: password.value })
        router.push('/')
    } finally {
        loading.value = false
    }
}
</script>

<template>
    <h1 class="text-h3 font-weight-medium text-center mb-10" style="letter-spacing: 4px;">LOGIN</h1>

    <form @submit.prevent="handleLogin">
        <div class="mb-6">
            <label class="text-subtitle-2 font-weight-bold text-grey-darken-2 mb-2 d-block">E-mail</label>
            <v-text-field v-model="email" placeholder="seuemail@gmail.com" variant="outlined" density="comfortable"
                hide-details class="custom-input elevation-1 rounded-lg"></v-text-field>
        </div>

        <div class="mb-4">
            <label class="text-subtitle-2 font-weight-bold text-grey-darken-2 mb-2 d-block">Senha</label>
            <v-text-field v-model="password" placeholder="********" :type="showPassword ? 'text' : 'password'"
                variant="outlined" density="comfortable" hide-details class="custom-input elevation-1 rounded-lg"
                :append-inner-icon="showPassword ? 'mdi-eye-off-outline' : 'mdi-eye-outline'"
                @click:append-inner="showPassword = !showPassword"></v-text-field>
        </div>

        <Button type="submit" variant="tertiary" :loading="loading" block class="py-6 text-h6 font-weight-bold mt-8">
            Entrar
        </Button>
    </form>

    <div class="mt-6 text-center">
        <p class="text-body-2 text-grey-darken-1">
            Não tem uma conta?
            <NuxtLink to="/register" class="text-primary font-weight-bold text-decoration-none">Cadastre-se
            </NuxtLink>
        </p>
    </div>
</template>