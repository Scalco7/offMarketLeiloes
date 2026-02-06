<script setup lang="ts">
import Button from '~/components/atoms/button.vue';

</script>

<template>
    <div class="d-flex flex-column pa-10 ga-10 align-center justify-center bg-grey">
        Página inicial
        <Button variant="primary" :loading="true">
            Test Button
        </Button>
        <Button variant="tertiary" :loading="false">
            Test Button
        </Button>
        <Button variant="outlined-white" :loading="false">
            Test Button
        </Button>
    </div>
</template>