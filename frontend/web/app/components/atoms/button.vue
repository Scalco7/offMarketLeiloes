<script setup lang="ts">
type buttonVariant = "primary" | "tertiary" | "outlined-white";

interface ButtonProps {
    loading: boolean;
    variant: buttonVariant;
}

const { loading, variant } = defineProps<ButtonProps>()

const variantColors = computed(() => {
    switch (variant) {
        case "primary":
            return "text-white bg-primary";
        case "tertiary":
            return "text-white bg-tertiary";
        case "outlined-white":
            return "text-white bg-outlined-white";
        default:
            return "";
    }
})

const variantType = computed(() => {
    switch (variant) {
        case "outlined-white":
            return "outlined";
        default:
            return undefined;
    }
})
</script>

<template>
    <v-btn class="text-none" :class="variantColors" :loading="loading" :variant="variantType" rounded="lg" size="large">
        <slot />
    </v-btn>
</template>