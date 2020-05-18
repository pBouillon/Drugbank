package diagnostic;

import factory.RepositoryFactory;
import factory.RepositoryFactorySingleton;

/**
 * Diagnostic manager entity to perform diagnostic based on response/request POJOs
 * @see DiagnosticRequest
 * @see DiagnosticResponse
 */
public class DiagnosticManager {

    /**
     * Provider for the various repositories
     * @see RepositoryFactorySingleton
     */
    private RepositoryFactory _repositoryFactory = RepositoryFactorySingleton.instance;

    /**
     * Core method to generate a diagnostic from a request
     * @param diagnosticRequest Diagnostic request holding the information on which base the diagnostic process
     * @return A DiagnosticResponse holding the diagnostic
     */
    public static DiagnosticResponse generateDiagnostic(DiagnosticRequest diagnosticRequest) {
        return null;
    }

}
