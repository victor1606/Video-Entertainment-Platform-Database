package recommendations;

import fileio.ActionInputData;
import fileio.SerialInputData;
import fileio.MovieInputData;
import fileio.UserInputData;
import fileio.ShowInput;
import fileio.Input;

import utils.MultiComparator;
import utils.Utils;

import java.util.LinkedHashMap;
import java.util.Map;


public final class BestUnseenRec {

    private BestUnseenRec() {
    }

    /**
     * This method obtains a best unseen video recommendation.
     * The method finds the highest rated video from the database
     * not present in the user's history.
     *
     * @param command contains all data of a user's input
     * @param input   contains all data of a test
     * @param output  contains the output StringBuilder
     */
    public static StringBuilder doBestUnseenRecommendation(final ActionInputData command,
                                                           final Input input,
                                                           final StringBuilder output) {

        Map<ShowInput, Double> allVideos = new LinkedHashMap<>();
        Map<ShowInput, Double> sortedVideos;

        UserInputData user = Utils.findUser(command, input);

        for (MovieInputData movie : input.getMovies()) {
            allVideos.put(movie, movie.getAverage());
        }

        for (SerialInputData series : input.getSerials()) {
            allVideos.put(series, series.getAverage());
        }

        sortedVideos = MultiComparator.sortAllVideosByRating(allVideos, "desc");

        for (Map.Entry<ShowInput, Double> entry : sortedVideos.entrySet()) {
            if (user != null) {
                if (!user.getHistory().containsKey(entry.getKey().getTitle())) {
                    output.append("BestRatedUnseenRecommendation result: ");
                    output.append(entry.getKey().getTitle());
                    return output;
                }
            }
        }
        output.append("BestRatedUnseenRecommendation cannot be applied!");
        return output;
    }
}
