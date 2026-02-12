<template>
    <v-card rounded="xl" elevation="4" class="overflow-hidden border-0 bg-white" width="270" height="440">
        <v-skeleton-loader type="image" height="250" class="rounded-0"></v-skeleton-loader>

        <div class="pa-0">
            <v-skeleton-loader type="text" class="w-75"></v-skeleton-loader>
        </div>

        <v-card-text class="pa-0">
            <v-col class="pa-0 ga-1 d-flex flex-column">
                <v-skeleton-loader type="text" width="120"></v-skeleton-loader>
                <v-skeleton-loader type="heading" class="mb-2 mt-8"></v-skeleton-loader>
            </v-col>
        </v-card-text>
    </v-card>
</template>

<style scoped>
:deep(.v-skeleton-loader) {
    background: transparent !important;
}

:deep(.v-skeleton-loader__image) {
    height: 100% !important;
}
</style>
