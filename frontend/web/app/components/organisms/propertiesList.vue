<script setup lang="ts">
import type { IPropertyList, IListPropertiesResponse } from "~/api/modules/property/queries/list-properties.query";
import PropertyBox from "../molecules/propertyBox.vue";
import { EPropertyStatus } from "~/utils/enums/propertyStatus.enum";
import { EPropertyType } from "~/utils/enums/propertyType.enum";

const mockContent: IPropertyList[] = [
    {
        id: "1",
        name: "Imóvel 1",
        description: "Descrição do imóvel 1",
        valuedPrice: 1000,
        currentPrice: 1000,
        auctionDateTime: new Date(),
        auctioneerName: "Zé Leilão",
        auctionLink: "https://auctioneer.com/1",
        imageLink: "https://picsum.photos/150",
        address: {
            city: "City 1",
            state: "State 1",
            zipCode: "",
            street: "",
            number: "",
            neighborhood: ""
        },
        type: EPropertyType.HOUSE,
        status: EPropertyStatus.ACTIVE
    },
    {
        id: "2",
        name: "Casa Térrea em Bairro Aviação 100m² com jardim e acabamento nobre.",
        description: "Descrição do imóvel 2",
        valuedPrice: 1000,
        currentPrice: 1000,
        auctionDateTime: new Date(),
        auctioneerName: "Bradesco",
        auctionLink: "https://auctioneer.com/2",
        imageLink: "https://picsum.photos/150",
        address: {
            city: "City 2",
            state: "State 2",
            zipCode: "",
            street: "",
            number: "",
            neighborhood: ""
        },
        type: EPropertyType.HOUSE,
        status: EPropertyStatus.ACTIVE,
    },
    {
        id: "3",
        name: "Imóvel 3",
        description: "Descrição do imóvel 3",
        valuedPrice: 1000,
        currentPrice: 1000,
        auctionDateTime: new Date(),
        auctioneerName: "Caixa",
        auctionLink: "https://auctioneer.com/3",
        address: {
            city: "City 3",
            state: "State 3",
            zipCode: "",
            street: "",
            number: "",
            neighborhood: ""
        },
        type: EPropertyType.HOUSE,
        status: EPropertyStatus.ACTIVE
    }
]

const properties = ref<IListPropertiesResponse>({
    content: mockContent,
    totalElements: 4,
    totalPages: 1,
    size: 4,
    number: 0,
});
</script>

<template>
    <v-container>
        <v-col class="d-flex flex-column ga-10">
            <h2>Imóveis em Destaque</h2>
            <v-row class="ga-5">
                <PropertyBox v-for="property in properties.content" :key="property.id" :imageLink="property.imageLink"
                    :title="property.name" :currentPrice="property.currentPrice" :oldPrice="property.valuedPrice"
                    :endDate="property.auctionDateTime" :city="property.address.city" :state="property.address.state"
                    :discount="20" :is-favorite="true" :auctioneerName="property.auctioneerName" />
            </v-row>
            <v-pagination></v-pagination>
        </v-col>
    </v-container>
</template>