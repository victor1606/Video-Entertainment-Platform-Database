package commandcaller;

import commands.Command;
import fileio.ActionInputData;
import fileio.Input;
import fileio.Writer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import queries.Query;
import recommendations.Recommendation;

import java.io.IOException;
import java.util.Objects;

public final class UserCommands {

    private UserCommands() {
    }

    /**
     * This method calls all functionalities of the project based on user input.
     * The method also writes the output to a JSONArray
     *
     * @param writer      used to obtain JSONObject
     * @param input       contains all data of a user's input
     * @param resultArray contains all output of a test
     */
    public static void userCommands(final Input input, final Writer writer,
                                  final JSONArray resultArray) throws IOException {
        StringBuilder output = new StringBuilder();

        for (ActionInputData command : input.getCommands()) {
            if (command.getActionType().equals("command")) {
                output = Command.doCommand(command, input);
            }

            if (command.getActionType().equals("query")) {
                output = Query.doQuery(command, input);
            }

            if (command.getActionType().equals("recommendation")) {
                output = Recommendation.doRecommendation(command, input);
            }

            JSONObject object = writer.writeFile(command.getActionId(),
                    null, Objects.requireNonNull(output).toString());
            resultArray.add(object);
        }
    }
}
