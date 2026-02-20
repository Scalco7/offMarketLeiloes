import axios from "axios";
import { useToastStore } from "~/stores/toast";
import type { IStandardError } from "~/utils/interfaces/standardError";

const apiClient = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL,
  headers: {
    "Content-Type": "application/json",
    Accept: "application/json",
  },
});

let isRefreshing = false;
let failedQueue: any[] = [];

const processQueue = (error: any, token: string | null = null) => {
  failedQueue.forEach((prom) => {
    if (error) {
      prom.reject(error);
    } else {
      prom.resolve(token);
    }
  });

  failedQueue = [];
};

apiClient.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("accessToken");
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  },
);

apiClient.interceptors.response.use(
  (response) => {
    return response;
  },
  async (error) => {
    const originalRequest = error.config;
    const toast = useToastStore();

    const isAuthRequest =
      originalRequest.url?.includes("/auth/login") ||
      originalRequest.url?.includes("/auth/refresh-token");

    if (
      error.response?.status === 401 &&
      !originalRequest._retry &&
      !isAuthRequest
    ) {
      if (isRefreshing) {
        return new Promise((resolve, reject) => {
          failedQueue.push({ resolve, reject });
        })
          .then((token) => {
            originalRequest.headers.Authorization = `Bearer ${token}`;
            return apiClient(originalRequest);
          })
          .catch((err) => {
            return Promise.reject(err);
          });
      }

      originalRequest._retry = true;
      isRefreshing = true;

      const refreshToken = localStorage.getItem("refreshToken");
      const accountId = localStorage.getItem("accountId");

      if (!refreshToken) {
        isRefreshing = false;
        toast.error("Sessão expirada. Faça login novamente.");
        return Promise.reject(error);
      }

      try {
        const { data } = await axios.post(
          `${apiClient.defaults.baseURL}/auth/refresh-token`,
          {
            refreshToken,
            accountId,
          },
        );

        const { accessToken: newAccessToken, refreshToken: newRefreshToken } =
          data;

        localStorage.setItem("accessToken", newAccessToken);
        localStorage.setItem("refreshToken", newRefreshToken);

        apiClient.defaults.headers.common.Authorization = `Bearer ${newAccessToken}`;
        originalRequest.headers.Authorization = `Bearer ${newAccessToken}`;

        processQueue(null, newAccessToken);
        return apiClient(originalRequest);
      } catch (refreshError) {
        processQueue(refreshError, null);
        localStorage.removeItem("accessToken");
        localStorage.removeItem("refreshToken");
        localStorage.removeItem("accountId");
        return Promise.reject(refreshError);
      } finally {
        isRefreshing = false;
      }
    }

    if (import.meta.client) {
      if (error.response) {
        // Erro retornado pelo servidor (4xx, 5xx)
        // Se for 401 e não for um retry, o código acima já tratou (ou tentou tratar)
        // EXCETO se for uma requisição de auth, que deve mostrar o erro
        if (
          error.response.status !== 401 ||
          originalRequest._retry ||
          isAuthRequest
        ) {
          let message = "Ocorreu um erro no servidor";
          let errorType = "Error";

          if (error.response.data && typeof error.response.data === "object") {
            const errorData = error.response.data as IStandardError;
            message = errorData.message || message;
            errorType = errorData.error || errorType;

            console.error(
              `[API Error] ${error.response.status} ${errorType}: ${message}`,
              {
                path: errorData.path,
                timestamp: errorData.timestamp,
                status: errorData.status,
                fullError: error.response.data,
              },
            );
          } else {
            message = error.message || message;
            console.error(`[API Error] ${error.response.status}: ${message}`);
          }

          toast.error(message);
        }
      } else if (error.request) {
        console.error("[API Error] No response received:", error.request);
        toast.error(
          "Não foi possível conectar ao servidor. Verifique sua conexão.",
        );
      } else {
        console.error("[API Error] Setup error:", error.message);
        toast.error("Ocorreu um erro interno ao processar a requisição.");
      }
    }

    return Promise.reject(error);
  },
);

export default apiClient;
