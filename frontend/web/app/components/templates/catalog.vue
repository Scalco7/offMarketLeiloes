<script setup lang="ts">
import PropertiesList from '../organisms/propertiesList.vue';
</script>

<template>
    <v-container fluid class="pa-0">
        <v-img src="~/assets/leilao.png" cover min-height="100" max-height="400"></v-img>
        <v-container>
            <PropertiesList />
        </v-container>
    </v-container>
</template>