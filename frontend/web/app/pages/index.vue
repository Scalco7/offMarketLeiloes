<script setup lang="ts">
import { useAuth } from '~/composables/useAuth'
import Catalog from '~/components/templates/catalog.vue'
import type { IFavoriteRequest } from '~/api/modules/favorites/favorites.interface'
import { useToastStore } from '~/stores/toast'
import type { IListPropertiesResponse, IPropertyList } from '~/api/modules/property/queries/list-properties/list-properties.interface'

const { $api } = useNuxtApp()
const { isLoggedIn } = useAuth()
const toast = useToastStore()

const properties = ref<IListPropertiesResponse>()

function fetchProperties(
    page: number = 1,
    name?: string,
    minPrice?: number,
    maxPrice?: number,
    state?: string,
    city?: string,
    sortByPrice?: "asc" | "desc"
) {
    const pageSize = 12
    scrollToTop()
    $api.property.queries.list({ page, pageSize, name, minPrice, maxPrice, state, city, sortByPrice }).then((response) => {
        properties.value = response.data
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
    fetchProperties(page)
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
})

watch(() => isLoggedIn.value, () => {
    fetchProperties()
})
</script>

<template>
    <Catalog :properties="properties" @updatePage="handleUpdatePage" @toggleFavorite="handleToggleFavorite" />
</template>