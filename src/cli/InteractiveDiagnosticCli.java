package cli;

import cli.display.CliCommon;
import cli.display.Display;
import diagnostic.DiagnosticManager;
import diagnostic.request.DiagnosticRequest;
import diagnostic.response.DiagnosticResponse;
import lucene.searcher.LuceneSearcherBase;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * CLI dedicated to the diagnostic generation
 */
public class InteractiveDiagnosticCli {

    private static DiagnosticResponse getAndShowDiagnosticFor(DiagnosticRequest request) {
        DiagnosticResponse response;

        System.out.print("Evaluating potential causes and their cures ... ");

        long start = System.currentTimeMillis();
        response = DiagnosticManager.generateDiagnostic(request);
        long end = System.currentTimeMillis();

        System.out.println("Found in " + (end - start) / 1000. + " sc\n");

        return response;
    }

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

    public static void startInteractiveDiagnostic() {
        String userEffects = getStringifiedUserEffect();

        DiagnosticResponse diagnosticRequest = getAndShowDiagnosticFor(new DiagnosticRequest(userEffects));

        showOverview(diagnosticRequest);
    }

    private static void showOverview(DiagnosticResponse response) {
        StringBuilder overview = new StringBuilder();

        int causesSize = response.getCures().size();

        overview.append("This symptoms may be caused by ")
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

        System.out.println(overview.append("\n").toString());
    }

}
