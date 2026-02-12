import { loginCommand } from "./commands/login.command";
import { registerCommand } from "./commands/register.command";
import { refreshTokenCommand } from "./commands/refresh-token.command";

export const authActions = {
  commands: {
    login: loginCommand,
    register: registerCommand,
    refreshToken: refreshTokenCommand,
  },
  queries: {},
};
