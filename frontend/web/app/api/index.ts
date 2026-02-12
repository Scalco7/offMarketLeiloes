import { authActions } from "./modules/auth";
import { propertyActions } from "./modules/property";

export const API = {
  auth: authActions,
  property: propertyActions,
};

export default API;
