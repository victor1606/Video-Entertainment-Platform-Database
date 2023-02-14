package commands;

import fileio.ActionInputData;
import fileio.Input;

public final class Command {

    private Command() {
    }

    /**
     * This method calls the appropriate command methods based on the command
     *
     * @param command contains all data of a user's input
     * @param input   contains all data of a test
     */
    public static StringBuilder doCommand(final ActionInputData command, final Input input) {
        switch (command.getType()) {
            case "view":
                return View.viewShow(command, input);

            case "favorite":
                return Favorite.favoriteShow(command, input);

            case "rating":
                return Rate.rateShow(command, input);

            default:
                return null;
        }
    }
}
