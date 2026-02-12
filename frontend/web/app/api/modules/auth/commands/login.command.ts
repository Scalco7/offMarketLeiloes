import type { AxiosResponse } from "axios";
import apiClient from "~/api/client";
import type { IAuthResponse } from "../auth.interface";

export interface ILoginCommandRequest {
  email: string;
  password: string;
}

export const loginCommand = async (
  payload: ILoginCommandRequest,
): Promise<AxiosResponse<IAuthResponse>> => {
  return apiClient.post("/auth/login", payload);
};
