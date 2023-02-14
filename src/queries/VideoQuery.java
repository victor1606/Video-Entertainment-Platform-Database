package queries;


import fileio.ActionInputData;
import fileio.Input;
import fileio.UserInputData;
import fileio.MovieInputData;
import fileio.SerialInputData;
import utils.MultiComparator;
import utils.Utils;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;


public final class VideoQuery {

    private VideoQuery() {
    }

    /**
     * This method obtains a list of filtered movies, based on the user's input
     *
     * @param movies         contains initial movies list
     * @param command        contains all data of a test
     * @param filteredMovies contains filtered movies list
     */
    public static void setFiltersMovies(final List<MovieInputData> movies,
                                        final ActionInputData command,
                                        final List<MovieInputData> filteredMovies) {

        for (MovieInputData movie : movies) {
            if (movie != null) {
                if (command.getFilters().get(0).get(0) != null) {
                    int year = Integer.parseInt(
                            command.getFilters().get(0).get(0));
                    if (movie.getYear() != year) {
                        continue;
                    }
                }
                if (command.getFilters().get(1).get(0) != null) {
                    if (!movie.getGenres().contains(
                            command.getFilters().get(1).get(0))) {
                        continue;
                    }
                }
                filteredMovies.add(movie);
            }
        }
    }

    /**
     * This method obtains a list of filtered series, based on the user's input
     *
     * @param series         contains initial series list
     * @param command        contains all data of a test
     * @param filteredSeries contains filtered series list
     */
    public static void setFiltersSeries(final List<SerialInputData> series,
                                        final ActionInputData command,
                                        final List<SerialInputData> filteredSeries) {

        for (SerialInputData serial : series) {
            if (serial != null) {
                if (command.getFilters().get(0).get(0) != null) {
                    int year = Integer.parseInt(
                            command.getFilters().get(0).get(0));
                    if (serial.getYear() != year) {
                        continue;
                    }
                }

                if (command.getFilters().get(1).get(0) != null) {
                    if (!serial.getGenres().contains(
                            command.getFilters().get(1).get(0))) {
                        continue;
                    }
                }
                filteredSeries.add(serial);
            }
        }
    }

    /**
     * This method builds a map of every video and its favorite count.
     *
     * @param favoritedVideos contains map of videos(title, favorite_count)
     * @param input           contains all data of a test
     */
    public static void makeFavoritedVideosMap(final Map<String, Integer> favoritedVideos,
                                              final Input input) {

        for (UserInputData user : input.getUsers()) {
            for (String show : user.getFavoriteMovies()) {
                if (!favoritedVideos.containsKey(show)) {
                    favoritedVideos.put(show, 1);
                } else {
                    favoritedVideos.replace(show,
                            favoritedVideos.get(show) + 1);
                }
            }
        }
    }

    /**
     * This method obtains a list of the most favorited videos.
     * The method makes a map of every video and its favorite count.
     * 2 new lists are made, containing every movie/series (filtered based
     * on input). The method then iterates through the map, checking if the
     * filtered movies/series list contain the map entries. If not, their values are nulled;
     * The output StringBuilder is built appending the map keys whose values are not null;
     *
     * @param command contains all data of a user's input
     * @param input   contains all data of a test
     * @param output  contains the output StringBuilder
     */
    public static StringBuilder doFavoriteQuery(final ActionInputData command,
                                                final Input input,
                                                final StringBuilder output) {
        Map<String, Integer> favoritedVideos = new HashMap<>();

        makeFavoritedVideosMap(favoritedVideos, input);

        List<MovieInputData> filteredMovies = new ArrayList<>();
        List<SerialInputData> filteredSeries = new ArrayList<>();

        if (command.getObjectType().equals("movies")) {
            setFiltersMovies(input.getMovies(), command, filteredMovies);
        } else {
            setFiltersSeries(input.getSerials(), command, filteredSeries);
        }

        favoritedVideos = MultiComparator.sortMapByValues(
                favoritedVideos, command.getSortType());
        favoritedVideos = MultiComparator.sortMapAlpha(
                favoritedVideos, command.getSortType());

        for (Map.Entry<String, Integer> entry : favoritedVideos.entrySet()) {
            if (command.getObjectType().equals("movies")) {
                if (!filteredMovies.contains(
                        Utils.findMovieByName(entry.getKey(), input))) {
                    entry.setValue(null);
                }
            } else {
                if (!filteredSeries.contains(
                        Utils.findSerialByName(entry.getKey(), input))) {
                    entry.setValue(null);
                }
            }
        }

        favoritedVideos.entrySet().removeIf(val -> null == val.getValue());

        int titleCount = 0;
        for (Map.Entry<String, Integer> entry : favoritedVideos.entrySet()) {
            if (titleCount < command.getNumber()) {
                output.append(entry.getKey());
                titleCount++;

                if (titleCount == command.getNumber()
                        || titleCount == favoritedVideos.size()) {
                    output.append("]");
                    return output;
                } else {
                    output.append(", ");
                }
            }
        }

        output.append("]");
        return output;
    }

    /**
     * This method obtains a list of the highest rated movies.
     * The method adds every movie to a list which is sorted and filtered
     * based on the user's input.
     *
     * @param command contains all data of a user's input
     * @param input   contains all data of a test
     * @param output  contains the output StringBuilder
     */
    public static StringBuilder doMovieRatingQuery(final ActionInputData command,
                                                   final Input input,
                                                   final StringBuilder output) {
        List<MovieInputData> sortedMovies = new ArrayList<>(input.getMovies());
        List<MovieInputData> filteredMovies = new ArrayList<>();

        MultiComparator.sortMoviesByRating(sortedMovies, command.getSortType());
        MultiComparator.sortMoviesAlphaRating(sortedMovies, command.getSortType());

        setFiltersMovies(sortedMovies, command, filteredMovies);

        filteredMovies.removeIf(movie -> (movie.getAverage() == 0));

        if (filteredMovies.isEmpty()) {
            output.append("]");
            return output;
        }

        int titleCount = 0;
        for (MovieInputData movie : filteredMovies) {
            if (titleCount < command.getNumber()) {
                output.append(movie.getTitle());
                titleCount++;

                if (titleCount == command.getNumber()
                        || titleCount == filteredMovies.size()) {
                    output.append("]");
                } else {
                    output.append(", ");
                }
            }
        }
        return output;
    }

    /**
     * This method obtains a list of the highest rated series.
     * The method adds every series to a list which is sorted and filtered
     * based on the user's input.
     *
     * @param command contains all data of a user's input
     * @param input   contains all data of a test
     * @param output  contains the output StringBuilder
     */
    public static StringBuilder doSeriesRatingQuery(final ActionInputData command,
                                                    final Input input,
                                                    final StringBuilder output) {
        List<SerialInputData> sortedSeries = new ArrayList<>(input.getSerials());
        List<SerialInputData> filteredSeries = new ArrayList<>();

        MultiComparator.sortSeriesByRating(sortedSeries, command.getSortType());
        MultiComparator.sortSeriesAlphaRating(sortedSeries, command.getSortType());

        setFiltersSeries(sortedSeries, command, filteredSeries);

        filteredSeries.removeIf(series -> (series.getAverage() == 0));

        if (filteredSeries.isEmpty()) {
            output.append("]");
            return output;
        }

        int titleCount = 0;
        for (SerialInputData series : filteredSeries) {
            if (titleCount < command.getNumber()) {
                output.append(series.getTitle());
                titleCount++;

                if (titleCount == command.getNumber()
                        || titleCount == filteredSeries.size()) {
                    output.append("]");
                } else {
                    output.append(", ");
                }
            }
        }

        return output;
    }

    /**
     * This method obtains a list of the longest movies.
     * The method adds every movie to a list which is sorted and filtered
     * based on the user's input.
     *
     * @param command contains all data of a user's input
     * @param input   contains all data of a test
     * @param output  contains the output StringBuilder
     */
    public static StringBuilder doLongestMovieQuery(final ActionInputData command,
                                                    final Input input,
                                                    final StringBuilder output) {
        List<MovieInputData> sortedMovies = new ArrayList<>(input.getMovies());
        List<MovieInputData> filteredMovies = new ArrayList<>();

        MultiComparator.sortMoviesByDuration(sortedMovies, command.getSortType());
        MultiComparator.sortMoviesAlphaDuration(sortedMovies, command.getSortType());

        setFiltersMovies(sortedMovies, command, filteredMovies);

        if (filteredMovies.isEmpty()) {
            output.append("]");
            return output;
        }

        filteredMovies.removeIf(movie -> (movie.getDuration() == 0));

        int titleCount = 0;
        for (MovieInputData movie : filteredMovies) {
            if (titleCount < command.getNumber()) {
                output.append(movie.getTitle());
                titleCount++;

                if (titleCount == command.getNumber()
                        || titleCount == filteredMovies.size()) {
                    output.append("]");
                } else {
                    output.append(", ");
                }
            }
        }
        return output;
    }

    /**
     * This method obtains a list of the longest series.
     * The method adds every series to a list which is sorted and filtered
     * based on the user's input.
     *
     * @param command contains all data of a user's input
     * @param input   contains all data of a test
     * @param output  contains the output StringBuilder
     */
    public static StringBuilder doLongestSeriesQuery(final ActionInputData command,
                                                     final Input input,
                                                     final StringBuilder output) {
        List<SerialInputData> sortedSeries = new ArrayList<>(input.getSerials());
        List<SerialInputData> filteredSeries = new ArrayList<>();

        MultiComparator.sortSeriesByDuration(sortedSeries, command.getSortType());
        MultiComparator.sortSeriesAlphaDuration(sortedSeries, command.getSortType());

        setFiltersSeries(sortedSeries, command, filteredSeries);

        filteredSeries.removeIf(series -> (series.getTotalDuration() == 0));

        if (filteredSeries.isEmpty()) {
            output.append("]");
            return output;
        }

        int titleCount = 0;
        for (SerialInputData series : filteredSeries) {
            if (titleCount < command.getNumber()) {
                output.append(series.getTitle());
                titleCount++;

                if (titleCount == command.getNumber()
                        || titleCount == filteredSeries.size()) {
                    output.append("]");
                } else {
                    output.append(", ");
                }
            }
        }
        return output;
    }

    /**
     * This method obtains a list of the most viewed movies.
     * The method iterates through every user's history and updates the
     * totalViewsMovies map accordingly. The map is sorted and the entries are
     * copied to a list which is then filtered based on the user's filters.
     * The list's titles are appended to the output StringBuilder.
     *
     * @param command contains all data of a user's input
     * @param input   contains all data of a test
     * @param output  contains the output StringBuilder
     */
    public static StringBuilder doMostViewedMovieQuery(final ActionInputData command,
                                                       final Input input,
                                                       final StringBuilder output) {

        Map<String, Integer> totalViewsMovies = new HashMap<>();

        for (UserInputData user : input.getUsers()) {
            for (Map.Entry<String, Integer> entry : user.getHistory().entrySet()) {
                if (totalViewsMovies.containsKey(entry.getKey())) {
                    totalViewsMovies.replace(entry.getKey(),
                            totalViewsMovies.get(entry.getKey())
                                    + (int) entry.getValue());
                } else {
                    totalViewsMovies.put(entry.getKey(), entry.getValue());
                }
            }
        }

        totalViewsMovies = MultiComparator.sortMapByValues(
                totalViewsMovies, command.getSortType());
        totalViewsMovies = MultiComparator.sortMapAlpha(
                totalViewsMovies, command.getSortType());

        List<MovieInputData> sortedMovies = new ArrayList<>();
        List<MovieInputData> filteredMovies = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : totalViewsMovies.entrySet()) {
            sortedMovies.add(Utils.findMovieByName(entry.getKey(), input));
        }

        setFiltersMovies(sortedMovies, command, filteredMovies);

        if (filteredMovies.isEmpty()) {
            output.append("]");
            return output;
        }

        int titleCount = 0;
        for (MovieInputData movie : filteredMovies) {
            if (titleCount < command.getNumber()) {
                output.append(movie.getTitle());
                titleCount++;

                if (titleCount == command.getNumber()
                        || titleCount == filteredMovies.size()) {
                    output.append("]");
                } else {
                    output.append(", ");
                }
            }
        }
        return output;
    }

    /**
     * This method obtains a list of the most viewed series.
     * The method iterates through every user's history and updates the
     * totalViewsSeries map accordingly. The map is sorted and the entries are
     * copied to a list which is then filtered based on the user's filters.
     * The list's titles are appended to the output StringBuilder.
     *
     * @param command contains all data of a user's input
     * @param input   contains all data of a test
     * @param output  contains the output StringBuilder
     */
    public static StringBuilder doMostViewedSeriesQuery(final ActionInputData command,
                                                        final Input input,
                                                        final StringBuilder output) {

        Map<String, Integer> totalViewsSeries = new HashMap<>();

        for (UserInputData user : input.getUsers()) {
            for (Map.Entry<String, Integer> entry : user.getHistory().entrySet()) {
                if (totalViewsSeries.containsKey(entry.getKey())) {
                    totalViewsSeries.replace(entry.getKey(),
                            totalViewsSeries.get(entry.getKey())
                                    + (int) entry.getValue());
                } else {
                    totalViewsSeries.put(entry.getKey(), entry.getValue());
                }
            }
        }

        totalViewsSeries = MultiComparator.sortMapByValues(
                totalViewsSeries, command.getSortType());
        totalViewsSeries = MultiComparator.sortMapAlpha(
                totalViewsSeries, command.getSortType());

        List<SerialInputData> sortedSeries = new ArrayList<>();
        List<SerialInputData> filteredSeries = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : totalViewsSeries.entrySet()) {
            sortedSeries.add(Utils.findSerialByName(entry.getKey(), input));
        }

        setFiltersSeries(sortedSeries, command, filteredSeries);

        if (filteredSeries.isEmpty()) {
            output.append("]");
            return output;
        }

        int titleCount = 0;
        for (SerialInputData movie : filteredSeries) {
            if (titleCount < command.getNumber()) {
                output.append(movie.getTitle());
                titleCount++;

                if (titleCount == command.getNumber()
                        || titleCount == filteredSeries.size()) {
                    output.append("]");
                } else {
                    output.append(", ");
                }
            }
        }
        return output;
    }

    /**
     * This method calls the appropriate video query methods.
     * The method returns the output StringBuilder.
     *
     * @param command contains all data of a user's input
     * @param input   contains all data of a test
     */
    public static StringBuilder doVideoQuery(final ActionInputData command, final Input input) {

        StringBuilder output = new StringBuilder("Query result: [");

        switch (command.getCriteria()) {
            case "ratings":
                if (command.getObjectType().equals("movies")) {
                    return doMovieRatingQuery(command, input, output);
                } else {
                    return doSeriesRatingQuery(command, input, output);
                }

            case "favorite":
                return doFavoriteQuery(command, input, output);

            case "longest":
                if (command.getObjectType().equals("movies")) {
                    return doLongestMovieQuery(command, input, output);
                } else {
                    return doLongestSeriesQuery(command, input, output);
                }

            case "most_viewed":
                if (command.getObjectType().equals("movies")) {
                    return doMostViewedMovieQuery(command, input, output);
                } else {
                    return doMostViewedSeriesQuery(command, input, output);
                }

            default:
                break;
        }
        return null;
    }
}
