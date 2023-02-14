package utils;


import fileio.ActorInputData;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;
import fileio.ShowInput;

import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Objects;

public final class MultiComparator {

    private MultiComparator() {
    }

    /**
     * This method sorts a list of actors based on their average rating
     *
     * @param list contains the list to be sorted
     * @param sortType contains the type of sorting(asc/desc)
     */
    public static void sortByValue(final List<ActorInputData> list, final String sortType) {
        list.sort((o1, o2) -> {
            int a;
            int b;
            if (sortType.equals("asc")) {
                a = -1;
                b = 1;
            } else {
                a = 1;
                b = -1;
            }
            if (o1.getVideosAverage() < o2.getVideosAverage()) {
                return a;
            }
            if (o1.getVideosAverage() > o2.getVideosAverage()) {
                return b;
            }
            return 0;
        });
    }

    /**
     * This method sorts a list of actors lexicographically if their ratings are equal.
     *
     * @param list contains the list to be sorted
     * @param sortType contains the type of sorting(asc/desc)
     */
    public static void sortAlpha(final List<ActorInputData> list, final String sortType) {
        list.sort((o1, o2) -> {
            int a;
            int b;
            if (sortType.equals("asc")) {
                a = -1;
                b = 1;
            } else {
                a = 1;
                b = -1;
            }
            if (o1.getVideosAverage() == o2.getVideosAverage()) {
                if (o1.getName().compareTo(o2.getName()) < 0) {
                    return a;
                }
                if (o1.getName().compareTo(o2.getName()) > 0) {
                    return b;
                }
            }
            return 0;
        });
    }

    /**
     * This method sorts a list of actors based on their award count
     *
     * @param list contains the list to be sorted
     * @param sortType contains the type of sorting(asc/desc)
     */
    public static void sortByAwards(final List<ActorInputData> list, final String sortType) {
        list.sort((o1, o2) -> {
            int a;
            int b;
            if (sortType.equals("asc")) {
                a = -1;
                b = 1;
            } else {
                a = 1;
                b = -1;
            }
            if (o1.getAwardCount() < o2.getAwardCount()) {
                return a;
            }
            if (o1.getAwardCount() > o2.getAwardCount()) {
                return b;
            }
            return 0;
        });
    }

    /**
     * This method sorts a list of actors lexicographically if their award counts are equal.
     *
     * @param list contains the list to be sorted
     * @param sortType contains the type of sorting(asc/desc)
     */
    public static void sortByAwardsAlpha(final List<ActorInputData> list, final String sortType) {
        list.sort((o1, o2) -> {
            int a;
            int b;
            if (sortType.equals("asc")) {
                a = -1;
                b = 1;
            } else {
                a = 1;
                b = -1;
            }
            if (o1.getAwardCount() == o2.getAwardCount()) {
                if (o1.getName().compareTo(o2.getName()) < 0) {
                    return a;
                }
                if (o1.getName().compareTo(o2.getName()) > 0) {
                    return b;
                }
            }
            return 0;
        });
    }

    /**
     * This method sorts a list of actors lexicographically.
     *
     * @param list contains the list to be sorted
     * @param sortType contains the type of sorting(asc/desc)
     */
    public static void sortByNames(final List<ActorInputData> list, final String sortType) {
        list.sort((o1, o2) -> {
            int a;
            int b;
            if (sortType.equals("asc")) {
                a = -1;
                b = 1;
            } else {
                a = 1;
                b = -1;
            }
            if (o1.getName().compareTo(o2.getName()) < 0) {
                return a;
            }
            if (o1.getName().compareTo(o2.getName()) > 0) {
                return b;
            }
            return 0;
        });
    }

    /**
     * This method sorts a map based on its values.
     *
     * @param videos contains the list to be sorted
     * @param sortType contains the type of sorting(asc/desc)
     */
    public static Map<String, Integer> sortMapByValues(final Map<String,
            Integer> videos, final String sortType) {
        List<Map.Entry<String, Integer>> list =
                new LinkedList<>(videos.entrySet());

        list.sort((o1, o2) -> {
            int a;
            int b;
            if (sortType.equals("asc")) {
                a = -1;
                b = 1;
            } else {
                a = 1;
                b = -1;
            }
            if (o1.getValue() < o2.getValue()) {
                return a;
            }
            if (o1.getValue() > o2.getValue()) {
                return b;
            }
            return 0;
        });

        HashMap<String, Integer> temp = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

    /**
     * This method sorts a map lexicographically if values are equal.
     *
     * @param videos contains the list to be sorted
     * @param sortType contains the type of sorting(asc/desc)
     */
    public static Map<String, Integer> sortMapAlpha(final Map<String, Integer> videos,
                                                    final String sortType) {
        List<Map.Entry<String, Integer>> list =
                new LinkedList<>(videos.entrySet());

        list.sort((o1, o2) -> {
            int a;
            int b;
            if (sortType.equals("asc")) {
                a = -1;
                b = 1;
            } else {
                a = 1;
                b = -1;
            }
            if (Objects.equals(o1.getValue(), o2.getValue())) {
                if (o1.getKey().compareTo(o2.getKey()) < 0) {
                    return a;
                }
                if (o1.getKey().compareTo(o2.getKey()) > 0) {
                    return b;
                }
            }
            return 0;
        });

        HashMap<String, Integer> temp = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

    /**
     * This method sorts a list of movies based on their average rating.
     *
     * @param list contains the list to be sorted
     * @param sortType contains the type of sorting(asc/desc)
     */
    public static void sortMoviesByRating(final List<MovieInputData> list, final String sortType) {
        list.sort((o1, o2) -> {
            int a;
            int b;
            if (sortType.equals("asc")) {
                a = -1;
                b = 1;
            } else {
                a = 1;
                b = -1;
            }
            if (o1.getAverage() < o2.getAverage()) {
                return a;
            }
            if (o1.getAverage() > o2.getAverage()) {
                return b;
            }
            return 0;
        });
    }

    /**
     * This method sorts a list of movies lexicographically if their average ratings are equal.
     *
     * @param list contains the list to be sorted
     * @param sortType contains the type of sorting(asc/desc)
     */
    public static void sortMoviesAlphaRating(final List<MovieInputData> list,
                                             final String sortType) {
        list.sort((o1, o2) -> {
            int a;
            int b;
            if (sortType.equals("asc")) {
                a = -1;
                b = 1;
            } else {
                a = 1;
                b = -1;
            }
            if (o1.getAverage() == o2.getAverage()) {
                if (o1.getTitle().compareTo(o2.getTitle()) < 0) {
                    return a;
                }
                if (o1.getTitle().compareTo(o2.getTitle()) > 0) {
                    return b;
                }
            }
            return 0;
        });
    }

    /**
     * This method sorts a list of movies based on their durations.
     *
     * @param list contains the list to be sorted
     * @param sortType contains the type of sorting(asc/desc)
     */
    public static void sortMoviesByDuration(final List<MovieInputData> list,
                                            final String sortType) {
        list.sort((o1, o2) -> {
            int a;
            int b;
            if (sortType.equals("asc")) {
                a = -1;
                b = 1;
            } else {
                a = 1;
                b = -1;
            }
            if (o1.getDuration() < o2.getDuration()) {
                return a;
            }
            if (o1.getDuration() > o2.getDuration()) {
                return b;
            }
            return 0;
        });
    }

    /**
     * This method sorts a list of movies lexicographically if their durations are equal.
     *
     * @param list contains the list to be sorted
     * @param sortType contains the type of sorting(asc/desc)
     */
    public static void sortMoviesAlphaDuration(final List<MovieInputData> list,
                                               final String sortType) {
        list.sort((o1, o2) -> {
            int a;
            int b;
            if (sortType.equals("asc")) {
                a = -1;
                b = 1;
            } else {
                a = 1;
                b = -1;
            }
            if (o1.getDuration() == o2.getDuration()) {
                if (o1.getTitle().compareTo(o2.getTitle()) < 0) {
                    return a;
                }
                if (o1.getTitle().compareTo(o2.getTitle()) > 0) {
                    return b;
                }
            }
            return 0;
        });
    }

    /**
     * This method sorts a list of series based on their average rating.
     *
     * @param list contains the list to be sorted
     * @param sortType contains the type of sorting(asc/desc)
     */
    public static void sortSeriesByRating(final List<SerialInputData> list, final String sortType) {
        list.sort((o1, o2) -> {
            int a;
            int b;
            if (sortType.equals("asc")) {
                a = -1;
                b = 1;
            } else {
                a = 1;
                b = -1;
            }
            if (o1.getAverage() < o2.getAverage()) {
                return a;
            }
            if (o1.getAverage() > o2.getAverage()) {
                return b;
            }
            return 0;
        });
    }

    /**
     * This method sorts a list of series lexicographically if their ratings are equal.
     *
     * @param list contains the list to be sorted
     * @param sortType contains the type of sorting(asc/desc)
     */
    public static void sortSeriesAlphaRating(final List<SerialInputData> list,
                                             final String sortType) {
        list.sort((o1, o2) -> {
            int b;
            int a;
            if (sortType.equals("asc")) {
                a = -1;
                b = 1;
            } else {
                a = 1;
                b = -1;
            }
            if (o1.getAverage() == o2.getAverage()) {
                if (o1.getTitle().compareTo(o2.getTitle()) < 0) {
                    return a;
                }
                if (o1.getTitle().compareTo(o2.getTitle()) > 0) {
                    return b;
                }
            }
            return 0;
        });
    }

    /**
     * This method sorts a list of series based on their duration.
     *
     * @param list contains the list to be sorted
     * @param sortType contains the type of sorting(asc/desc)
     */
    public static void sortSeriesByDuration(final List<SerialInputData> list,
                                            final String sortType) {
        list.sort((o1, o2) -> {
            int a;
            int b;
            if (sortType.equals("asc")) {
                a = -1;
                b = 1;
            } else {
                a = 1;
                b = -1;
            }
            o1.setTotalDuration();
            o2.setTotalDuration();
            if (o1.getTotalDuration() < o2.getTotalDuration()) {
                return a;
            }
            if (o1.getTotalDuration() > o2.getTotalDuration()) {
                return b;
            }
            return 0;
        });
    }

    /**
     * This method sorts a list of series lexicographically if their durations are equal.
     *
     * @param list contains the list to be sorted
     * @param sortType contains the type of sorting(asc/desc)
     */
    public static void sortSeriesAlphaDuration(final List<SerialInputData> list,
                                               final String sortType) {
        list.sort((o1, o2) -> {
            int a;
            int b;
            if (sortType.equals("asc")) {
                a = -1;
                b = 1;
            } else {
                a = 1;
                b = -1;
            }
            if (o1.getTotalDuration() == o2.getTotalDuration()) {
                if (o1.getTitle().compareTo(o2.getTitle()) < 0) {
                    return a;
                }
                if (o1.getTitle().compareTo(o2.getTitle()) > 0) {
                    return b;
                }
            }
            return 0;
        });
    }

    /**
     * This method sorts a list of users based on the number of their reviews.
     *
     * @param list contains the list to be sorted
     * @param sortType contains the type of sorting(asc/desc)
     */
    public static void sortUsersRatings(final List<UserInputData> list,
                                        final String sortType) {
        list.sort((o1, o2) -> {
            int a;
            int b;
            if (sortType.equals("asc")) {
                a = -1;
                b = 1;
            } else {
                a = 1;
                b = -1;
            }
            if (o1.getRatedMovies().size() < o2.getRatedMovies().size()) {
                return a;
            }
            if (o1.getRatedMovies().size() > o2.getRatedMovies().size()) {
                return b;
            }
            return 0;
        });
    }

    /**
     * This method sorts a list of users lexicographically if their number of reviews is equal.
     *
     * @param list contains the list to be sorted
     * @param sortType contains the type of sorting(asc/desc)
     */
    public static void sortUsersAlpha(final List<UserInputData> list,
                                      final String sortType) {
        list.sort((o1, o2) -> {
            int a;
            int b;
            if (sortType.equals("asc")) {
                a = -1;
                b = 1;
            } else {
                a = 1;
                b = -1;
            }
            if (o1.getRatedMovies().size() == o2.getRatedMovies().size()) {
                if (o1.getUsername().compareTo(o2.getUsername()) < 0) {
                    return a;
                }
                if (o1.getUsername().compareTo(o2.getUsername()) > 0) {
                    return b;
                }
            }
            return 0;
        });
    }

    /**
     * This method sorts a map of videos based on the values(Double).
     *
     * @param videos contains the list to be sorted
     * @param sortType contains the type of sorting(asc/desc)
     */
    public static Map<ShowInput, Double> sortAllVideosByRating(final Map<ShowInput, Double> videos,
                                                               final String sortType) {
        List<Map.Entry<ShowInput, Double>> list =
                new LinkedList<>(videos.entrySet());

        list.sort((o1, o2) -> {
            int a;
            int b;
            if (sortType.equals("asc")) {
                a = -1;
                b = 1;
            } else {
                a = 1;
                b = -1;
            }
            if (o1.getValue() < o2.getValue()) {
                return a;
            }
            if (o1.getValue() > o2.getValue()) {
                return b;
            }
            return 0;
        });

        HashMap<ShowInput, Double> temp = new LinkedHashMap<>();
        for (Map.Entry<ShowInput, Double> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

    /**
     * This method sorts a map of videos lexicographically if
     * the values(Double) are equal.
     *
     * @param videos contains the list to be sorted
     * @param sortType contains the type of sorting(asc/desc)
     */
    public static Map<ShowInput, Double> sortAllVideosAlpha(final Map<ShowInput, Double> videos,
                                                            final String sortType) {
        List<Map.Entry<ShowInput, Double>> list = new LinkedList<>(videos.entrySet());

        list.sort((o1, o2) -> {
            int a;
            int b;
            if (sortType.equals("asc")) {
                a = -1;
                b = 1;
            } else {
                a = 1;
                b = -1;
            }
            if (Objects.equals(o1.getValue(), o2.getValue())) {
                if (o1.getKey().getTitle().compareTo(o2.getKey().getTitle()) < 0) {
                    return a;
                }
                if (o1.getKey().getTitle().compareTo(o2.getKey().getTitle()) > 0) {
                    return b;
                }
            }
            return 0;
        });

        HashMap<ShowInput, Double> temp = new LinkedHashMap<>();
        for (Map.Entry<ShowInput, Double> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }
}
