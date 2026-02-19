<script setup lang="ts">
import { LucideMail, LucidePhone, LucideMapPin, LucideClock } from 'lucide-vue-next'

const form = ref({
    name: '',
    email: '',
    subject: '',
    message: ''
})

const loading = ref(false)

function handleSubmit() {
    loading.value = true
    // Simulate API call
    setTimeout(() => {
        loading.value = false
        alert('Mensagem enviada com sucesso!')
        form.value = {
            name: '',
            email: '',
            subject: '',
            message: ''
        }
    }, 1500)
}
</script>

<template>
    <div class="contact-template">
        <section class="hero-section d-flex align-center justify-center text-white">
            <v-container>
                <v-row align="center" justify="center">
                    <v-col cols="12" md="8" class="text-center">
                        <h1 class="text-h2 font-weight-bold mb-4 animate-fade-in-up">
                            Entre em <span class="text-primary-gradient">Contato</span>
                        </h1>
                        <p class="text-h6 mb-6 opacity-80 animate-fade-in-up delay-1">
                            Estamos aqui para ajudar você a encontrar as melhores oportunidades e tirar todas as suas
                            dúvidas sobre leilões off-market.
                        </p>
                    </v-col>
                </v-row>
            </v-container>
        </section>

        <v-container class="py-16">
            <v-row justify="space-between">
                <v-col cols="12" md="5" class="pr-md-10">
                    <h2 class="text-h4 font-weight-bold mb-6">Informações de Contato</h2>
                    <p class="text-body-1 text-medium-emphasis mb-10">
                        Escolha o canal de sua preferência. Nossa equipe de especialistas está pronta para oferecer
                        um
                        atendimento personalizado e eficiente.
                    </p>

                    <div>
                        <div class="d-flex align-center mb-2 info-item">
                            <v-avatar color="primary-lighten-4" size="56" class="mr-4">
                                <LucidePhone class="text-primary" :size="24" />
                            </v-avatar>
                            <div>
                                <div class="text-subtitle-2 font-weight-bold text-primary text-uppercase">Telefone
                                </div>
                                <div class="font-weight-medium">(11) 4003-0000</div>
                            </div>
                        </div>

                        <div class="d-flex align-center mb-2 info-item">
                            <v-avatar color="primary-lighten-4" size="56" class="mr-4">
                                <LucideMail class="text-primary" :size="24" />
                            </v-avatar>
                            <div>
                                <div class="text-subtitle-2 font-weight-bold text-primary text-uppercase">E-mail
                                </div>
                                <div class="font-weight-medium">contato@offmarketleiloes.com.br</div>
                            </div>
                        </div>

                        <div class="d-flex align-center mb-2 info-item">
                            <v-avatar color="primary-lighten-4" size="56" class="mr-4">
                                <LucideMapPin class="text-primary" :size="24" />
                            </v-avatar>
                            <div>
                                <div class="text-subtitle-2 font-weight-bold text-primary text-uppercase">Endereço
                                </div>
                                <div class="font-weight-medium">Av. Paulista, 1000 - Bela Vista, São Paulo - SP
                                </div>
                            </div>
                        </div>

                        <div class="d-flex align-center info-item">
                            <v-avatar color="primary-lighten-4" size="56" class="mr-4">
                                <LucideClock class="text-primary" :size="24" />
                            </v-avatar>
                            <div>
                                <div class="text-subtitle-2 font-weight-bold text-primary text-uppercase">Horário
                                </div>
                                <div class="font-weight-medium">Segunda a Sexta, das 09h às 18h</div>
                            </div>
                        </div>
                    </div>
                </v-col>
                <div class="map-section">
                    <div class="d-flex align-center justify-center">
                        <v-card variant="outlined" class="pa-10 text-center rounded-xl bg-white elevation-5 mx-4"
                            max-width="500">
                            <LucideMapPin class="text-primary mb-4 mx-auto" :size="48" />
                            <h3 class="text-h5 font-weight-bold mb-2">Localização Estratégica</h3>
                            <p class="text-body-1 text-medium-emphasis">
                                Estamos localizados no coração financeiro de São Paulo, facilitando o acesso e a conexão
                                com
                                os
                                principais arrematantes e leiloeiros.
                            </p>
                        </v-card>
                    </div>
                </div>
            </v-row>
        </v-container>
    </div>
</template>

<style scoped>
.hero-section {
    background: linear-gradient(rgba(0, 0, 0, 0.7), rgba(0, 0, 0, 0.7)), url('~/assets/leilao.png');
    background-size: cover;
    background-position: center;
}

.map-section {
    width: 50%;
    height: 400px;
    display: flex;
    align-items: center;
    justify-content: center;
}
</style>
