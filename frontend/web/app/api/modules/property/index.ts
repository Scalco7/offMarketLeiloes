import { listAvailableStatesQuery } from "./queries/list-available-states/list-available-states.query";
import { listPropertiesQuery } from "./queries/list-properties/list-properties.query";

export const propertyActions = {
  commands: {},
  queries: {
    list: listPropertiesQuery,
    listAvailableStates: listAvailableStatesQuery,
  },
};
