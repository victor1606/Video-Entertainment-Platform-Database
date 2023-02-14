package queries;

import actor.ActorsAwards;
import common.Constants;
import fileio.ActionInputData;
import fileio.ActorInputData;
import fileio.Input;
import utils.MultiComparator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public final class ActorQuery {

    private ActorQuery() {
    }

    /**
     * This method returns the output of the AverageActorQuery.
     * Every actor is added to a new ArrayList that is sorted based on their
     * calculated average ratings. Elements of the list are then appended to
     * the output StringBuilder accordingly.
     *
     * @param command contains all data of a user's input
     * @param input   contains all data of a test
     * @param output  contains initialized StringBuilder output
     */
    public static StringBuilder doAverageActorQuery(final ActionInputData command,
                                                    final Input input,
                                                    final StringBuilder output) {
        ArrayList<ActorInputData> sortedActors = new ArrayList<>();

        for (ActorInputData actor : input.getActors()) {
            actor.setVideosAverage(input);
            if (actor.getVideosAverage() > 0) {
                sortedActors.add(actor);
            }
        }

        MultiComparator.sortByValue(sortedActors, command.getSortType());
        MultiComparator.sortAlpha(sortedActors, command.getSortType());

        int actorCount = 0;
        for (ActorInputData actor : sortedActors) {
            output.append(actor.getName());
            actorCount++;
            if (actorCount < command.getNumber() && actorCount < sortedActors.size()) {
                output.append(", ");
            } else {
                output.append(("]"));
                return output;
            }
        }
        return output;
    }

    /**
     * This method returns the output of the AwardsActorQuery.
     * Every actor that has the required awards is added to a new ArrayList
     * that is sorted based on the actor's calculated award count.
     * Elements of the list are then appended to the output StringBuilder accordingly.
     *
     * @param command contains all data of a user's input
     * @param input   contains all data of a test
     * @param output  contains initialized StringBuilder output
     */
    public static StringBuilder doAwardsActorQuery(final ActionInputData command,
                                                   final Input input,
                                                   final StringBuilder output) {

        List<String> filterAwards = command.getFilters().get(Constants.GENRES_FIELD);
        List<ActorInputData> sortedActorList = new ArrayList<>();

        for (ActorInputData actor : input.getActors()) {
            actor.setAwardCount();
            int inputAwardsCount = 0;

            for (String award : filterAwards) {
                if (actor.getAwards().containsKey(ActorsAwards.valueOf(award))) {
                    inputAwardsCount++;
                } else {
                    break;
                }
            }
            if (inputAwardsCount == filterAwards.size()) {
                sortedActorList.add(actor);
            }
        }

        if (!sortedActorList.isEmpty()) {
            MultiComparator.sortByAwards(sortedActorList, command.getSortType());
            MultiComparator.sortByAwardsAlpha(sortedActorList, command.getSortType());

            for (int i = 0; i < sortedActorList.size(); ++i) {
                output.append(sortedActorList.get(i).getName());
                if (i == sortedActorList.size() - 1) {
                    output.append("]");
                } else {
                    output.append(", ");
                }
            }
        } else {
            output.append("]");
        }
        return output;
    }

    /**
     * This method returns the output of the FilterActorQuery.
     * Every actor that has the required keywords present in its description
     * is added to a new sorted actor list.
     * Elements of the list are then appended to the output StringBuilder accordingly.
     *
     * @param command contains all data of a user's input
     * @param input   contains all data of a test
     * @param output  contains initialized StringBuilder output
     */
    public static StringBuilder doFilterActorQuery(final ActionInputData command,
                                                   final Input input,
                                                   final StringBuilder output) {
        List<String> keywords = command.getFilters().get(2);
        List<ActorInputData> sortedActorList = new ArrayList<>();

        for (ActorInputData actor : input.getActors()) {
            List<String> descriptionLower = Arrays.asList(actor.getCareerDescription().
                    toLowerCase(Locale.ROOT).split(" "));

            if (descriptionLower.containsAll(keywords)) {
                sortedActorList.add(actor);
            }
        }

        if (!sortedActorList.isEmpty()) {
            MultiComparator.sortByNames(sortedActorList, command.getSortType());

            for (int i = 0; i < sortedActorList.size(); ++i) {
                output.append(sortedActorList.get(i).getName());
                if (i == sortedActorList.size() - 1) {
                    output.append("]");
                } else {
                    output.append(", ");
                }
            }
        } else {
            output.append("]");
        }
        return output;
    }

    /**
     * This method calls the appropriate Query methods.
     * The method returns the queries' StringBuilder output.
     *
     * @param command contains all data of a user's input
     * @param input   contains all data of a test
     */
    public static StringBuilder doActorQuery(final ActionInputData command, final Input input) {

        StringBuilder output = new StringBuilder("Query result: [");

        switch (command.getCriteria()) {
            case "average":
                return doAverageActorQuery(command, input, output);

            case "awards":
                return doAwardsActorQuery(command, input, output);

            case "filter_description":
                return doFilterActorQuery(command, input, output);

            default:
                break;
        }
        return null;
    }
}
