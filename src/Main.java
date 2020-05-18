import diagnostic.DiagnosticManager;
import diagnostic.request.DiagnosticRequest;

/**
 * Program startup class
 */
public class Main {

    /**
     * Program's entry point
     * @param args Command line arguments
     */
    public static void main(String[] args) {

        // Dummy request for pre-prod test purposes
        DiagnosticRequest diagnosticRequest = new DiagnosticRequest();
        diagnosticRequest.setUndesirableEffect("headache");

        DiagnosticManager.generateDiagnostic(diagnosticRequest);
    }

}
