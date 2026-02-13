import type { AxiosResponse } from "axios";
import apiClient from "~/api/client";
import type { IListFavoritesFilters } from "../favorites.interface";
import type {
  IListPropertiesResponse,
  IPropertyList,
} from "../../property/queries/list-properties.query";

export async function listFavoritesQuery(
  params?: IListFavoritesFilters,
): Promise<AxiosResponse<IListPropertiesResponse>> {
  return apiClient.get("/favorites", { params }).then((response) => {
    response.data.content = response.data.content.map(
      (property: IPropertyList) => {
        property.auctionDateTime = new Date(property.auctionDateTime);
        return property;
      },
    );
    return response;
  });
}
