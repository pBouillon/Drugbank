import factory.RepositoryFactorySingleton;
import repository.DrugRepository;

public class Main {

    public static void main(String[] args) {

        DrugRepository drugRepository =  RepositoryFactorySingleton.instance.getDrugRepository();

        System.out.println("Hello World !");
    }

}
