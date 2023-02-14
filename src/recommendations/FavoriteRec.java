package recommendations;

import fileio.ActionInputData;
import fileio.Input;
import fileio.UserInputData;
import utils.MultiComparator;
import utils.Utils;

import java.util.HashMap;
import java.util.Map;

public final class FavoriteRec {

    private FavoriteRec() {
    }

    /**
     * This method obtains a favorite recommendation.
     * The method finds the most favorited video from the database not present
     * in the user's history. A sorted map is made containing all videos and their
     * favorite count.
     *
     * @param command contains all data of a user's input
     * @param input   contains all data of a test
     * @param output  contains the output StringBuilder
     */
    public static StringBuilder doFavoriteRecommendation(final ActionInputData command,
                                                         final Input input,
                                                         final StringBuilder output) {

        UserInputData user = Utils.findUser(command, input);
        if (user == null) {
            return output;
        }

        if (user.getSubscriptionType().equals("BASIC")) {
            output.append("FavoriteRecommendation cannot be applied!");
            return output;
        }

        Map<String, Integer> totalFavoritedVideos = new HashMap<>();

        for (UserInputData users : input.getUsers()) {
            for (String video : users.getFavoriteMovies()) {
                if (totalFavoritedVideos.containsKey(video)) {
                    totalFavoritedVideos.replace(video,
                            totalFavoritedVideos.get(video) + 1);
                } else {
                    totalFavoritedVideos.put(video, 1);
                }
            }
        }

        Map<String, Integer> sortedVideos = MultiComparator.sortMapByValues(
                totalFavoritedVideos, "desc");

        sortedVideos.keySet().removeIf(key -> user.getHistory().containsKey(key));

        if (sortedVideos.isEmpty()) {
            output.append("FavoriteRecommendation cannot be applied!");
            return output;
        }

        output.append("FavoriteRecommendation result: ");

        for (Map.Entry<String, Integer> entry : sortedVideos.entrySet()) {
            output.append(entry.getKey());
        }

        return output;
    }
}
