<script setup lang="ts">
interface ISelectInputProps {
    modelValue?: any
    label?: string
    placeholder?: string
    items: any[]
    itemTitle?: string
    itemValue?: string
    hideDetails?: boolean
}

const props = withDefaults(defineProps<ISelectInputProps>(), {
    modelValue: null,
    label: '',
    placeholder: '',
    itemTitle: 'title',
    itemValue: 'value',
    hideDetails: true
})

const emit = defineEmits(['update:modelValue'])
</script>

<template>
    <div class="d-flex flex-column ga-1 w-100">
        <label v-if="label" class="text-white text-body-2 font-weight-medium ml-1">
            {{ label }}
        </label>
        <v-select v-bind="$attrs" :model-value="modelValue" @update:model-value="emit('update:modelValue', $event)"
            :placeholder="placeholder" :items="items" :item-title="itemTitle" :item-value="itemValue"
            :hide-details="hideDetails" density="compact" rounded="lg" bg-color="white"
            class="custom-select"></v-select>
    </div>
</template>

<style scoped>
:deep(.v-field__input) {
    min-height: 44px !important;
}
</style>
