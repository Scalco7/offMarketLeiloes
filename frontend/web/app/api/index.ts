import { authActions } from "./modules/auth";
import { propertyActions } from "./modules/property";
import { favoriteActions } from "./modules/favorites";

export const API = {
  auth: authActions,
  property: propertyActions,
  favorites: favoriteActions,
};

export default API;
