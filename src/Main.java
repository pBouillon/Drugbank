import factory.RepositoryFactory;
import factory.RepositoryFactorySingleton;
import repository.DiseaseRepository;
import repository.DrugRepository;
import repository.SymptomRepository;

/**
 * Program startup class
 */
public class Main {

    /**
     * Program's entry point
     * @param args Command line arguments
     */
    public static void main(String[] args) {

        RepositoryFactory repositories = RepositoryFactorySingleton.instance;

        // Get an instance of the Disease repository
        DiseaseRepository diseaseRepository =  repositories.getDiseaseRepository();

        // Get an instance of the Drug repository
        DrugRepository drugRepository =  repositories.getDrugRepository();

        // Get an instance of the Symptom repository
        SymptomRepository symptomRepository =  repositories.getSymptomRepository();

    }

}
