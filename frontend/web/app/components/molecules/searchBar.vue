<script setup lang="ts">
import TextInput from '../atoms/textInput.vue'
import SelectInput from '../atoms/selectInput.vue'
import Button from '../atoms/button.vue'
import type { ISearchFilters } from '~/utils/interfaces/searchFilters'
import type { IAvailableState } from '~/api/modules/property/queries/list-available-states/list-available-states.interface'

interface SearchBarProps {
    availableStates?: IAvailableState[]
}

const props = defineProps<SearchBarProps>()
const emit = defineEmits(['search'])

const filters = reactive<ISearchFilters>({
    name: '',
    sortByPrice: undefined,
    minPrice: undefined,
    maxPrice: undefined,
    state: ''
})

const orderOptions = [
    { title: 'Menor Preço', value: 'asc' },
    { title: 'Maior Preço', value: 'desc' }
]

const stateOptions = computed(() => {
    const states = [{ title: 'Todos', value: '' }]
    if (!props.availableStates) return states
    return states.concat(props.availableStates.map((state) => {
        return {
            title: state.state,
            value: state.state
        }
    }))
})

function handleSearch() {
    emit('search', { ...filters })
}
</script>

<template>
    <div class="search-bar-container w-100">
        <div class="search-tab d-inline-flex align-center bg-primary px-8 py-3 rounded-t-xl">
            <span class="text-white font-weight-bold text-h6">Buscar imóvel</span>
        </div>

        <v-sheet color="primary" class="pa-6 rounded-b-xl rounded-te-xl elevation-4">
            <v-row class="ga-4 ga-md-0">
                <v-col cols="12" md="8">
                    <TextInput v-model="filters.name" label="Busca" placeholder="Digite o nome do imóvel..." />
                </v-col>
                <v-col cols="12" md="4">
                    <SelectInput v-model="filters.sortByPrice" label="Ordenar Por" placeholder="Menor Preço"
                        :items="orderOptions" />
                </v-col>

                <v-col cols="12" md="3">
                    <TextInput v-model.number="filters.minPrice" label="Preço Min" placeholder="R$ 0,00"
                        type="number" />
                </v-col>
                <v-col cols="12" md="3">
                    <TextInput v-model.number="filters.maxPrice" label="Preço Máx" placeholder="R$ 1.000.000,00"
                        type="number" />
                </v-col>
                <v-col cols="12" md="4">
                    <SelectInput v-model="filters.state" label="Estado" placeholder="Selecione o estado"
                        :items="stateOptions" />
                </v-col>
                <v-col cols="12" md="2" class="d-flex align-end">
                    <Button variant="tertiary" :loading="false" class="w-100 font-weight-bold py-3" size="large"
                        rounded="lg" @click="handleSearch">
                        Buscar
                    </Button>
                </v-col>
            </v-row>
        </v-sheet>
    </div>
</template>

<style scoped>
.search-tab {
    position: relative;
    z-index: 1;
    border-bottom: none;
}

.search-bar-container {
    max-width: 1200px;
    margin: 0 auto;
}

:deep(.v-row) {
    margin: -8px;
}

:deep(.v-col) {
    padding: 8px;
}
</style>