package commands;

import entertainment.Season;

import fileio.ActionInputData;
import fileio.Input;
import fileio.ShowInput;
import fileio.MovieInputData;
import fileio.UserInputData;
import utils.Utils;

import java.util.Objects;

public final class Rate {

    private Rate() {
    }

    /**
     * Returns output message after processing "rate" command.
     * This method checks if a title has been watched/already rated.
     * If not, the method adds the rating to the title's ratings list.
     *
     * @param command contains all data of a user's input
     * @param input   contains all data of a test
     */
    public static StringBuilder rateShow(final ActionInputData command, final Input input) {
        UserInputData user = Utils.findUser(command, input);
        ShowInput show = Utils.findMovie(command, input);
        StringBuilder output = new StringBuilder();

        if (Objects.requireNonNull(user).getRatedMovies().contains(command.getTitle())
                || user.getRatedMovies().contains(command.getTitle()
                + " " + command.getSeasonNumber())) {
            output.append("error -> ");
            output.append(command.getTitle());
            output.append(" has been already rated");

            return output;
        }

        if (!user.getHistory().containsKey(command.getTitle())) {
            output.append("error -> ");
            output.append(command.getTitle());
            output.append(" is not seen");

            return output;
        }

        if (show != null) {
            ((MovieInputData) show).getRatings().add(command.getGrade());
            show.setAverage();
        } else {
            show = Utils.findSerial(command, input);
            Season season = Utils.findSeason(command, show);

            Objects.requireNonNull(season).getRatings().add(command.getGrade());
            season.setAverage();

            Objects.requireNonNull(show).setAverage();
        }

        String ratedTitle = command.getTitle();
        if (command.getSeasonNumber() != 0) {
            ratedTitle = command.getTitle() + " " + command.getSeasonNumber();
        }

        user.getRatedMovies().add(ratedTitle);

        output.append("success -> ");
        output.append(command.getTitle());
        output.append(" was rated with ");
        output.append(command.getGrade());
        output.append(" by ");
        output.append(user.getUsername());

        return output;
    }
}
