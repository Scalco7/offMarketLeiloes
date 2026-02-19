<script setup lang="ts">
import Button from '~/components/atoms/button.vue'
import { LucideTarget, LucideEye, LucideShieldCheck } from 'lucide-vue-next'
</script>

<template>
    <div class="about-template">
        <section class="hero-section d-flex align-center justify-center text-white">
            <v-container>
                <v-row align="center">
                    <v-col cols="12" md="7" class="text-center text-md-left">
                        <h1 class="text-h2 font-weight-bold mb-4 animate-fade-in-up">
                            Inovando o Mercado de <span class="text-primary-gradient">Leilões Off-Market</span>
                        </h1>
                        <p class="text-h6 mb-6 opacity-80 animate-fade-in-up delay-1">
                            A OffMarketLeilões é a plataforma líder em curadoria de oportunidades imobiliárias
                            exclusivas, conectando investidores a negócios de alto potencial antes mesmo de chegarem ao
                            grande público.
                        </p>
                        <div class="animate-fade-in-up delay-2">
                            <Button variant="primary" :loading="false" size="large" @click="navigateTo('/')">
                                Ver Oportunidades
                            </Button>
                        </div>
                    </v-col>
                </v-row>
            </v-container>
        </section>

        <v-container class="py-16">
            <v-row>
                <v-col cols="12" class="text-center mb-10">
                    <h2 class="text-h3 font-weight-bold mb-2">Nossa Essência</h2>
                    <div class="divider mx-auto mb-6"></div>
                </v-col>

                <v-col cols="12" md="4">
                    <v-card variant="flat" class="pa-6 h-100 value-card">
                        <v-avatar color="primary-lighten-4" size="64" class="mb-4">
                            <LucideTarget class="text-primary" :size="32" />
                        </v-avatar>
                        <h3 class="text-h5 font-weight-bold mb-3">Missão</h3>
                        <p class="text-body-1 text-medium-emphasis">
                            Democratizar o acesso a leilões de alta rentabilidade através de tecnologia transparente e
                            curadoria especializada.
                        </p>
                    </v-card>
                </v-col>

                <v-col cols="12" md="4">
                    <v-card variant="flat" class="pa-6 h-100 value-card">
                        <v-avatar color="secondary-lighten-4" size="64" class="mb-4">
                            <LucideEye class="text-secondary" :size="32" />
                        </v-avatar>
                        <h3 class="text-h5 font-weight-bold mb-3">Visão</h3>
                        <p class="text-body-1 text-medium-emphasis">
                            Ser a referência global em inteligência de mercado para ativos imobiliários sob demanda e
                            execuções extrajudiciais.
                        </p>
                    </v-card>
                </v-col>

                <v-col cols="12" md="4">
                    <v-card variant="flat" class="pa-6 h-100 value-card">
                        <v-avatar color="success-lighten-4" size="64" class="mb-4">
                            <LucideShieldCheck class="text-success" :size="32" />
                        </v-avatar>
                        <h3 class="text-h5 font-weight-bold mb-3">Valores</h3>
                        <p class="text-body-1 text-medium-emphasis">
                            Ética inegociável, inovação constante e foco absoluto no sucesso do arrematante.
                        </p>
                    </v-card>
                </v-col>
            </v-row>
        </v-container>

        <!-- Stats Section -->
        <section class="stats-section py-16 bg-grey-lighten-4">
            <v-container>
                <v-row class="text-center">
                    <v-col cols="12" sm="4">
                        <div class="text-h2 font-weight-bold text-primary mb-2">+R$ 500M</div>
                        <div class="text-subtitle-1 text-uppercase font-weight-medium">Em Ativos Arrematados</div>
                    </v-col>
                    <v-col cols="12" sm="4">
                        <div class="text-h2 font-weight-bold text-primary mb-2">15k</div>
                        <div class="text-subtitle-1 text-uppercase font-weight-medium">Investidores Ativos</div>
                    </v-col>
                    <v-col cols="12" sm="4">
                        <div class="text-h2 font-weight-bold text-primary mb-2">98%</div>
                        <div class="text-subtitle-1 text-uppercase font-weight-medium">Taxa de Satisfação</div>
                    </v-col>
                </v-row>
            </v-container>
        </section>

        <v-container class="py-16">
            <v-row align="center">
                <v-col cols="12" md="6">
                    <v-img src="~/assets/leilao.png" cover class="rounded-xl elevation-10 hero-img-mask overflow-hidden"
                        height="450"></v-img>
                </v-col>
                <v-col cols="12" md="6" class="pl-md-10">
                    <h2 class="text-h3 font-weight-bold mb-6">Por que a OffMarketLeilões?</h2>
                    <ul class="feature-list px-0">
                        <li class="d-flex align-start mb-4">
                            <v-icon color="primary" class="mr-3 mt-1">mdi-check-circle</v-icon>
                            <div>
                                <span class="font-weight-bold">Oportunidades Inéditas:</span>
                                Acessamos dados de fontes oficiais e extraoficiais para trazer imóveis antes do mercado.
                            </div>
                        </li>
                        <li class="d-flex align-start mb-4">
                            <v-icon color="primary" class="mr-3 mt-1">mdi-check-circle</v-icon>
                            <div>
                                <span class="font-weight-bold">Transparência Total:</span>
                                Relatórios detalhados de viabilidade e riscos para cada ativo listado.
                            </div>
                        </li>
                        <li class="d-flex align-start mb-4">
                            <v-icon color="primary" class="mr-3 mt-1">mdi-check-circle</v-icon>
                            <div>
                                <span class="font-weight-bold">Suporte Jurídico:</span>
                                Parceria com os melhores escritórios para garantir segurança na posse do imóvel.
                            </div>
                        </li>
                    </ul>
                </v-col>
            </v-row>
        </v-container>
    </div>
</template>

<style scoped>
.hero-section {
    min-height: 80vh;
    background: linear-gradient(rgba(0, 0, 0, 0.6), rgba(0, 0, 0, 0.6)), url('~/assets/leilao.png');
    background-size: cover;
    background-position: center;
}

.hero-img-mask {
    border-radius: 30% 70% 70% 30% / 30% 30% 70% 70%;
}

.opacity-80 {
    opacity: 0.8;
}

.feature-list {
    list-style: none;
}
</style>
