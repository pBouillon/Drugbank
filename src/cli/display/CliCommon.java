package cli.display;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class CliCommon {

    /**
     * Scanner to fetch user inputs
     */
    public static final Scanner input = new Scanner(System.in);

    /**
     * Default symbol to print before the user input
     */
    public static final String userCursor
            = Display.getColorizedString("? ", Display.ConsoleColors.BLUE_BOLD);

    /**
     * Project name in ASCII art
     * Generated with: http://patorjk.com/software/taag
     */
    public static final String stylizedName =
            "  ____  _                             _   _         ____            \n" +
            " |  _ \\(_) __ _  __ _ _ __   ___  ___| |_(_) ___   / ___| ___ _ __  \n" +
            " | | | | |/ _` |/ _` | '_ \\ / _ \\/ __| __| |/ __| | |  _ / _ \\ '_ \\ \n" +
            " | |_| | | (_| | (_| | | | | (_) \\__ \\ |_| | (__  | |_| |  __/ | | |\n" +
            " |____/|_|\\__,_|\\__, |_| |_|\\___/|___/\\__|_|\\___|  \\____|\\___|_| |_|\n" +
            "                |___/                                               \n";

    /**
     * Get a text separator for the CLI
     * @return A separator with the width of the longest line of the stylized name
     */
    public static String getScreenSeparator() {
        int nameBiggestRow = Arrays.stream(CliCommon.stylizedName.split("\n"))
                .max(Comparator.comparingInt(String::length))
                .map(String::length)
                .orElse(15);

        return "─".repeat(Math.max(0, nameBiggestRow));
    }

    /**
     * Get the user input after display
     * @return The value specified by the user
     */
    public static String getUserCliInput() {
        System.out.print(userCursor);
        return input.nextLine();
    }

}
