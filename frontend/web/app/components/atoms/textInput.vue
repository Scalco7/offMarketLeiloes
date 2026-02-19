<script setup lang="ts">
interface ITextInputProps {
    modelValue?: string | number
    label?: string
    placeholder?: string
    type?: string
    hideDetails?: boolean
}

const props = withDefaults(defineProps<ITextInputProps>(), {
    modelValue: '',
    label: '',
    placeholder: '',
    type: 'text',
    hideDetails: true
})

const emit = defineEmits(['update:modelValue'])
</script>

<template>
    <div class="d-flex flex-column ga-1 w-100">
        <label v-if="label" class="text-white text-body-2 font-weight-medium ml-1">
            {{ label }}
        </label>
        <v-text-field v-bind="$attrs" :model-value="modelValue" @update:model-value="emit('update:modelValue', $event)"
            :placeholder="placeholder" :type="type" :hide-details="hideDetails" density="compact" rounded="lg"
            bg-color="white" class="custom-input"></v-text-field>
    </div>
</template>

<style scoped>
:deep(.v-field__input) {
    min-height: 44px !important;
}
</style>
