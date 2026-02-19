<script setup lang="ts">
import PropertySkeleton from "../molecules/propertySkeleton.vue";
import PropertyBox from "../molecules/propertyBox.vue";
import EmptyState from "../molecules/emptyState.vue";
import type { IListPropertiesResponse, IPropertyList } from "~/api/modules/property/queries/list-properties/list-properties.interface";

interface IPropertiesListProps {
    title: string
    properties?: IListPropertiesResponse
}

const props = defineProps<IPropertiesListProps>()
const emit = defineEmits(['updatePage', 'resetFilters', 'toggleFavorite'])

const page = ref(props.properties?.number || 1)

watch(page, (newPage) => {
    emit('updatePage', newPage)
})

function navigateToProperty(property: IPropertyList) {
    navigateTo(property.auctionLink, { external: true, open: { target: '_blank' } })
}

function toggleFavorite(property: IPropertyList) {
    emit('toggleFavorite', property)
}
</script>

<template>
    <v-container>
        <v-col class="d-flex flex-column ga-10">
            <h2>{{ title }}</h2>
            <div v-if="properties">
                <v-row class="ga-4" v-if="properties.content.length > 0">
                    <PropertyBox v-for="property in properties.content" :key="property.id"
                        :imageLink="property.imageLink" :title="property.name" :currentPrice="property.currentPrice"
                        :oldPrice="property.valuedPrice" :endDate="property.auctionDateTime"
                        :city="property.address.city" :state="property.address.state"
                        :auctioneerName="property.auctioneerName" :isFavorite="property.isFavorite"
                        :discount="property.discount" @click="navigateToProperty(property)"
                        @toggleFavorite="toggleFavorite(property)" />
                </v-row>
                <EmptyState v-else @resetFilters="emit('resetFilters')" />
            </div>
            <v-row v-else class="ga-4">
                <PropertySkeleton v-for="i in 4" :key="i" />
            </v-row>

            <v-pagination v-if="properties && properties.content.length > 0" :length="properties.totalPages"
                :total-visible="4" v-model="page">
            </v-pagination>
        </v-col>
    </v-container>
</template>