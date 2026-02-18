import type { AxiosResponse } from "axios";
import apiClient from "~/api/client";
import type {
  IListPropertiesQueryRequest,
  IListPropertiesResponse,
  IPropertyList,
} from "./list-properties.interface";

export async function listPropertiesQuery(
  params?: IListPropertiesQueryRequest,
): Promise<AxiosResponse<IListPropertiesResponse>> {
  return apiClient.get("/properties", { params }).then((response) => {
    response.data.content = response.data.content.map(
      (property: IPropertyList) => {
        property.auctionDateTime = new Date(property.auctionDateTime);
        return property;
      },
    );
    return response;
  });
}
