<script setup lang="ts">
import Header from '@/components/molecules/header.vue'
import Footer from '@/components/molecules/footer.vue'
</script>

<template>
    <div>
        <Header />
        <div class="auth-wrapper d-flex align-center justify-center">
            <v-card class="rounded-lg overflow-hidden elevation-10" width="100%" max-width="1000" height="90%">
                <v-row no-gutters class="fill-height" height="100%">
                    <v-col cols="12" md="6" class="pa-4 d-none d-md-flex align-center justify-center" height="100%">
                        <div class="bg-primary rounded-xl d-flex align-center justify-center pa-10 fill-height w-100">
                            <v-img src="~/assets/logo.png" alt="Off Market Leilões" max-width="80%" contain></v-img>
                        </div>
                    </v-col>

                    <v-col cols="12" md="6" class="d-flex align-center justify-center pa-8 pa-md-16" height="100%">
                        <div class="w-100" style="max-width: 400px;">
                            <slot />
                        </div>
                    </v-col>
                </v-row>
            </v-card>
        </div>
        <Footer />
    </div>
</template>

<style scoped>
.auth-wrapper {
    min-height: 100vh;
    background-color: #D6E0F2;
}

:deep(.custom-input .v-field) {
    border-radius: 8px !important;
    background-color: #ffffff !important;
}

:deep(.custom-input .v-field__outline) {
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
