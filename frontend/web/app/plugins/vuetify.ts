import { createVuetify } from "vuetify";
import "vuetify/styles";
import "@mdi/font/css/materialdesignicons.css";

export default defineNuxtPlugin((nuxtApp) => {
  const vuetify = createVuetify({
    ssr: true,
    theme: {
      themes: {
        light: {
          dark: false,
          colors: {
            primary: "#20325C",
            tertiary: "#2563EB",
          },
        },
      },
    },
  });

  nuxtApp.vueApp.use(vuetify);
});
