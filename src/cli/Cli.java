package cli;

import cli.display.CliCommon;
import cli.display.Display;
import cli.options.UserOptions;
import common.Configuration;
import repository.factory.RepositoryFactorySingleton;

/**
 * Default command line interface for the diagnostic generator
 */
public class Cli implements Runnable {

    /**
     * Perform the action requested by the user
     * @param userOption Option to process
     */
    private void handleUserOption(String userOption) {
        switch (userOption) {
            case UserOptions.Diagnostic:
                InteractiveDiagnosticCli.startInteractiveDiagnostic();
                break;

            case UserOptions.Help:
                showUserOptions();
                break;

            case UserOptions.Quit:
                System.exit(0);
        }
    }

    /**
     * Ask and retrieve the user option
     * @return The option the user has chosen
     */
    private String getUserOption() {
        boolean isOptionValid;
        String userOption;

        do {
            System.out.print(CliCommon.userCursor);
            userOption = CliCommon.input.nextLine();

            // Leave an empty line for readability
            System.out.println();

            isOptionValid = UserOptions.isValid(userOption);

            if (!isOptionValid) {
                System.out.println(
                        Display.getColorizedString(
                                "Unrecognized option (try 'h' for help)",
                                Display.ConsoleColors.RED)
                );
            }
        } while (!isOptionValid);

        return userOption;
    }

    /**
     * Infinite listener for the user to interact with
     */
    private void poll() {
        String firstMessage =
                "Welcome in the " +
                Display.getColorizedString("Diagnostic Gen", Display.ConsoleColors.BLUE) + "erator CLI.\n" +
                "(Type 'h' for help and 'q' to quit)\n";
        System.out.println(firstMessage);

        String userOption;
        for (;;) {
            userOption = getUserOption();
            handleUserOption(userOption);
        }
    }

    /**
     * Shows the greeting messages and the project's information
     */
    private void showGreetings() {
        StringBuilder greeter = new StringBuilder();

        // Generate greeting name
        greeter.append(Display.getColorizedString(
                CliCommon.stylizedName, Display.ConsoleColors.BLUE))
                .append("\n");

        // Project's authors
        greeter.append(
                    Display.getColorizedString(
                            "Authors: ", Display.ConsoleColors.WHITE_BOLD))
                .append("\n")
                .append("\t- Antoine Jacque\n")
                .append("\t- Mathieu Dreyer\n")
                .append("\t- Pierre Bouillon\n")
                .append("\n");

        // Project's version
        greeter.append(
                    Display.getColorizedString(
                            "Version: ", Display.ConsoleColors.WHITE_BOLD))
                .append(Configuration.VERSION)
                .append("\n");

        // Ends presentation section
        greeter.append(CliCommon.getScreenSeparator());

        System.out.println(greeter.toString());
    }

    /**
     * Start and show Lucene indexing process
     */
    private void startAndShowIndexingProcess() {
        System.out.println("Starting Lucene indexation ...");

        long start = System.currentTimeMillis();
        RepositoryFactorySingleton.instance.initializeRepositories();
        long end = System.currentTimeMillis();

        System.out.println("Done in " + (end - start) / 1000. + " sc");
        System.out.println(CliCommon.getScreenSeparator());
    }

    /**
     * Display user options and effects
     */
    private void showUserOptions() {
        System.out.println(UserOptions.getAllDescribedOptions());
    }

    /**
     * Run the CLI
     */
    @Override
    public void run() {
        showGreetings();
        startAndShowIndexingProcess();
        poll();
    }

}
