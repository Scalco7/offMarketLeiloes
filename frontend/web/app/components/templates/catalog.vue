<script setup lang="ts">
import type { IListPropertiesResponse } from '~/api/modules/property/queries/list-properties/list-properties.interface';
import PropertiesList from '../organisms/propertiesList.vue';
import SearchBar from '../molecules/searchBar.vue';
import type { ISearchFilters } from '~/utils/interfaces/searchFilters';
import type { IAvailableState } from '~/api/modules/property/queries/list-available-states/list-available-states.interface';

interface ICatalogProps {
    availableStates?: IAvailableState[]
    properties?: IListPropertiesResponse
    filters: ISearchFilters
}

const props = defineProps<ICatalogProps>()
const emit = defineEmits(['updatePage', 'toggleFavorite', 'resetFilters'])

const filters = defineModel<ISearchFilters>('filters', { required: true })

watch(() => props.filters, () => {
    console.log(props.filters)
}, { deep: true })

function handleSearch() {
    emit('updatePage', 1)
}
</script>

<template>
    <v-container fluid class="pa-0">
        <div class="d-flex align-center w-100 position-relative" style="padding-bottom: 100px">
            <v-img src="~/assets/leilao.png" cover min-height="100" max-height="400"></v-img>
            <div class="position-absolute d-flex align-center w-100 h-full bottom-0">
                <SearchBar :availableStates="availableStates" v-model:filters="filters" @search="handleSearch" />
            </div>
        </div>
        <v-container>
            <PropertiesList :properties="properties" @updatePage="emit('updatePage', $event)"
                @toggleFavorite="emit('toggleFavorite', $event)" title="Imóveis em Destaque"
                @resetFilters="emit('resetFilters')" />
        </v-container>
    </v-container>
</template>