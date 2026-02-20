<script setup lang="ts">
import Button from '../atoms/button.vue';
import { useAuth } from '~/composables/useAuth';
import { LucideStar, LucideLogOut, LucideMenu, LucideHome } from 'lucide-vue-next';

const router = useRouter()
const { isLoggedIn, logout } = useAuth()

const emit = defineEmits(['toggle-drawer'])

function goToCadastro() {
    if (router.currentRoute.value.path === '/register') return
    router.push('/register')
}

function goToLogin() {
    if (router.currentRoute.value.path === '/login') return
    router.push('/login')
}

function goToHome() {
    if (router.currentRoute.value.path === '/') return
    router.push('/')
}

function gotToFavorites() {
    router.push('/favorites')
}

const isInFavoritesPath = computed(() => {
    return router.currentRoute.value.path === '/favorites'
})
</script>

<template>
    <header class="bg-primary">
        <v-container>
            <v-row align="center" justify="space-between" no-gutters>
                <div class="d-flex align-center">
                    <v-img src="~/assets/logo.png" alt="Logo" width="160" sm="200" @click="goToHome"
                        class="cursor-pointer" />
                </div>

                <div class="d-none d-md-flex ga-4 align-center">
                    <template v-if="isLoggedIn">
                        <Button v-if="isInFavoritesPath" variant="tertiary" :loading="false" @click="goToHome">
                            <LucideHome :size="20" />
                            <span class="ml-2">Todos os Imóveis</span>
                        </Button>
                        <Button v-else variant="tertiary" :loading="false" @click="gotToFavorites">
                            <LucideStar :size="20" />
                            <span class="ml-2">Meus Favoritos</span>
                        </Button>

                        <Button variant="outlined-white" :loading="false" @click="() => logout(true)">
                            <LucideLogOut :size="20" />
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
                </div>

                <v-btn icon variant="text" color="white" class="d-md-none" @click="emit('toggle-drawer')">
                    <LucideMenu />
                </v-btn>
            </v-row>
        </v-container>
    </header>
</template>