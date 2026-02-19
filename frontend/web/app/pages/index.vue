<script setup lang="ts">
import { useAuth } from '~/composables/useAuth'
import Catalog from '~/components/templates/catalog.vue'
import type { IFavoriteRequest } from '~/api/modules/favorites/favorites.interface'
import { useToastStore } from '~/stores/toast'
import type { IListPropertiesResponse, IPropertyList } from '~/api/modules/property/queries/list-properties/list-properties.interface'
import type { IAvailableState } from '~/api/modules/property/queries/list-available-states/list-available-states.interface'
import type { ISearchFilters } from '~/utils/interfaces/searchFilters'

const { $api } = useNuxtApp()
const { isLoggedIn } = useAuth()
const toast = useToastStore()

const properties = ref<IListPropertiesResponse>()
const availableStates = ref<IAvailableState[]>()
const filters = ref<ISearchFilters>({
    name: '',
    sortByPrice: undefined,
    minPrice: undefined,
    maxPrice: undefined,
    state: ''
})

function fetchProperties(
    page: number = 1,
    name: string | undefined = filters.value.name,
    minPrice: number | undefined = filters.value.minPrice,
    maxPrice: number | undefined = filters.value.maxPrice,
    state: string | undefined = filters.value.state,
    city?: string,
    sortByPrice: "asc" | "desc" | undefined = filters.value.sortByPrice
) {
    const pageSize = 12
    scrollToTop()
    $api.property.queries.list({ page, pageSize, name, minPrice, maxPrice, state, city, sortByPrice }).then((response) => {
        properties.value = response.data
    })
}

function fetchAvailableStates() {
    $api.property.queries.listAvailableStates().then((response) => {
        availableStates.value = response.data
    })
}

function scrollToTop() {
    if (!window) return

    window.scrollTo({
        top: 0,
        behavior: 'smooth'
    });
}

function handleUpdatePage(page: number) {
    properties.value = undefined
    const { name, minPrice, maxPrice, state, sortByPrice } = filters.value
    fetchProperties(page, name, minPrice, maxPrice, state, undefined, sortByPrice)
}

function resetFilters() {
    filters.value.name = ''
    filters.value.minPrice = undefined
    filters.value.maxPrice = undefined
    filters.value.state = ''
    filters.value.sortByPrice = undefined
    handleUpdatePage(1)
}

function handleToggleFavorite(property: IPropertyList) {

    if (!isLoggedIn.value) {
        navigateTo('/login')
        return
    }
    const requestData: IFavoriteRequest = {
        propertyId: property.id,
    }

    if (property.isFavorite) {
        $api.favorites.commands.remove(requestData).then(() => {
            property.isFavorite = false
            toast.success("Imóvel removido dos favoritos.")
        })
    } else {
        $api.favorites.commands.add(requestData).then(() => {
            property.isFavorite = true
            toast.success("Imóvel adicionado aos favoritos.")
        })
    }
}

onMounted(() => {
    fetchProperties()
    fetchAvailableStates()
})

watch(() => isLoggedIn.value, () => {
    fetchProperties()
})
</script>

<template>
    <Catalog :properties="properties" :availableStates="availableStates" v-model:filters="filters"
        @updatePage="handleUpdatePage" @toggleFavorite="handleToggleFavorite" @resetFilters="resetFilters" />
</template>
