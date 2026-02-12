<script setup lang="ts">
import { ref } from 'vue'
import Button from '~/components/atoms/button.vue'

const email = ref('')
const password = ref('')
const showPassword = ref(false)
const loading = ref(false)

const handleLogin = () => {
    loading.value = true
    console.log('Login clicked', { email: email.value, password: password.value })
    setTimeout(() => {
        loading.value = false
    }, 2000)
}
</script>

<template>
    <div class="login-wrapper d-flex align-center justify-center">
        <v-card class="rounded-lg overflow-hidden elevation-10" width="100%" max-width="1000" height="90%"
            min-height="600">
            <v-row no-gutters class="fill-height">
                <v-col cols="12" md="6" class="pa-4 d-none d-md-flex align-center justify-center">
                    <div class="bg-primary rounded-xl d-flex align-center justify-center pa-10 fill-height w-100">
                        <v-img src="~/assets/logo.png" alt="Off Market Leilões" max-width="80%" contain></v-img>
                    </div>
                </v-col>

                <v-col cols="12" md="6" class="d-flex align-center justify-center pa-8 pa-md-16">
                    <div class="w-100" style="max-width: 400px;">
                        <h1 class="text-h3 font-weight-medium text-center mb-10" style="letter-spacing: 4px;">LOGIN</h1>

                        <div class="mb-6">
                            <label
                                class="text-subtitle-2 font-weight-bold text-grey-darken-2 mb-2 d-block">E-mail</label>
                            <v-text-field v-model="email" placeholder="seuemail@gmail.com" variant="outlined"
                                density="comfortable" hide-details
                                class="custom-input elevation-1 rounded-lg"></v-text-field>
                        </div>

                        <div class="mb-12">
                            <label
                                class="text-subtitle-2 font-weight-bold text-grey-darken-2 mb-2 d-block">Senha</label>
                            <v-text-field v-model="password" placeholder="********"
                                :type="showPassword ? 'text' : 'password'" variant="outlined" density="comfortable"
                                hide-details class="custom-input elevation-1 rounded-lg"
                                :append-inner-icon="showPassword ? 'mdi-eye-off-outline' : 'mdi-eye-outline'"
                                @click:append-inner="showPassword = !showPassword"></v-text-field>
                        </div>

                        <Button variant="tertiary" :loading="loading" block class="py-6 text-h6 font-weight-bold"
                            @click="handleLogin">
                            Login
                        </Button>
                    </div>
                </v-col>
            </v-row>
        </v-card>
    </div>
</template>

<style scoped>
.login-wrapper {
    min-height: 100vh;
    background-color: #D6E0F2;
}

.custom-input :deep(.v-field) {
    border-radius: 8px !important;
    background-color: #ffffff !important;
}

.custom-input :deep(.v-field__outline) {
    --v-field-border-opacity: 0.1;
}

@media (max-width: 959px) {
    .v-card {
        height: auto !important;
        min-height: auto !important;
        margin: 20px;
    }
}
</style>