package diagnostic;

import factory.RepositoryFactory;
import factory.RepositoryFactorySingleton;

public class DiagnosticManager {

    RepositoryFactory repositoryFactory = RepositoryFactorySingleton.instance;

    public static DiagnosticResponse generateDiagnostic(DiagnosticRequest diagnosticRequest) {
        return null;
    }

}
