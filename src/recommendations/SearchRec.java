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


public final class SearchRec {

    private SearchRec() {
    }

    /**
     * This method obtains a search recommendation.
     * The method finds the every video from the database not present
     * in the user's history that also has every genre present in the command
     * input. A sorted list is made containing all videos based on their rating.
     * If a video's genre list fails to contain every genre, the video is removed.
     *
     * @param command contains all data of a user's input
     * @param input   contains all data of a test
     * @param output  contains the output StringBuilder
     */
    public static StringBuilder doSearchRecommendation(final ActionInputData command,
                                                       final Input input,
                                                       final StringBuilder output) {

        UserInputData user = Utils.findUser(command, input);
        if (user == null) {
            return output;
        }

        if (user.getSubscriptionType().equals("BASIC")) {
            output.append("SearchRecommendation cannot be applied!");
            return output;
        }

        Map<ShowInput, Double> allVideos = new LinkedHashMap<>();

        for (MovieInputData movie : input.getMovies()) {
            allVideos.put(movie, movie.getAverage());
        }

        for (SerialInputData series : input.getSerials()) {
            allVideos.put(series, series.getAverage());
        }

        Map<ShowInput, Double> sortedVideos = MultiComparator.
                sortAllVideosByRating(allVideos, "asc");
        sortedVideos = MultiComparator.sortAllVideosAlpha(sortedVideos, "asc");

        sortedVideos.keySet().removeIf(
                key -> !key.getGenres().contains(command.getGenre()));
        sortedVideos.keySet().removeIf(
                key -> user.getHistory().containsKey(key.getTitle()));

        if (sortedVideos.isEmpty()) {
            output.append("SearchRecommendation cannot be applied!");
            return output;
        }

        output.append("SearchRecommendation result: [");

        int videoCount = 0;
        for (Map.Entry<ShowInput, Double> entry : sortedVideos.entrySet()) {
            output.append(entry.getKey().getTitle());
            videoCount++;
            if (videoCount < sortedVideos.size()) {
                output.append(", ");
            } else {
                output.append("]");
                return output;
            }
        }
        return output;
    }
}
