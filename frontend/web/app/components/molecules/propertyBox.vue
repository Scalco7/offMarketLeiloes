<script setup lang="ts">
import { computed } from 'vue';
import { actioneerLogoData } from '~/utils/helpers/actioneer-logo-data';
import formatDate from '~/utils/helpers/formatDate';
import formatDateTime from '~/utils/helpers/formatDateTime';
import formatMoney from '~/utils/helpers/formatMoney';
import { normalizeStateName } from '~/utils/helpers/normalizeStateName';

interface PropertyProps {
    imageLink?: string;
    auctioneerName: string;
    endDate: Date;
    city?: string;
    state?: string;
    title: string;
    currentPrice: number;
    oldPrice?: number;
    discount?: number;
    isFavorite?: boolean;
}

const props = defineProps<PropertyProps>()

const stateName = computed(() => {
    if (!props.state) return ""
    else if (props.city) return props.state
    else return normalizeStateName(props.state)
})

const actioneerLogoPath = computed(() => {
    return actioneerLogoData[props.auctioneerName]?.logoPath || null
})

const actioneerLogoBackgroundColor = computed(() => {
    return actioneerLogoData[props.auctioneerName]?.backgroundColor || null
})

defineEmits(['toggleFavorite', 'click']);
</script>

<template>
    <v-card rounded="xl" elevation="4" class="overflow-hidden border-0 property-card-wrapper" @click="$emit('click')"
        height="440">
        <v-img :src="props.imageLink" height="250" cover class="position-relative d-flex align-end">
            <div v-if="actioneerLogoPath" class="d-flex align-end justify-end w-100" style="z-index: 1">
                <div class="px-3 py-1 d-flex align-center justify-center shadow-sm"
                    :style="`background-color: ${actioneerLogoBackgroundColor}; border-top-left-radius: 8px; width: fit-content;`">
                    <v-img :src="actioneerLogoPath" width="80" height="30px" contain />
                </div>
            </div>
            <div v-else class="d-flex align-end justify-end w-100 " style="z-index: 1">
                <div class="bg-primary px-5 d-flex align-center justify-center shadow-sm"
                    style="border-top-left-radius: 8px; width: fit-content;">
                    {{ props.auctioneerName }}
                </div>
            </div>

            <div class="bg-tertiary text-white text-center py-2 text-body-2 font-weight-medium position-relative"
                style="z-index: 2; width: 100%;">
                Data do leilão: {{ props.endDate.getHours() == 0 ? formatDate(props.endDate) :
                    formatDateTime(props.endDate) }}
            </div>

            <template v-slot:placeholder>
                <v-row class="fill-height ma-0" align="center" justify="center">
                    <LucideHousePlus :size="100" class="text-grey-lighten-4" />
                </v-row>
            </template>
        </v-img>

        <v-card-text class="pa-5">
            <v-col class="pa-0 ga-5 d-flex flex-column">
                <v-col class="pa-0 ga-1 d-flex flex-column">
                    <div class="d-flex align-center justify-space-between">
                        <div class="d-flex align-center text-grey-darken-1 text-body-2">
                            <LucideMapPin :size="20" class="mr-1 text-grey" />
                            <span class="text-truncate font-weight-medium">{{ props.city || props.state ? `${props.city
                                ?? ""}
                                ${props.city && props.state
                                    ? "-" :
                                    ""} ${stateName}` : "À Consultar"
                            }}</span>
                        </div>

                        <v-btn icon variant="plain" density="comfortable" class="mr-n2" :color="'grey-darken-1'"
                            @click.stop="$emit('toggleFavorite')">
                            <LucideStar :size="25" :style="props.isFavorite ? 'fill: currentColor' : ''" />
                        </v-btn>
                    </div>

                    <h3 class="font-weight-semibold text-grey-darken-4 leading-tight line-clamp-2">
                        {{ props.title }}
                    </h3>
                </v-col>

                <div class="d-flex align-end justify-space-between mt-auto">
                    <div class="d-flex flex-column">
                        <div class="text-h6 font-weight-bold text-tertiary lh-1 mb-1">
                            {{ formatMoney(props.currentPrice) }}
                        </div>
                        <div v-if="props.oldPrice && props.oldPrice > props.currentPrice"
                            class="text-body-2 text-grey-lighten-1 text-decoration-line-through">
                            {{ formatMoney(props.oldPrice) }}
                        </div>
                    </div>

                    <div v-if="props.discount"
                        class="bg-success text-white px-3 py-1 rounded-pill d-flex align-center font-weight-bold text-caption">
                        <LucideArrowDown :size="14" class="mr-1" stroke-width="3" />
                        {{ props.discount }}%
                    </div>
                </div>
            </v-col>
        </v-card-text>
    </v-card>
</template>

<style scoped>
.line-clamp-2 {
    display: -webkit-box;
    -webkit-line-clamp: 2;
    line-clamp: 2;
    -webkit-box-orient: vertical;
    overflow: hidden;
    min-height: 2.5rem;
}

.lh-1 {
    line-height: 1;
}

.leading-tight {
    line-height: 1.25;
}
</style>
