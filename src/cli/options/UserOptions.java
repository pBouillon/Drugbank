package cli.options;

import cli.display.Display;

/**
 * User option in the main CLI
 */
public class UserOptions {

    /**
     * Option to trigger the interactive diagnostic mode
     */
    public static final String Diagnostic = "d";

    public static final String Help = "h";

    public static final String Quit = "q";

    public static String getAllDescribedOptions() {
        StringBuilder builder = new StringBuilder();

        builder.append("Available commands:")
                .append("\n");

        builder.append("\t")
                .append(Display.getColorizedString(
                        Diagnostic, Display.ConsoleColors.WHITE_BOLD))
                .append("\tLaunch the interactive diagnostic mode")
                .append("\n");

        builder.append("\t")
                .append(Display.getColorizedString(
                        Help, Display.ConsoleColors.WHITE_BOLD))
                .append("\tDisplay help")
                .append("\n");

        builder.append("\t")
                .append(Display.getColorizedString(
                        Quit, Display.ConsoleColors.WHITE_BOLD))
                .append("\tExit the diagnostic generator")
                .append("\n");

        return builder.toString();
    }

    public static boolean isValid(String option) {
        return option.equals(Diagnostic)
            || option.equals(Help)
            || option.equals(Quit);
    }

}
