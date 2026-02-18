<script setup lang="ts">
import { useAuth } from '~/composables/useAuth'
import { useToastStore } from '~/stores/toast'
import Favorites from '~/components/templates/favorites.vue'
import type { IFavoriteRequest } from '~/api/modules/favorites/favorites.interface'
import type { IListPropertiesResponse, IPropertyList } from '~/api/modules/property/queries/list-properties/list-properties.interface'

definePageMeta({
    middleware: 'auth'
})

const { $api } = useNuxtApp()
const { isLoggedIn } = useAuth()
const toast = useToastStore()

const properties = ref<IListPropertiesResponse>()

function fetchProperties(
    page: number = 1,
    name?: string,
    sortByPrice?: "asc" | "desc"
) {
    const pageSize = 12
    $api.favorites.queries.list({ page, pageSize, name, sortByPrice }).then((response) => {
        properties.value = response.data
    })
}

function handleUpdatePage(page: number) {
    properties.value = undefined
    fetchProperties(page)
}

function handleToggleFavorite(property: IPropertyList) {
    console.log(isLoggedIn.value)
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
</script>

<template>
    <Favorites :properties="properties" @updatePage="handleUpdatePage" @toggleFavorite="handleToggleFavorite" />
</template>