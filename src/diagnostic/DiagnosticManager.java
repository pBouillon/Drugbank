package diagnostic;

import common.pojo.Disease;
import common.pojo.Drug;
import diagnostic.request.DiagnosticRequest;
import diagnostic.response.DiagnosticResponse;
import diagnostic.response.IDiagnosableEntity;
import factory.RepositoryFactory;
import factory.RepositoryFactorySingleton;
import util.Lazy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        DiagnosticResponse response = new DiagnosticResponse();
        Map<IDiagnosableEntity, Lazy<List<Drug>>> cures = response.getCures();

        // Fetch all potential causes
        List<IDiagnosableEntity> causes = new ArrayList<>();
        causes.addAll(diagnoseDiseases(diagnosticRequest));
        causes.addAll(diagnoseDrugs(diagnosticRequest));

        // Register all potential causes of the effect specified in the request
        causes.forEach(cause
                -> cures.put(cause, new Lazy<>()));

        // Gather the diagnostic for each of one of the causes
        cures.forEach((cause, cure)
                -> cure.setSupplier(() -> getCureFor(cause)));

        return response;
    }

    /**
     * TODO
     * Diagnose all disease that may cause the effect specified in the request
     * @param diagnosticRequest Diagnostic request holding the information on which base the diagnostic process
     * @return A list of all the disease that may cause the effect
     */
    private static List<Disease> diagnoseDiseases(DiagnosticRequest diagnosticRequest) {
        List<Disease> diseaseCauses = new ArrayList<>();

        // TODO: fetch from repositories

        return diseaseCauses;
    }

    /**
     * TODO
     * Diagnose all drugs that may cause the effect specified in the request
     * @param diagnosticRequest Diagnostic request holding the information on which base the diagnostic process
     * @return A list of all the drugs that may cause the effect
     */
    private static List<Drug> diagnoseDrugs(DiagnosticRequest diagnosticRequest) {
        List<Drug> drugCauses = new ArrayList<>();

        // TODO: fetch from repositories

        return drugCauses;
    }

    /**
     * TODO
     * @param cause
     * @return
     */
    private static List<Drug> getCureFor(IDiagnosableEntity cause) {
        List<Drug> cures = new ArrayList<>();

        // TODO: fetch from repositories

        return cures;
    }

}
