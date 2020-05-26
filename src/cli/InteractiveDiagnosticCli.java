package cli;

import diagnostic.DiagnosticManager;
import diagnostic.request.DiagnosticRequest;
import diagnostic.response.DiagnosticResponse;

/**
 * CLI dedicated to the diagnostic generation
 */
public class InteractiveDiagnosticCli {

    /**
     * Diagnostic manager instance to interact with the application logic
     */
    private final DiagnosticManager _diagnosticManager = new DiagnosticManager();

    private final DiagnosticRequest _diagnosticRequest = new DiagnosticRequest();

    private final DiagnosticResponse _diagnosticResponse = new DiagnosticResponse();

    private static String getStringifiedUserEffect() {
        System.out.println("Please specify each of your symptoms, separated by a `,`:");
        return null;
    }

    public static void startInteractiveDiagnostic() {
        String userEffects = getStringifiedUserEffect();

        // Leave an empty line for readability
        System.out.println();
    }

}
