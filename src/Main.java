import diagnostic.DiagnosticManager;
import diagnostic.request.DiagnosticRequest;
import repository.factory.RepositoryFactory;
import repository.factory.RepositoryFactorySingleton;

/**
 * Program startup class
 */
public class Main {

    /**
     * Program's entry point
     * @param args Command line arguments
     */
    public static void main(String[] args) {

        // Force-init Lucene indexing (see  #13)
        RepositoryFactory repositoryFactory = RepositoryFactorySingleton.instance;
        repositoryFactory.getDiseaseRepository();
        repositoryFactory.getDrugRepository();
        repositoryFactory.getSymptomRepository();

        // Dummy request for pre-prod test purposes
        DiagnosticRequest diagnosticRequest = new DiagnosticRequest();
        diagnosticRequest.setUndesirableEffect("headache");

        DiagnosticManager.generateDiagnostic(diagnosticRequest);
    }

}
