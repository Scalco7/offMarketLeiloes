import type { AxiosResponse } from "axios";
import apiClient from "~/api/client";
import type { IFavoriteRequest } from "../favorites.interface";

export async function removeFavoriteCommand(
  data: IFavoriteRequest,
): Promise<AxiosResponse<void>> {
  return apiClient.delete("/favorites", { data });
}
