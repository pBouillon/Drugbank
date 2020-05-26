package diagnostic;

import common.Configuration;
import common.pojo.Disease;
import common.pojo.Drug;
import common.pojo.Symptom;
import diagnostic.request.DiagnosticRequest;
import diagnostic.response.DiagnosticResponse;
import diagnostic.response.IDiagnosableEntity;
import lucene.searcher.SearchParam;
import repository.factory.RepositoryFactory;
import repository.factory.RepositoryFactorySingleton;
import util.Lazy;

import java.util.*;

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
    private static final RepositoryFactory _repositoryFactory
            = RepositoryFactorySingleton.instance;

    /**
     * Core method to generate a diagnostic from a request
     * @param diagnosticRequest Diagnostic request holding the information on which base the diagnostic process
     * @return A DiagnosticResponse holding the diagnostic
     */
    public static DiagnosticResponse generateDiagnostic(DiagnosticRequest diagnosticRequest) {
        DiagnosticResponse response = new DiagnosticResponse();
        Map<IDiagnosableEntity, Lazy<List<Drug>>> cures = response.getCures();

        // Force repository population
        _repositoryFactory.initializeRepositories();

        // Extract the associated symptoms
        List<List<Symptom>> associatedSymptoms = getAssociatedSymptoms(diagnosticRequest);

        // Fetch all potential causes
        List<IDiagnosableEntity> causes = new ArrayList<>();
        causes.addAll(diagnoseDiseasesFor(associatedSymptoms));
        //causes.addAll(diagnoseDrugsFor(associatedSymptoms));

        // Register all potential causes of the effect specified in the request
        causes.forEach(cause
                -> cures.put(cause, new Lazy<>()));

        // Gather the diagnostic for each of one of the causes
        cures.forEach((cause, cure)
                -> cure.setSupplier(() -> getCureFor(cause)));

        return response;
    }

    /**
     * Diagnose all disease that may cause the effect specified in the request
     * @param symptoms Symptoms to be associated with a disease
     * @return A list of all the disease that may cause the effect
     */
    private static List<Disease> diagnoseDiseasesFor(List<List<Symptom>> symptoms) {
        // Buffer for query parameters relative to each symptom
        List<SearchParam> searchParams;

        // Retrieve all diseases that may cause each symptom
        List<Disease> diseasesForCurrentSymptom;

        searchParams = _repositoryFactory
                .getDiseaseRepository()
                .generateSearchParamsFromSymptom(symptoms);

        diseasesForCurrentSymptom = _repositoryFactory
                .getDiseaseRepository()
                .getEntities(
                        searchParams.toArray(SearchParam[]::new));

        // Since diseaseCauses is a set, remove any duplicate
        Set<Disease> diseaseCauses = new HashSet<>(diseasesForCurrentSymptom);


        return new ArrayList<>(diseaseCauses);
    }

    /**
     * Diagnose all drugs that may cause the effect specified in the request
     * @param symptoms Symptoms to be associated with a drug's side effect
     * @return A list of all the drugs that may cause the effect
     */
    private static List<Drug> diagnoseDrugsFor(List<Symptom> symptoms) {
        Set<Drug> drugCauses = new HashSet<>();

        // Buffer for query parameters relative to each symptom
        List<SearchParam> searchParams;

        // Retrieve all diseases that may cause each symptom
        List<Drug> drugForCurrentSymptom;
        for (Symptom symptom : symptoms) {
            searchParams = _repositoryFactory
                    .getDrugRepository()
                    .generateSearchParamsFromSymptom(symptom);

            drugForCurrentSymptom = _repositoryFactory
                    .getDrugRepository()
                    .getEntities(
                            searchParams.toArray(SearchParam[]::new));

            // Since diseaseCauses is a set, remove any duplicate
            drugCauses.addAll(drugForCurrentSymptom);
        }

        return new ArrayList<>(drugCauses);
    }

    /**
     * Get all symptoms that are matching the described undesirable effect in the request
     * @param diagnosticRequest User's diagnostic request
     * @return A List of all the symptoms matching the user's request
     * @see Symptom
     */
    private static List<List<Symptom>> getAssociatedSymptoms(DiagnosticRequest diagnosticRequest) {
        List<List<Symptom>> associatedMap = new ArrayList<>();
        for (String request : diagnosticRequest.getUndesirableEffect()) {
            associatedMap.add(_repositoryFactory
                    .getSymptomRepository()
                    .getEntities(
                            new SearchParam(Configuration.Lucene.IndexKey.Symptom.NAME,
                                    request)));
        }
        return associatedMap;
    }

    /**
     * Get the all cures for the side effect of the provided disease
     * @param cause Disease to cure
     * @return A list of all drugs that may cure the cause
     */
    private static List<Drug> getCureFor(Disease cause) {
        List<Drug> cures = new ArrayList<>();

        // TODO: fetch from repositories

        return cures;
    }

    /**
     * Get the all cures for the side effect of the provided drug
     * @param cause Drug to cure
     * @return A list of all drugs that may cure the cause
     */
    private static List<Drug> getCureFor(Drug cause) {
        List<Drug> cures = new ArrayList<>();

        // TODO: fetch from repositories

        return cures;
    }

    /**
     * Get the all cures for the provided cause
     * @param cause IDiagnosableEntity to cure, either Disease or Drug
     * @return A list of all drugs that may cure the cause
     * @see IDiagnosableEntity
     * @see Disease
     * @see Drug
     */
    private static List<Drug> getCureFor(IDiagnosableEntity cause) {
        return cause instanceof Disease
                ? getCureFor((Disease) cause)
                : getCureFor((Drug) cause);
    }

}
