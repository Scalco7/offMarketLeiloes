import type { AxiosResponse } from "axios";
import apiClient from "~/api/client";
import type { IAuthResponse } from "../auth.interface";

export interface IRefreshTokenCommandRequest {
  refreshToken: string;
  accountId: string;
}

export async function refreshTokenCommand(
  payload: IRefreshTokenCommandRequest,
): Promise<AxiosResponse<IAuthResponse>> {
  return apiClient.post("/auth/refresh-token", payload);
}
