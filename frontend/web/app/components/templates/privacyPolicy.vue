<script setup lang="ts">
import { LucideShieldAlert, LucideLock, LucideFileText, LucideUserCheck } from 'lucide-vue-next'
</script>

<template>
    <div class="privacy-policy-template">
        <section class="hero-section d-flex align-center justify-center text-white">
            <v-container>
                <v-row align="center">
                    <v-col cols="12" md="8" class="text-center text-md-left">
                        <h1 class="text-h3 text-sm-h2 font-weight-bold mb-4 animate-fade-in-up">
                            Política de <span class="text-primary-gradient">Privacidade</span>
                        </h1>
                        <p class="text-h6 mb-6 opacity-80 animate-fade-in-up delay-1">
                            Sua privacidade e a segurança dos seus dados são nossa prioridade máxima na
                            OffMarketLeilões.
                        </p>
                    </v-col>
                </v-row>
            </v-container>
        </section>

        <v-container class="py-16">
            <v-row>
                <v-col cols="12" md="3" class="d-none d-md-block">
                    <v-card variant="outlined" class="pa-4 sticky-top rounded-lg border-opacity-25">
                        <div class="text-subtitle-1 font-weight-bold mb-4 text-primary">Nesta Política</div>
                        <nav class="privacy-nav">
                            <a href="#coleta"
                                class="d-block py-2 text-decoration-none text-body-2 text-medium-emphasis hover-primary">1.
                                Coleta de Dados</a>
                            <a href="#uso"
                                class="d-block py-2 text-decoration-none text-body-2 text-medium-emphasis hover-primary">2.
                                Uso das Informações</a>
                            <a href="#compartilhamento"
                                class="d-block py-2 text-decoration-none text-body-2 text-medium-emphasis hover-primary">3.
                                Compartilhamento</a>
                            <a href="#seguranca"
                                class="d-block py-2 text-decoration-none text-body-2 text-medium-emphasis hover-primary">4.
                                Segurança</a>
                            <a href="#direitos"
                                class="d-block py-2 text-decoration-none text-body-2 text-medium-emphasis hover-primary">5.
                                Seus Direitos</a>
                        </nav>
                    </v-card>
                </v-col>

                <v-col cols="12" md="9" class="pa-0 pa-sm-3">
                    <v-card variant="flat" class="pa-4 pa-sm-8 pb-16 policy-card">
                        <section id="introducao" class="mb-12">
                            <h2 class="text-h4 font-weight-bold mb-4">Introdução</h2>
                            <p class="text-body-1 text-medium-emphasis mb-4">
                                Esta Política de Privacidade descreve como a OffMarketLeilões coleta, utiliza e protege
                                suas informações ao utilizar nossa plataforma. Ao acessar nossos serviços, você concorda
                                com as práticas descritas neste documento.
                            </p>
                            <p class="text-body-2 text-disabled">Última atualização: 19 de Fevereiro de 2026</p>
                        </section>

                        <v-divider class="mb-12 border-opacity-25"></v-divider>

                        <section id="coleta" class="mb-12">
                            <div class="d-flex align-center mb-4">
                                <v-avatar color="primary-lighten-4" size="40" class="mr-3">
                                    <LucideFileText class="text-primary" :size="20" />
                                </v-avatar>
                                <h2 class="text-h5 font-weight-bold">1. Coleta de Dados</h2>
                            </div>
                            <p class="text-body-1 text-medium-emphasis mb-4">
                                Coletamos informações que você nos fornece diretamente ao criar uma conta, como:
                            </p>
                            <ul class="policy-list mb-4 pl-6 text-medium-emphasis">
                                <li class="mb-2">Nome completo e informações de contato;</li>
                                <li class="mb-2">Endereço de e-mail e número de telefone;</li>
                                <li class="mb-2">Informações de perfil e preferências de investimento;</li>
                                <li class="mb-2">Documentação necessária para participação em leilões (quando
                                    aplicável).</li>
                            </ul>
                        </section>

                        <section id="uso" class="mb-12">
                            <div class="d-flex align-center mb-4">
                                <v-avatar color="secondary-lighten-4" size="40" class="mr-3">
                                    <LucideUserCheck class="text-secondary" :size="20" />
                                </v-avatar>
                                <h2 class="text-h5 font-weight-bold">2. Uso das Informações</h2>
                            </div>
                            <p class="text-body-1 text-medium-emphasis mb-4">
                                Utilizamos seus dados para:
                            </p>
                            <ul class="policy-list mb-4 pl-6 text-medium-emphasis">
                                <li class="mb-2">Prover e manter nossos serviços de curadoria;</li>
                                <li class="mb-2">Notificá-lo sobre oportunidades exclusivas (off-market);</li>
                                <li class="mb-2">Processar suas transações e gerenciar sua conta;</li>
                                <li class="mb-2">Melhorar a experiência do usuário e nossa plataforma.</li>
                            </ul>
                        </section>

                        <section id="seguranca" class="mb-12">
                            <div class="d-flex align-center mb-4">
                                <v-avatar color="success-lighten-4" size="40" class="mr-3">
                                    <LucideLock class="text-success" :size="20" />
                                </v-avatar>
                                <h2 class="text-h5 font-weight-bold">3. Segurança</h2>
                            </div>
                            <p class="text-body-1 text-medium-emphasis mb-4">
                                Implementamos medidas de segurança técnicas e organizacionais para proteger seus dados
                                contra acesso não autorizado, alteração ou destruição. Utilizamos criptografia de ponta
                                a ponta e auditorias regulares de segurança.
                            </p>
                        </section>

                        <section id="direitos" class="mb-12">
                            <div class="d-flex align-center mb-4">
                                <v-avatar color="warning-lighten-4" size="40" class="mr-3">
                                    <LucideShieldAlert class="text-warning" :size="20" />
                                </v-avatar>
                                <h2 class="text-h5 font-weight-bold">4. Seus Direitos (LGPD)</h2>
                            </div>
                            <p class="text-body-1 text-medium-emphasis mb-4">
                                Em conformidade com a Lei Geral de Proteção de Dados (LGPD), você tem o direito de:
                            </p>
                            <ul class="policy-list mb-4 pl-6 text-medium-emphasis">
                                <li class="mb-2">Acessar seus dados pessoais;</li>
                                <li class="mb-2">Corrigir dados incompletos ou inexatos;</li>
                                <li class="mb-2">Solicitar a anonimização ou eliminação de dados desnecessários;</li>
                                <li class="mb-2">Revogar o consentimento a qualquer momento.</li>
                            </ul>
                        </section>

                        <v-alert border="start" color="primary" variant="tonal" class="mt-8 rounded-lg">
                            <template v-slot:prepend>
                                <LucideShieldAlert :size="24" class="mr-2" />
                            </template>
                            Para exercer seus direitos ou tirar dúvidas sobre esta política, entre em contato conosco
                            através do e-mail
                            <span
                                class="font-weight-bold text-truncate d-flex w-100">privacidade@offmarketleiloes.com.br.</span>
                        </v-alert>
                    </v-card>
                </v-col>
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

.policy-card {
    line-height: 1.8;
}

.privacy-nav a {
    border-left: 2px solid transparent;
    padding-left: 12px;
}

.privacy-nav a:hover {
    border-left-color: #1867c0;
}

.policy-list {
    list-style-type: disc;
}
</style>
