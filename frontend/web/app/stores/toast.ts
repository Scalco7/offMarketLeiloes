import { defineStore } from "pinia";

export type ToastType = "success" | "error" | "warning" | "info";

interface ToastState {
  show: boolean;
  message: string;
  type: ToastType;
  timeout: number;
}

export const useToastStore = defineStore("toast", {
  state: (): ToastState => ({
    show: false,
    message: "",
    type: "info",
    timeout: 3000,
  }),
  actions: {
    showToast(
      message: string,
      type: ToastType = "info",
      timeout: number = 3000,
    ) {
      this.message = message;
      this.type = type;
      this.timeout = timeout;
      this.show = true;
    },
    success(message: string, timeout?: number) {
      this.showToast(message, "success", timeout);
    },
    error(message: string, timeout?: number) {
      this.showToast(message, "error", timeout);
    },
    warning(message: string, timeout?: number) {
      this.showToast(message, "warning", timeout);
    },
    info(message: string, timeout?: number) {
      this.showToast(message, "info", timeout);
    },
  },
});
