package recommendations;

import fileio.ActionInputData;
import fileio.SerialInputData;
import fileio.MovieInputData;
import fileio.UserInputData;
import fileio.ShowInput;
import fileio.Input;

import utils.MultiComparator;
import utils.Utils;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;



public final class PopularRec {

    private PopularRec() {
    }

    /**
     * This method builds a genre popularity map.
     * Iterates through every user's history, gets said video's genre and
     * adds an entry to the genre popularity map(genre, times viewed).
     *
     * @param genrePopularity contains all the map being built
     * @param input           contains all data of a test
     */
    public static void makeGenrePopularityMap(final Map<String, Integer> genrePopularity,
                                              final Input input) {
        for (UserInputData user : input.getUsers()) {
            for (Map.Entry<String, Integer> historyEntry
                    : user.getHistory().entrySet()) {
                ShowInput watchedVideo =
                        Utils.findVideoByName(historyEntry.getKey(), input);
                if (watchedVideo != null) {
                    for (String genre : watchedVideo.getGenres()) {
                        if (genrePopularity.containsKey(genre)) {
                            genrePopularity.replace(genre,
                                    genrePopularity.get(genre)
                                            + historyEntry.getValue());
                        } else {
                            genrePopularity.put(genre,
                                    historyEntry.getValue());
                        }
                    }
                }
            }
        }
    }

    /**
     * This method builds a list of videos based on their genre.
     *
     * @param genre contains genre that every video from the list will have
     * @param input   contains all data of a test
     */
    public static List<ShowInput> makeShowsByGenreList(final String genre,
                                                       final Input input) {
        List<ShowInput> showsByGenre = new ArrayList<>();
        for (MovieInputData movie : input.getMovies()) {
            if (movie.getGenres().contains(genre)) {
                showsByGenre.add(movie);
            }
        }
        for (SerialInputData serial : input.getSerials()) {
            if (serial.getGenres().contains(genre)) {
                showsByGenre.add(serial);
            }
        }
        return showsByGenre;
    }

    /**
     * This method obtains a popular recommendation.
     * Once the genre popularity map is built and sorted, it is iterated through,
     * building a new list of shows for every genre present. The first unwatched
     * show from the list is appended to the output StringBuilder.
     *
     * @param command contains all data of a user's input
     * @param input   contains all data of a test
     * @param output  contains the output StringBuilder
     */
    public static StringBuilder doPopularRecommendation(final ActionInputData command,
                                                        final Input input,
                                                        final StringBuilder output) {

        UserInputData user = Utils.findUser(command, input);
        if (user == null) {
            return output;
        }

        if (user.getSubscriptionType().equals("BASIC")) {
            output.append("PopularRecommendation cannot be applied!");
            return output;
        }

        Map<String, Integer> genrePopularity = new HashMap<>();
        makeGenrePopularityMap(genrePopularity, input);

        genrePopularity = MultiComparator.sortMapByValues(genrePopularity, "desc");

        int found = 0;
        for (Map.Entry<String, Integer> genre : genrePopularity.entrySet()) {
            List<ShowInput> showsByGenre = makeShowsByGenreList(genre.getKey(), input);

            for (ShowInput video : showsByGenre) {
                if (!user.getHistory().containsKey(video.getTitle())) {
                    output.append("PopularRecommendation result: ");
                    output.append(video.getTitle());
                    found++;
                    break;
                }
            }
            if (found != 0) {
                return output;
            }
        }

        output.append("PopularRecommendation cannot be applied!");
        return output;
    }
}
