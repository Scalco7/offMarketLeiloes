import { listPropertiesQuery } from "./queries/list-properties.query";

export const propertyActions = {
  commands: {},
  queries: {
    list: listPropertiesQuery,
  },
};
