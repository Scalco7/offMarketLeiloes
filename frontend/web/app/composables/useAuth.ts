import { jwtDecode, type JwtPayload } from "jwt-decode";
import type { ILoginCommandRequest } from "~/api/modules/auth/commands/login.command";
import type { IRegisterCommandRequest } from "~/api/modules/auth/commands/register.command";

interface ITokenPayload extends JwtPayload {
  accountId: string;
}

export const useAuth = () => {
  const { $api } = useNuxtApp();
  const accessToken = useState<string | null>("accessToken", () => null);
  const refreshToken = useState<string | null>("refreshToken", () => null);
  const accountId = useState<string | null>("accountId", () => null);

  if (import.meta.client) {
    accessToken.value = localStorage.getItem("accessToken");
    refreshToken.value = localStorage.getItem("refreshToken");
    accountId.value = localStorage.getItem("accountId");
  }

  const isLoggedIn = computed(() => !!accessToken.value);

  async function login(credentials: ILoginCommandRequest) {
    try {
      const response = await $api.auth.commands.login(credentials);
      const { data } = response;

      accessToken.value = data.accessToken;
      refreshToken.value = data.refreshToken;

      const dataAccountId = getAccountId(data.accessToken);
      accountId.value = dataAccountId;

      if (import.meta.client) {
        localStorage.setItem("accessToken", data.accessToken);
        localStorage.setItem("refreshToken", data.refreshToken);
        localStorage.setItem("accountId", dataAccountId);
      }

      return response;
    } catch (error) {
      logout();
      throw error;
    }
  }

  async function register(userData: IRegisterCommandRequest) {
    try {
      const response = await $api.auth.commands.register(userData);
      const { data } = response;

      accessToken.value = data.accessToken;
      refreshToken.value = data.refreshToken;

      const dataAccountId = getAccountId(data.accessToken);
      accountId.value = dataAccountId;

      if (import.meta.client) {
        localStorage.setItem("accessToken", data.accessToken);
        localStorage.setItem("refreshToken", data.refreshToken);
        localStorage.setItem("accountId", dataAccountId);
      }

      return response;
    } catch (error) {
      throw error;
    }
  }

  function logout() {
    accessToken.value = null;
    refreshToken.value = null;
    accountId.value = null;

    if (import.meta.client) {
      localStorage.removeItem("accessToken");
      localStorage.removeItem("refreshToken");
      localStorage.removeItem("accountId");
    }
  }

  function getAccountId(accessToken: string): string {
    const decodedToken = jwtDecode<ITokenPayload>(accessToken);
    return decodedToken.accountId;
  }

  return {
    accessToken,
    refreshToken,
    accountId,
    isLoggedIn,
    login,
    register,
    logout,
  };
};
