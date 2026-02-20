<script setup lang="ts">
import type { IListPropertiesResponse } from '~/api/modules/property/queries/list-properties/list-properties.interface';
import PropertiesList from '../organisms/propertiesList.vue';

interface IFavoritesProps {
    properties?: IListPropertiesResponse
}

const props = defineProps<IFavoritesProps>()
const emit = defineEmits(['updatePage', 'toggleFavorite'])

function goHome() {
    navigateTo('/')
}
</script>

<template>
    <v-container fluid class="pa-0">
        <v-img src="~/assets/leilao.png" cover min-height="100" max-height="400"></v-img>
        <v-container>
            <PropertiesList :properties="properties" title="Imóveis Favoritos" emptyStateButtonText="Ver Imóveis"
                @toggleFavorite="emit('toggleFavorite', $event)" @onClickEmptyAction="goHome"
                @updatePage="emit('updatePage', $event)" />
        </v-container>
    </v-container>
</template>