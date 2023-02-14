package commands;

import fileio.ActionInputData;
import fileio.Input;
import fileio.ShowInput;
import fileio.UserInputData;
import utils.Utils;

import java.util.Map;
import java.util.Objects;

public final class View {

    private View() {
    }

    /**
     * Returns output message after processing "view" command.
     * This method checks the user's history for the title entry.
     * If found, the view count for the title is incremented.
     * If not, the method adds the title to the viewer's history.
     *
     * @param  command contains all data of a user's input
     * @param  input contains all data of a test
     */
    public static StringBuilder viewShow(final ActionInputData command, final Input input) {
        UserInputData user = Utils.findUser(command, input);
        ShowInput show = Utils.findMovie(command, input);

        if (show == null) {
            show = Utils.findSerial(command, input);
        }

        Map<String, Integer> history = Objects.requireNonNull(user).getHistory();

        if (history.containsKey(Objects.requireNonNull(show).getTitle())) {
            int watchCount = history.get(show.getTitle());
            history.replace(show.getTitle(), watchCount + 1);
        } else {
            history.put(show.getTitle(), 1);
        }

        StringBuilder output = new StringBuilder();
        output.append("success -> ");
        output.append(show.getTitle());
        output.append(" was viewed with total views of ");
        output.append(user.getHistory().get(show.getTitle()));

        return output;
    }
}
