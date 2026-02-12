import { defineNuxtPlugin } from "#app";
import API from "../api";

export default defineNuxtPlugin((nuxtApp) => {
  // Inject the API into the context as $api
  // This allows usage like const { $api } = useNuxtApp()
  return {
    provide: {
      api: API,
    },
  };
});
