import { listPropertiesQuery } from "./queries/list-properties/list-properties.query";

export const propertyActions = {
  commands: {},
  queries: {
    list: listPropertiesQuery,
  },
};
