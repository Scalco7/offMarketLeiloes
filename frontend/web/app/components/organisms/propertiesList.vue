<script setup lang="ts">
import type { IListPropertiesResponse } from "~/api/modules/property/queries/list-properties.query";
import PropertyBox from "../molecules/propertyBox.vue";

interface IPropertiesListProps {
    properties: IListPropertiesResponse
}

const props = defineProps<IPropertiesListProps>()
const emit = defineEmits(['updatePage'])

const page = ref(props.properties.number)

watch(page, (newPage) => {
    emit('updatePage', newPage)
})

</script>

<template>
    <v-container>
        <v-col class="d-flex flex-column ga-10">
            <h2>Imóveis em Destaque</h2>
            <v-row class="ga-4">
                <PropertyBox v-for="property in properties.content" :key="property.id" :imageLink="property.imageLink"
                    :title="property.name" :currentPrice="property.currentPrice" :oldPrice="property.valuedPrice"
                    :endDate="property.auctionDateTime" :city="property.address.city" :state="property.address.state"
                    :auctioneerName="property.auctioneerName" />
            </v-row>
            <v-pagination :length="properties.totalPages" :total-visible="4" v-model="page">
            </v-pagination>
        </v-col>
    </v-container>
</template>