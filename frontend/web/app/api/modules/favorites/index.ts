import { addFavoriteCommand } from "./commands/add-favorite.command";
import { removeFavoriteCommand } from "./commands/remove-favorite.command";
import { listFavoritesQuery } from "./queries/list-favorites/list-favorites.query";

export const favoriteActions = {
  commands: {
    add: addFavoriteCommand,
    remove: removeFavoriteCommand,
  },
  queries: {
    list: listFavoritesQuery,
  },
};
