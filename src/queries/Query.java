package queries;


import fileio.ActionInputData;
import fileio.Input;

public final class Query {

    private Query() {
    }

    /**
     * This method calls the appropriate Query methods based on the command
     *
     * @param command contains all data of a user's input
     * @param input   contains all data of a test
     */
    public static StringBuilder doQuery(final ActionInputData command, final Input input) {

        switch (command.getObjectType()) {
            case "actors":
                return ActorQuery.doActorQuery(command, input);

            case "movies":
            case "shows":
                return VideoQuery.doVideoQuery(command, input);

            case "users":
                return UserQuery.doUserQuery(command, input);

            default:
                break;
        }
        return null;
    }
}
