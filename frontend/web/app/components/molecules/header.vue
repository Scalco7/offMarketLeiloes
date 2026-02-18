<script setup lang="ts">
import Button from '../atoms/button.vue';
import { useAuth } from '~/composables/useAuth';
import { LucideStar, LucideLogOut } from 'lucide-vue-next';

const rounter = useRouter()
const { isLoggedIn, logout } = useAuth()

function goToCadastro() {
    if (rounter.currentRoute.value.path === '/register') return
    rounter.push('/register')
}

function goToLogin() {
    if (rounter.currentRoute.value.path === '/login') return
    rounter.push('/login')
}

function goToHome() {
    if (rounter.currentRoute.value.path === '/') return
    rounter.push('/')
}

function gotToFavorites() {
    rounter.push('/favorites')
}
</script>

<template>
    <header class="bg-primary">
        <v-container>
            <v-row justify="space-between">
                <v-col class="flex">
                    <v-img src="~/assets/logo.png" alt="Logo" width="200" @click="goToHome" class="cursor-pointer" />
                </v-col>
                <v-row align="center" justify="center" class="ga-4" style="max-width: 300px;">
                    <template v-if="isLoggedIn">
                        <Button variant="tertiary" :loading="false" @click="gotToFavorites">
                            <LucideStar />
                            <span class="ml-2">Favoritos</span>
                        </Button>
                        <Button variant="outlined-white" :loading="false" @click="logout">
                            <LucideLogOut />
                            <span class="ml-2">Sair</span>
                        </Button>
                    </template>
                    <template v-else>
                        <Button variant="outlined-white" :loading="false" @click="goToCadastro">
                            Cadastro
                        </Button>
                        <Button variant="tertiary" :loading="false" @click="goToLogin">
                            Login
                        </Button>
                    </template>
                </v-row>
            </v-row>
        </v-container>
    </header>
</template>