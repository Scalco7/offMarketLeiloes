<script setup lang="ts">
import Button from '../atoms/button.vue';


</script>

<template>
    <header class="bg-primary">
        <v-container>
            <v-row align="center" justify="space-between">
                <v-col cols="10">
                    <v-img src="~/assets/logo.png" alt="Logo" width="200" />
                </v-col>
                <v-row align="center" justify="space-between" cols="2">
                    <Button variant="outlined-white" :loading="false">
                        Cadastro
                    </Button>
                    <Button variant="tertiary" :loading="false">
                        Login
                    </Button>
                </v-row>
            </v-row>
        </v-container>
    </header>
</template>