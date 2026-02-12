<script setup lang="ts">
import GlobalToast from '@/components/atoms/globalToast.vue'
</script>

<template>
  <v-app>
    <GlobalToast />
    <NuxtLayout>
      <NuxtPage />
    </NuxtLayout>
  </v-app>
</template>
