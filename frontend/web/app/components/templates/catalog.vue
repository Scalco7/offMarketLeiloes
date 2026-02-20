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
        <div class="hero-wrapper position-relative">
            <v-img src="~/assets/leilao.png" cover class="hero-image"
                :height="$vuetify.display.smAndDown ? '250' : '400'"></v-img>
            <div class="search-overlay d-flex align-center w-100">
                <v-container>
                    <SearchBar :availableStates="availableStates" v-model:filters="filters" @search="handleSearch" />
                </v-container>
            </div>
        </div>
        <v-container class="pt-8">
            <PropertiesList :properties="properties" @updatePage="emit('updatePage', $event)"
                @toggleFavorite="emit('toggleFavorite', $event)" title="Imóveis em Destaque"
                @onClickEmptyAction="emit('resetFilters')" emptyStateButtonText="Limpar Filtros" />
        </v-container>
    </v-container>
</template>

<style scoped>
.hero-wrapper {
    min-height: 300px;
    display: flex;
    flex-direction: column;
}

@media (min-width: 960px) {
    .hero-wrapper {
        min-height: 400px;
        display: block;
    }

    .search-overlay {
        position: absolute;
        bottom: -60px;
        left: 0;
        z-index: 2;
    }
}

@media (max-width: 959px) {
    .hero-wrapper {
        background-color: rgb(var(--v-theme-primary));
    }

    .search-overlay {
        position: relative;
        margin-top: -40px;
        z-index: 2;
    }
}
</style>