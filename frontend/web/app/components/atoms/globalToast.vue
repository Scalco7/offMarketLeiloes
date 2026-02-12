<script setup lang="ts">
import { useToastStore } from '~/stores/toast'
import { storeToRefs } from 'pinia'

const toastStore = useToastStore()
const { show, message, type, timeout } = storeToRefs(toastStore)
</script>

<template>
    <v-snackbar v-model="show" :color="type" :timeout="timeout" location="top right" variant="flat" class="mt-4">
        <div class="d-flex align-center">
            <v-icon start
                :icon="type === 'success' ? 'mdi-check-circle' : type === 'error' ? 'mdi-alert-circle' : 'mdi-information'" />
            <span class="font-weight-medium">{{ message }}</span>
        </div>

        <template v-slot:actions>
            <v-btn icon="mdi-close" variant="text" @click="show = false"></v-btn>
        </template>
    </v-snackbar>
</template>

<style scoped>
:deep(.v-snackbar__wrapper) {
    border-radius: 8px !important;
}
</style>
