import type { AxiosResponse } from "axios";
import apiClient from "~/api/client";
import type { EPropertyStatus } from "~/utils/enums/propertyStatus.enum";
import type { EPropertyType } from "~/utils/enums/propertyType.enum";
import type { IPageableResponse } from "~/utils/interfaces/pageableResponse";

export interface IListPropertiesQueryRequest {
  page?: number;
  pageSize?: number;
  name?: string;
  minPrice?: number;
  maxPrice?: number;
  state?: string;
  city?: string;
  sortByPrice?: "asc" | "desc";
}

export interface IPropertyAddress {
  zipCode: string;
  city: string;
  state: string;
  street: string;
  number: string;
  neighborhood: string;
}

export interface IPropertyList {
  id: string;
  name: string;
  description: string;
  valuedPrice: number;
  currentPrice: number;
  auctionDateTime: Date;
  auctioneerName: string;
  auctionLink: string;
  imageLink?: string;
  type: EPropertyType;
  status: EPropertyStatus;
  address: IPropertyAddress;
}

export type IListPropertiesResponse = IPageableResponse<IPropertyList>;

export const listPropertiesQuery = async (
  params?: IListPropertiesQueryRequest,
): Promise<AxiosResponse<IListPropertiesResponse>> => {
  return apiClient.get("/properties", { params });
};
