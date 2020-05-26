package cli.display;

/**
 * Toolbox for color generation
 */
public class Display {

    /**
     * From: https://stackoverflow.com/a/45444716
     */
    public static class ConsoleColors {

        // Reset
        public static final String RESET = "\033[0m";

        // Regular Colors
        public static final String RED = "\033[0;31m";
        public static final String GREEN = "\033[0;32m";
        public static final String YELLOW = "\033[0;33m";
        public static final String BLUE = "\033[0;34m";
        public static final String PURPLE = "\033[0;35m";
        public static final String CYAN = "\033[0;36m";
        public static final String WHITE = "\033[0;37m";

        // Bold
        public static final String RED_BOLD = "\033[1;31m";
        public static final String GREEN_BOLD = "\033[1;32m";
        public static final String YELLOW_BOLD = "\033[1;33m";
        public static final String BLUE_BOLD = "\033[1;34m";
        public static final String PURPLE_BOLD = "\033[1;35m";
        public static final String CYAN_BOLD = "\033[1;36m";
        public static final String WHITE_BOLD = "\033[1;37m";

    }

    /**
     * Get the colorized version of the string
     * @param toColorize string to colorized
     * @param colorCode color to apply on the string
     * @return The ANSI flavored string
     * @see ConsoleColors
     */
    public static String getColorizedString(String toColorize, String colorCode) {
        return colorCode + toColorize + ConsoleColors.RESET;
    }

}
