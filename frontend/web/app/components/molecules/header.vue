<script setup lang="ts">
import Button from '../atoms/button.vue';

const rounter = useRouter()

const goToCadastro = () => {
    if (rounter.currentRoute.value.path === '/register') return
    rounter.push('/register')
}

const goToLogin = () => {
    if (rounter.currentRoute.value.path === '/login') return
    rounter.push('/login')
}

const goToHome = () => {
    if (rounter.currentRoute.value.path === '/') return
    rounter.push('/')
}

</script>

<template>
    <header class="bg-primary">
        <v-container>
            <v-row align="center" justify="space-between">
                <v-col cols="10">
                    <v-img src="~/assets/logo.png" alt="Logo" width="200" @click="goToHome" class="cursor-pointer" />
                </v-col>
                <v-row align="center" justify="space-between" cols="2">
                    <Button variant="outlined-white" :loading="false" @click="goToCadastro">
                        Cadastro
                    </Button>
                    <Button variant="tertiary" :loading="false" @click="goToLogin">
                        Login
                    </Button>
                </v-row>
            </v-row>
        </v-container>
    </header>
</template>