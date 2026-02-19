import type { AxiosResponse } from "axios";
import type { IAvailableState } from "./list-available-states.interface";
import apiClient from "~/api/client";

export async function listAvailableStatesQuery(): Promise<
  AxiosResponse<IAvailableState[]>
> {
  return apiClient.get("/properties/states");
}
