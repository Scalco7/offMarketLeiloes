<script setup lang="ts">
import Button from '../atoms/button.vue';

interface IEmptyStateProps {
    buttonText: string
}

const props = defineProps<IEmptyStateProps>()
defineEmits(['onClickEmptyAction'])
</script>

<template>
    <v-col class="d-flex flex-column align-center justify-center pb-16 pt-6 ga-6 animate-fade-in-up">
        <div class="icon-container">
            <div class="blob-bg"></div>
            <LucideSearchX :size="120" stroke-width="1.5" class="text-primary-gradient-icon relative-icon" />
        </div>

        <div class="text-center ga-2 d-flex flex-column">
            <h3 class="text-h4 font-weight-bold grey-darken-4">Nenhuma propriedade encontrada</h3>
            <p class="text-body-1 text-grey-darken-1 max-width-text">
                Não encontramos nenhuma propriedade que corresponda aos seus critérios no momento.
                Tente ajustar os filtros ou volte mais tarde para conferir as novidades.
            </p>
        </div>

        <Button @click="$emit('onClickEmptyAction')" :loading="false" variant="primary">
            {{ buttonText }}
        </Button>
    </v-col>
</template>

<style scoped>
.icon-container {
    position: relative;
    display: flex;
    align-items: center;
    justify-content: center;
    width: 200px;
    height: 200px;
}

.blob-bg {
    position: absolute;
    width: 150px;
    height: 150px;
    background: linear-gradient(135deg, rgba(24, 103, 192, 0.1) 0%, rgba(92, 187, 246, 0.1) 100%);
    border-radius: 30% 70% 70% 30% / 30% 30% 70% 70%;
    animation: blob-morph 8s ease-in-out infinite alternate;
}

.text-primary-gradient-icon {
    color: #1867c0;
    filter: drop-shadow(0 10px 15px rgba(24, 103, 192, 0.2));
}

.relative-icon {
    position: relative;
    z-index: 2;
}

.max-width-text {
    max-width: 450px;
    margin: 0 auto;
}

@keyframes blob-morph {
    0% {
        border-radius: 30% 70% 70% 30% / 30% 30% 70% 70%;
    }

    100% {
        border-radius: 60% 40% 30% 70% / 60% 30% 70% 40%;
    }
}

.animate-fade-in-up {
    animation: fadeInUp 0.8s ease-out forwards;
}

@keyframes fadeInUp {
    from {
        opacity: 0;
        transform: translateY(20px);
    }

    to {
        opacity: 1;
        transform: translateY(0);
    }
}
</style>
