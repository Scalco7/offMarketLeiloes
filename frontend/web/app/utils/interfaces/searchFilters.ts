export interface ISearchFilters {
  name?: string;
  sortByPrice?: "asc" | "desc";
  minPrice?: number;
  maxPrice?: number;
  state?: string;
}
