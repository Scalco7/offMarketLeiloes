<script setup lang="ts">
import Header from '~/components/molecules/header.vue'
import Footer from '~/components/molecules/footer.vue'
</script>

<template>
    <Header />
    <slot />
    <Footer />
</template>