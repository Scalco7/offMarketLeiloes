import { createVuetify } from "vuetify";
import "vuetify/styles";
import "@mdi/font/css/materialdesignicons.css";
import colors from "vuetify/util/colors";

export default defineNuxtPlugin((nuxtApp) => {
  const vuetify = createVuetify({
    ssr: true,
    theme: {
      themes: {
        light: {
          dark: false,
          colors: {
            primary: "#20325C",
          },
        },
      },
    },
  });

  nuxtApp.vueApp.use(vuetify);
});
