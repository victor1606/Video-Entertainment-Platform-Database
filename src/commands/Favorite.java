package commands;

import fileio.ActionInputData;
import fileio.Input;
import fileio.UserInputData;
import utils.Utils;

import java.util.Objects;

public final class Favorite {

    private Favorite() {
    }

    /**
     * Returns output message after processing "favorite" command.
     * This method checks if a title is already watched/added to favorites.
     * If not, the method adds the title to the favorites list.
     *
     * @param command contains all data of a user's input
     * @param input   contains all data of a test
     */
    public static StringBuilder favoriteShow(final ActionInputData command, final Input input) {
        UserInputData user = Utils.findUser(command, input);
        StringBuilder output = new StringBuilder();

        if (Objects.requireNonNull(user).getFavoriteMovies().contains(command.getTitle())) {
            output.append("error -> ");
            output.append(command.getTitle());
            output.append(" is already in favourite list");
            return output;
        }

        if (user.getHistory().containsKey(command.getTitle())) {
            user.getFavoriteMovies().add(command.getTitle());
        } else {
            output.append("error -> ");
            output.append(command.getTitle());
            output.append(" is not seen");
            return output;
        }

        output.append("success -> ");
        output.append(command.getTitle());
        output.append(" was added as favourite");

        return output;
    }
}
