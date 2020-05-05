package repository;

import dao.drugbank.DrugBankDao;

/**
 * Drug repository providing entry points for Drug fetching and creation
 */
public class DrugRepository extends RepositoryBase {

    /**
     * Private DAO to query the drugbank sources
     */
    private final DrugBankDao _drugBankDao;

    /**
     * Default constructor
     * Initialize the DAOs
     */
    public DrugRepository() {
        _drugBankDao = new DrugBankDao();
    }

}
