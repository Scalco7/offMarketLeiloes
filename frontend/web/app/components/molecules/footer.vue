<script setup lang="ts">
import Button from '../atoms/button.vue';

const institutionalLinks = [
    { text: 'Quem Somos', to: '/about' },
    { text: 'Contato', to: '/contacts' },
    { text: 'Política de Privacidade', to: '/privacy-policy' },
];

const propertyLinks = [
    { text: 'Encontre seu imóvel', to: '/' },
    { text: 'Imóveis Caixa', to: 'https://venda-imoveis.caixa.gov.br/sistema/busca-imovel.asp?sltTipoBusca=imoveis', target: '_blank' },
    { text: 'Simulador de Financiamento', to: 'https://www8.caixa.gov.br/siopiinternet-web/simulaOperacaoInternet.do?method=inicializarCasoUso', target: '_blank' },
];
</script>

<template>
    <footer class="bg-primary py-12 mt-auto">
        <v-container>
            <v-row class="d-flex flex-row">
                <v-col cols="12" md="3">
                    <v-img src="~/assets/logo.png" alt="Off Market Leilões" width="220" class="mb-6" />
                    <p class="text-white text-body-2 opacity-90" style="line-height: 1.6; max-width: 200px;">
                        Referência em leilões judiciais e extrajudiciais. Segurança, transparência e as melhores
                        oportunidades de
                        investimento em um só lugar.
                    </p>
                </v-col>

                <v-row class="mt-16" cols="12" md="9">
                    <v-col cols="12" md="3">
                        <h3 class="text-tertiary text-h8 font-weight-bold mb-2">Institucional</h3>
                        <div class="d-flex flex-column ga-1">
                            <NuxtLink v-for="link in institutionalLinks" :key="link.text" :to="link.to"
                                class="text-white text-decoration-none text-body-2 v-btn--variant-plain opacity-90">
                                {{ link.text }}
                            </NuxtLink>
                        </div>
                    </v-col>

                    <v-col cols="12" md="3">
                        <h3 class="text-tertiary text-h8 font-weight-bold mb-2">Imóveis</h3>
                        <div class="d-flex flex-column ga-1">
                            <NuxtLink v-for="link in propertyLinks" :key="link.text" :to="link.to" :target="link.target"
                                :external="!!link.target"
                                class="text-white text-decoration-none text-body-2 v-btn--variant-plain opacity-90">
                                {{ link.text }}
                            </NuxtLink>
                        </div>
                    </v-col>

                    <v-col cols="12" md="5">
                        <h3 class="text-tertiary text-h8 font-weight-bold mb-2">Acompanhe as Novidades</h3>
                        <p class="text-white text-body-2 mb-6">
                            Fique por dentro das nossas novidades e promoções
                        </p>
                    </v-col>
                </v-row>
            </v-row>
        </v-container>
    </footer>
</template>

<style scoped></style>
