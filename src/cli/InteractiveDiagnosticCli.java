package cli;

import cli.display.CliCommon;
import cli.display.Display;
import common.pojo.Disease;
import common.pojo.Drug;
import diagnostic.DiagnosticManager;
import diagnostic.request.DiagnosticRequest;
import diagnostic.response.DiagnosticResponse;
import diagnostic.response.IDiagnosableEntity;
import lucene.searcher.LuceneSearcherBase;
import util.Lazy;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * CLI dedicated to the diagnostic generation
 */
public class InteractiveDiagnosticCli {

    /**
     * Default output file for the diagnosed results
     */
    private static final String DEFAULT_OUTPUT_FILE = "diagnostic_results.json";

    /**
     * Number of field to preview to the user
     */
    private static final int NUMBER_OF_FIELDS_TO_PREVIEW = 3;

    /**
     * Maximum size of the string before being trunked when displayed
     */
    private static final int TRUNKED_SIZE_LIMIT = 80;

    /**
     * Symbol to display at the end of trunked strings
     */
    private static final String TRUNKED_SYMBOL = "...";

    /**
     * Allows the user to export its data
     * @param response User diagnostic
     */
    private static void exportResults(DiagnosticResponse response) {
        String userInput;

        System.out.println("Would you like to export the results ? (Y/n)");
        userInput = CliCommon.getUserCliInput().toUpperCase();

        if (userInput.equals("N")) {
            return;
        }

        Path outFile = Paths.get(DEFAULT_OUTPUT_FILE);

        try {
            if (!Files.exists(outFile)) {
                Files.createFile(outFile);
            }
        } catch (IOException e) {
            System.err.println("Unable to create the output file");
        }

        try {
            Files.write(outFile, getJsonifiedResponse(response));
        } catch (IOException e) {
            System.err.println("Unable to write the diagnostic in the desired file");
        }

        System.out.print("\nExporting results ... ");

        System.out.println(
                "Done\n" +
                "Results exported to: "
                + Display.getColorizedString(DEFAULT_OUTPUT_FILE, Display.ConsoleColors.WHITE_BOLD)
                + "\n");

    }

    /**
     * Generate the diagnostic for the user's diagnostic request
     * @param request User's diagnostic request
     * @return The associated diagnostic response
     */
    private static DiagnosticResponse getAndShowDiagnosticFor(DiagnosticRequest request) {
        DiagnosticResponse response;

        System.out.print("Evaluating potential causes and their cures ... ");

        long start = System.currentTimeMillis();
        response = DiagnosticManager.generateDiagnostic(request);
        long end = System.currentTimeMillis();

        System.out.println("Found in " + (end - start) / 1000. + " sc\n");

        return response;
    }

    /**
     * Generate a JSON of the provided response
     * @param response User diagnostic
     * @return The JSON of the response with each element being a line of the JSON
     */
    private static Stack<String> getJsonifiedResponse(DiagnosticResponse response) {
        Stack<String> jsonifiedDiagnostic = new Stack<>();

        jsonifiedDiagnostic.add("{");
        jsonifiedDiagnostic.add("\t\"causes\": [");

        for (Iterator<Map.Entry<IDiagnosableEntity, Lazy<List<Drug>>>> it = response.getCures().entrySet().iterator()
             ; it.hasNext(); ) {
            Map.Entry<IDiagnosableEntity, Lazy<List<Drug>>> entry = it.next();

            jsonifiedDiagnostic.add(
                    "\t\t{"
            );

            jsonifiedDiagnostic.add(
                    "\t\t\t\"type\": \""
                    + (entry.getKey() instanceof Disease ? "Disease" : "Drug")
                    + "\",");

            jsonifiedDiagnostic.add(
                    "\t\t\t\"name\": \""
                    + entry.toString()
                    + "\",");

            jsonifiedDiagnostic.add(
                    "\t\t\t\"cures\": ["
                    + entry.getValue()
                            .getOrCompute()
                            .stream()
                            .map(Drug::toString)
                            .collect(Collectors.joining(", "))
                    + "]");

            jsonifiedDiagnostic.add(
                    "\t\t}"
                    + (it.hasNext() ? "," : ""));
        }

        jsonifiedDiagnostic.add("\t]");
        jsonifiedDiagnostic.add("}");

        return jsonifiedDiagnostic;
    }

    /**
     * Get a pre-formatted string for Lucene searching with the user's symptoms
     * @return The pre-formatted string
     * @see LuceneSearcherBase getFieldForLuceneExactSearchOn method for string formatted
     */
    private static String getStringifiedUserEffect() {
        System.out.println("Specify each of your symptoms, separated by a comma (',') :");
        String userEffects = "placeholder";

        do {
            if (userEffects.isBlank()) {
                System.out.println(Display.getColorizedString(
                        "You must provide at least one of the symptoms that you are experiencing\n",
                        Display.ConsoleColors.RED));
            }
            userEffects = CliCommon.getUserCliInput();

        } while (userEffects.isBlank());

        System.out.println();

        return Arrays.stream(userEffects.split(","))
                .map(effect
                        -> LuceneSearcherBase.getFieldForLuceneExactSearchOn(
                            effect.strip()))
                .collect(Collectors.joining(" "));
    }

    /**
     * Starts the interactive diagnostic process
     */
    public static void startInteractiveDiagnostic() {
        String userEffects = getStringifiedUserEffect();

        DiagnosticResponse diagnosticResponse = getAndShowDiagnosticFor(new DiagnosticRequest(userEffects));

        showResult(diagnosticResponse);
        exportResults(diagnosticResponse);
    }

    /**
     * Give a short overview of the user diagnostic
     * @param response User diagnostic
     */
    private static void showResult(DiagnosticResponse response) {
        StringBuilder overview = new StringBuilder();

        int causesSize = response.getCures().size();

        overview.append("These symptoms may be caused by ")
                .append(Display.getColorizedString(
                        causesSize + " different cause" + (causesSize > 1 ? "s" : ""),
                        Display.ConsoleColors.BLUE))
                .append(":\n");

        int causesDiseaseSize = response.getDiseaseCauses().size();
        overview.append("\t- ")
                .append(Display.getColorizedString(
                        String.valueOf(causesDiseaseSize), Display.ConsoleColors.BLUE))
                .append(causesDiseaseSize > 1 ? " are" : " is")
                .append(Display.getColorizedString(
                        " disease" + (causesDiseaseSize > 1 ? "s" : ""), Display.ConsoleColors.BLUE))
                .append("\n");

        int causesDrugsSize = response.getDrugCauses().size();
        overview.append("\t- ")
                .append(Display.getColorizedString(
                        String.valueOf(causesDrugsSize), Display.ConsoleColors.BLUE))
                .append(causesDrugsSize > 1 ? " are" : " is")
                .append(Display.getColorizedString(
                        " drug" + (causesDrugsSize > 1 ? "s" : ""), Display.ConsoleColors.BLUE));

        overview.append("\n").append("\n")
                .append(showResultsPreview(response));

        System.out.println(overview.toString());
    }

    /**
     * Show some a bunch of trunked results to the user
     * @param response The user's diagnostic
     */
    private static String showResultsPreview(DiagnosticResponse response) {
        StringBuilder preview = new StringBuilder();
        preview.append("Some of these causes are: \n");

        String row;
        int previewedCount = 0;
        for (Map.Entry<IDiagnosableEntity, Lazy<List<Drug>>> entry : response.getCures().entrySet()) {
            if (previewedCount == NUMBER_OF_FIELDS_TO_PREVIEW) {
                break;
            }

            // TODO: uncomment on cures bind to causes
//            // Skip values with no cures attached for preview
//            if (entry.getValue().getOrCompute().size() == 0) {
//                continue;
//            }
            ++previewedCount;

            row = "\t";

            row += entry.getKey() instanceof Disease
                    ? Display.getColorizedString("[Disease] ", Display.ConsoleColors.BLUE)
                    : Display.getColorizedString("[Drug]    ", Display.ConsoleColors.BLUE);

            row += Display.getColorizedString(
                entry.getKey().toString() + ": ", Display.ConsoleColors.BLUE_BOLD);

            row += entry.getValue()
                    .getOrCompute()
                    .stream()
                    .map(Drug::toString)
                    .collect(Collectors.joining(", "));

            row += "\n";

            if (row.length() > TRUNKED_SIZE_LIMIT) {
                row = row.substring(0, TRUNKED_SIZE_LIMIT - TRUNKED_SYMBOL.length()) + TRUNKED_SYMBOL;
            }

            preview.append(row);
        }

        return preview.toString();
    }

}
