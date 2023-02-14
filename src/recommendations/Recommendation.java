package recommendations;


import fileio.ActionInputData;
import fileio.Input;


public final class Recommendation {

    private Recommendation() {
    }

    /**
     * This method calls the appropriate recommendation method.
     *
     * @param command contains all data of a user's input
     * @param input   contains all data of a test
     */
    public static StringBuilder doRecommendation(final ActionInputData command,
                                                 final Input input) {

        StringBuilder output = new StringBuilder();

        switch (command.getType()) {
            case "standard":
                return StandardRec.doStandardRecommendation(command, input, output);

            case "best_unseen":
                return BestUnseenRec.doBestUnseenRecommendation(command, input, output);

            case "favorite":
                return FavoriteRec.doFavoriteRecommendation(command, input, output);

            case "search":
                return SearchRec.doSearchRecommendation(command, input, output);

            case "popular":
                return PopularRec.doPopularRecommendation(command, input, output);

            default:
                break;
        }
        return null;
    }
}
