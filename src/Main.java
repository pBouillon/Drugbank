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

        // Get an instance of the Drug repository
        DrugRepository drugRepository =  RepositoryFactorySingleton.instance.getDrugRepository();
        SymptomRepository symptomRepository =  RepositoryFactorySingleton.instance.getSymptomRepository();
        DiseaseRepository diseaseRepository =  RepositoryFactorySingleton.instance.getDiseaseRepository();



    }

}
