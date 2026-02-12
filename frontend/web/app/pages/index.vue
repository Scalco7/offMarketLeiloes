<script setup lang="ts">
import type { IListPropertiesResponse } from '~/api/modules/property/queries/list-properties.query'
import Catalog from '~/components/templates/catalog.vue'

const { $api } = useNuxtApp()

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
    $api.property.queries.list({ page, pageSize, name, minPrice, maxPrice, state, city, sortByPrice }).then((response) => {
        properties.value = response.data
    })
}

function handleUpdatePage(page: number) {
    properties.value = undefined
    fetchProperties(page)
}

onMounted(() => {
    fetchProperties()
})
</script>

<template>
    <Catalog :properties="properties" @updatePage="handleUpdatePage" />
</template>