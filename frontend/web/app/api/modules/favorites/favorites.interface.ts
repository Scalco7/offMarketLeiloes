export interface IFavoriteRequest {
  propertyId: string;
}

export interface IListFavoritesFilters {
  name?: string;
  sortByPrice?: "asc" | "desc";
  page?: number;
  size?: number;
}
