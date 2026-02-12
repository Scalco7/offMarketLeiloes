<script setup lang="ts">
import PropertiesList from '../organisms/propertiesList.vue';
import type { IListPropertiesResponse } from '~/api/modules/property/queries/list-properties.query';

interface ICatalogProps {
    properties?: IListPropertiesResponse
}

const props = defineProps<ICatalogProps>()
const emit = defineEmits(['updatePage'])
</script>

<template>
    <v-container fluid class="pa-0">
        <v-img src="~/assets/leilao.png" cover min-height="100" max-height="400"></v-img>
        <v-container>
            <PropertiesList :properties="properties" @updatePage="emit('updatePage', $event)" />
        </v-container>
    </v-container>
</template>