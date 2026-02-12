import type { AxiosResponse } from "axios";
import apiClient from "~/api/client";
import type { IAuthResponse } from "../auth.interface";

export interface IRegisterCommandRequest {
  name: string;
  email: string;
  password: string;
}

export const registerCommand = async (
  payload: IRegisterCommandRequest,
): Promise<AxiosResponse<IAuthResponse>> => {
  return apiClient.post("/accounts/register", payload);
};
