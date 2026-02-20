<script setup lang="ts">
import PropertySkeleton from "../molecules/propertySkeleton.vue";
import PropertyBox from "../molecules/propertyBox.vue";
import EmptyState from "../molecules/emptyState.vue";
import type { IListPropertiesResponse, IPropertyList } from "~/api/modules/property/queries/list-properties/list-properties.interface";

interface IPropertiesListProps {
    title: string
    emptyStateButtonText: string
    properties?: IListPropertiesResponse
}

const props = defineProps<IPropertiesListProps>()
const emit = defineEmits(['updatePage', 'onClickEmptyAction', 'toggleFavorite'])

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
                <v-row v-if="properties.content.length > 0">
                    <v-col v-for="property in properties.content" :key="property.id" cols="12" sm="6" md="4" lg="3"
                        xl="2">
                        <PropertyBox :imageLink="property.imageLink" :title="property.name"
                            :currentPrice="property.currentPrice" :oldPrice="property.valuedPrice"
                            :endDate="property.auctionDateTime" :city="property.address.city"
                            :state="property.address.state" :auctioneerName="property.auctioneerName"
                            :isFavorite="property.isFavorite" :discount="property.discount"
                            @click="navigateToProperty(property)" @toggleFavorite="toggleFavorite(property)" />
                    </v-col>
                </v-row>
                <EmptyState v-else @onClickEmptyAction="emit('onClickEmptyAction')"
                    :buttonText="props.emptyStateButtonText" />
            </div>
            <v-row v-else class="ga-4">
                <v-col v-for="i in 4" :key="i" cols="12" sm="6" md="4" lg="3" xl="2">
                    <PropertySkeleton />
                </v-col>
            </v-row>

            <v-pagination v-if="properties && properties.content.length > 0" :length="properties.totalPages"
                :total-visible="4" v-model="page">
            </v-pagination>
        </v-col>
    </v-container>
</template>