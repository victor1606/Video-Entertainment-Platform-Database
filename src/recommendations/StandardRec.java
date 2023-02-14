package recommendations;

import fileio.ActionInputData;
import fileio.SerialInputData;
import fileio.MovieInputData;
import fileio.UserInputData;
import fileio.Input;

import utils.Utils;


public final class StandardRec {

    private StandardRec() {
    }

    /**
     * This method obtains a standard recommendation.
     * The method finds the first video from the database not present
     * in the user's history.
     *
     * @param command contains all data of a user's input
     * @param input   contains all data of a test
     * @param output  contains the output StringBuilder
     */
    public static StringBuilder doStandardRecommendation(final ActionInputData command,
                                                         final Input input,
                                                         final StringBuilder output) {

        UserInputData user = Utils.findUser(command, input);

        for (MovieInputData movie : input.getMovies()) {
            if (user != null) {
                if (!user.getHistory().containsKey(movie.getTitle())) {
                    output.append("StandardRecommendation result: ");
                    output.append(movie.getTitle());
                    return output;
                }
            }
        }

        for (SerialInputData series : input.getSerials()) {
            if (user != null) {
                if (!user.getHistory().containsKey(series.getTitle())) {
                    output.append("StandardRecommendation result: ");
                    output.append(series.getTitle());
                    return output;
                }
            }
        }
        output.append("StandardRecommendation cannot be applied!");
        return output;
    }
}
