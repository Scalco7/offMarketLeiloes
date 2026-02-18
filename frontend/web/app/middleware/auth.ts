export default defineNuxtRouteMiddleware((to, from) => {
  const { isLoggedIn } = useAuth();

  if (import.meta.client && !isLoggedIn.value) {
    return navigateTo("/login");
  }
});
