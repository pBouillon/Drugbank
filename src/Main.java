import factory.RepositoryFactorySingleton;
import repository.DrugRepository;

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

    }

}
