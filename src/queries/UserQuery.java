package queries;


import fileio.ActionInputData;
import fileio.Input;
import fileio.UserInputData;
import utils.MultiComparator;

import java.util.ArrayList;
import java.util.List;

public final class UserQuery {

    private UserQuery() {
    }

    /**
     * This method obtains a list of the most active users.
     * The sorting is done based on the user's number of rated videos.
     *
     * @param command contains all data of a user's input
     * @param input   contains all data of a test
     */
    public static StringBuilder doUserQuery(final ActionInputData command, final Input input) {

        StringBuilder output = new StringBuilder("Query result: [");

        List<UserInputData> sortedUsers = new ArrayList<>(input.getUsers());

        MultiComparator.sortUsersRatings(sortedUsers, command.getSortType());
        MultiComparator.sortUsersAlpha(sortedUsers, command.getSortType());

        sortedUsers.removeIf(user -> (user.getRatedMovies().size() == 0));

        if (sortedUsers.isEmpty()) {
            output.append("]");
            return output;
        }

        int userCount = 0;
        for (UserInputData user : sortedUsers) {
            if (userCount < command.getNumber()) {
                output.append(user.getUsername());
                userCount++;

                if (userCount == command.getNumber() || userCount == sortedUsers.size()) {
                    output.append("]");
                } else {
                    output.append(", ");
                }
            }
        }

        return output;
    }
}
