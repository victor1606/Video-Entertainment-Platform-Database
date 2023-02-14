package utils;

import actor.ActorsAwards;
import common.Constants;
import entertainment.Genre;
import entertainment.Season;

import fileio.ActionInputData;
import fileio.Input;
import fileio.ShowInput;
import fileio.SerialInputData;
import fileio.MovieInputData;
import fileio.UserInputData;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Map;
import java.util.LinkedHashMap;

/**
 * The class contains static methods that helps with parsing.
 *
 * We suggest you add your static methods here or in a similar class.
 */
public final class Utils {
    /**
     * for coding style
     */
    private Utils() {
    }

    /**
     * Transforms a string into an enum
     * @param genre of video
     * @return an Genre Enum
     */
    public static Genre stringToGenre(final String genre) {
        return switch (genre.toLowerCase()) {
            case "action" -> Genre.ACTION;
            case "adventure" -> Genre.ADVENTURE;
            case "drama" -> Genre.DRAMA;
            case "comedy" -> Genre.COMEDY;
            case "crime" -> Genre.CRIME;
            case "romance" -> Genre.ROMANCE;
            case "war" -> Genre.WAR;
            case "history" -> Genre.HISTORY;
            case "thriller" -> Genre.THRILLER;
            case "mystery" -> Genre.MYSTERY;
            case "family" -> Genre.FAMILY;
            case "horror" -> Genre.HORROR;
            case "fantasy" -> Genre.FANTASY;
            case "science fiction" -> Genre.SCIENCE_FICTION;
            case "action & adventure" -> Genre.ACTION_ADVENTURE;
            case "sci-fi & fantasy" -> Genre.SCI_FI_FANTASY;
            case "animation" -> Genre.ANIMATION;
            case "kids" -> Genre.KIDS;
            case "western" -> Genre.WESTERN;
            case "tv movie" -> Genre.TV_MOVIE;
            default -> null;
        };
    }

    /**
     * Transforms a string into an enum
     * @param award for actors
     * @return an ActorsAwards Enum
     */
    public static ActorsAwards stringToAwards(final String award) {
        return switch (award) {
            case "BEST_SCREENPLAY" -> ActorsAwards.BEST_SCREENPLAY;
            case "BEST_SUPPORTING_ACTOR" -> ActorsAwards.BEST_SUPPORTING_ACTOR;
            case "BEST_DIRECTOR" -> ActorsAwards.BEST_DIRECTOR;
            case "BEST_PERFORMANCE" -> ActorsAwards.BEST_PERFORMANCE;
            case "PEOPLE_CHOICE_AWARD" -> ActorsAwards.PEOPLE_CHOICE_AWARD;
            default -> null;
        };
    }

    /**
     * Transforms an array of JSON's into an array of strings
     * @param array of JSONs
     * @return a list of strings
     */
    public static ArrayList<String> convertJSONArray(final JSONArray array) {
        if (array != null) {
            ArrayList<String> finalArray = new ArrayList<>();
            for (Object object : array) {
                finalArray.add((String) object);
            }
            return finalArray;
        } else {
            return null;
        }
    }

    /**
     * Transforms an array of JSON's into a map
     * @param jsonActors array of JSONs
     * @return a map with ActorsAwardsa as key and Integer as value
     */
    public static Map<ActorsAwards, Integer> convertAwards(final JSONArray jsonActors) {
        Map<ActorsAwards, Integer> awards = new LinkedHashMap<>();

        for (Object iterator : jsonActors) {
            awards.put(stringToAwards((String) ((JSONObject) iterator).get(Constants.AWARD_TYPE)),
                    Integer.parseInt(((JSONObject) iterator).get(Constants.NUMBER_OF_AWARDS)
                            .toString()));
        }

        return awards;
    }

    /**
     * Transforms an array of JSON's into a map
     * @param movies array of JSONs
     * @return a map with String as key and Integer as value
     */
    public static Map<String, Integer> watchedMovie(final JSONArray movies) {
        Map<String, Integer> mapVideos = new LinkedHashMap<>();

        if (movies != null) {
            for (Object movie : movies) {
                mapVideos.put((String) ((JSONObject) movie).get(Constants.NAME),
                        Integer.parseInt(((JSONObject) movie).get(Constants.NUMBER_VIEWS)
                                .toString()));
            }
        } else {
            System.out.println("NU ESTE VIZIONAT NICIUN FILM");
        }

        return mapVideos;
    }

    /**
     * Returns the user that issued the command
     *
     * @param command contains all data of a user's input
     */
    public static UserInputData findUser(final ActionInputData command, final Input input) {
        for (UserInputData user : input.getUsers()) {
            if (user.getUsername().equals(command.getUsername())) {
                return user;
            }
        }
        return null;
    }

    /**
     * Returns the movie specified in the command
     *
     * @param command contains all data of a user's input
     */
    public static MovieInputData findMovie(final ActionInputData command, final Input input) {
        for (MovieInputData movie : input.getMovies()) {
            if (movie.getTitle().equals(command.getTitle())) {
                return movie;
            }
        }
        return null;
    }

    /**
     * Returns the movie named "name"
     *
     * @param input contains all data of a test
     */
    public static MovieInputData findMovieByName(final String name, final Input input) {
        for (MovieInputData movie : input.getMovies()) {
            if (movie.getTitle().equals(name)) {
                return movie;
            }
        }
        return null;
    }

    /**
     * Returns the series specified in the command
     *
     * @param command contains all data of a user's input
     */
    public static SerialInputData findSerial(final ActionInputData command, final Input input) {
        for (SerialInputData serial : input.getSerials()) {
            if (serial.getTitle().equals(command.getTitle())) {
                return serial;
            }
        }
        return null;
    }

    /**
     * Returns the series named "name"
     *
     * @param input contains all data of a test
     */
    public static SerialInputData findSerialByName(final String name, final Input input) {
        for (SerialInputData serial : input.getSerials()) {
            if (serial.getTitle().equals(name)) {
                return serial;
            }
        }
        return null;
    }

    /**
     * Returns the video named "name"
     *
     * @param input contains all data of a test
     */
    public static ShowInput findVideoByName(final String name, final Input input) {
        if (findMovieByName(name, input) != null) {
            return findMovieByName(name, input);
        } else if (findSerialByName(name, input) != null) {
            return findSerialByName(name, input);
        }
        return null;
    }

    /**
     * Returns the season specified in the command
     *
     * @param command contains all data of a user's input
     * @param show contains all data of a show
     */
    public static Season findSeason(final ActionInputData command, final ShowInput show) {
        int seasonCount = 1;
        for (Season season : ((SerialInputData) show).getSeasons()) {
            if (seasonCount == command.getSeasonNumber()) {
                return season;
            }
            seasonCount++;
        }
        return null;
    }
}
