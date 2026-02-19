<script setup lang="ts">
interface ITextInputProps {
    modelValue?: string | number
    label?: string
    placeholder?: string
    type?: string
    hideDetails?: boolean
    prefix?: string
    isCurrency?: boolean
}

const props = withDefaults(defineProps<ITextInputProps>(), {
    modelValue: '',
    label: '',
    placeholder: '',
    type: 'text',
    hideDetails: true,
    prefix: undefined,
    isCurrency: false
})

const emit = defineEmits(['update:modelValue'])

const displayValue = computed(() => {
    if (!props.isCurrency) return props.modelValue
    if (props.modelValue === undefined || props.modelValue === null || props.modelValue === '') return ''

    const val = typeof props.modelValue === 'string' ? parseFloat(props.modelValue) : props.modelValue
    if (isNaN(val)) return ''

    return new Intl.NumberFormat('pt-BR', {
        minimumFractionDigits: 2,
        maximumFractionDigits: 2
    }).format(val)
})

function handleInput(val: string) {
    if (!props.isCurrency) {
        emit('update:modelValue', val)
        return
    }

    const numericValue = val.replace(/\D/g, '')
    if (!numericValue) {
        emit('update:modelValue', undefined)
        return
    }

    const cents = parseInt(numericValue)
    const finalValue = cents / 100
    emit('update:modelValue', finalValue)
}
</script>

<template>
    <div class="d-flex flex-column ga-1 w-100">
        <label v-if="label" class="text-white text-body-2 font-weight-medium ml-1">
            {{ label }}
        </label>
        <v-text-field v-bind="$attrs" :model-value="displayValue" @update:model-value="handleInput($event)"
            :placeholder="placeholder" :type="isCurrency ? 'text' : type" :hide-details="hideDetails" density="compact"
            rounded="lg" bg-color="white" class="custom-input" variant="solo-filled">
            <template v-if="prefix" v-slot:prepend-inner>
                <span class="text-grey-darken-1 mr-1">{{ prefix }}</span>
            </template>
        </v-text-field>
    </div>
</template>
