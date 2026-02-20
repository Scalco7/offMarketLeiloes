<script setup lang="ts">
import Header from '~/components/molecules/header.vue'
import Footer from '~/components/molecules/footer.vue'
import { LucideHome, LucideStar, LucideLogOut } from 'lucide-vue-next';
import { useAuth } from '~/composables/useAuth';

const { isLoggedIn, logout } = useAuth()
const router = useRouter()
const drawer = ref(false)

function toggleDrawer() {
    drawer.value = !drawer.value
}

function navigateAndClose(path: string) {
    router.push(path)
    drawer.value = false
}

const isInFavoritesPath = computed(() => {
    return router.currentRoute.value.path === '/favorites'
})
</script>

<template>
    <div>
        <v-navigation-drawer v-model="drawer" temporary location="right">
            <v-list nav>
                <v-list-item v-if="isLoggedIn && isInFavoritesPath" prepend-icon="mdi-home" title="Todos os Imóveis"
                    @click="navigateAndClose('/')">
                    <template v-slot:prepend>
                        <LucideHome :size="20" />
                    </template>
                </v-list-item>
                <v-list-item v-if="isLoggedIn && !isInFavoritesPath" title="Meus Favoritos"
                    @click="navigateAndClose('/favorites')">
                    <template v-slot:prepend>
                        <LucideStar :size="20" />
                    </template>
                </v-list-item>

                <v-divider v-if="isLoggedIn" class="my-2"></v-divider>

                <v-list-item v-if="isLoggedIn" title="Sair" @click="() => logout(true)" base-color="error">
                    <template v-slot:prepend>
                        <LucideLogOut :size="20" />
                    </template>
                </v-list-item>

                <template v-if="!isLoggedIn">
                    <v-list-item title="Login" @click="navigateAndClose('/login')"></v-list-item>
                    <v-list-item title="Cadastro" @click="navigateAndClose('/register')"></v-list-item>
                </template>
            </v-list>
        </v-navigation-drawer>

        <Header @toggle-drawer="toggleDrawer" />
        <v-main>
            <slot />
        </v-main>
        <Footer />
    </div>
</template>