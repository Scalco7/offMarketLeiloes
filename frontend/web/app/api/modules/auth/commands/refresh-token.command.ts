import type { AxiosResponse } from "axios";
import apiClient from "~/api/client";
import type { IAuthResponse } from "../auth.interface";

export interface IRefreshTokenCommandRequest {
  refreshToken: string;
  accountId: string;
}

export const refreshTokenCommand = async (
  payload: IRefreshTokenCommandRequest,
): Promise<AxiosResponse<IAuthResponse>> => {
  return apiClient.post("/auth/refresh-token", payload);
};
