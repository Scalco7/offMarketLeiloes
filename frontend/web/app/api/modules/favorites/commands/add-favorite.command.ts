import type { AxiosResponse } from "axios";
import apiClient from "~/api/client";
import type { IFavoriteRequest } from "../favorites.interface";

export async function addFavoriteCommand(
  data: IFavoriteRequest,
): Promise<AxiosResponse<void>> {
  return apiClient.post("/favorites", data);
}
