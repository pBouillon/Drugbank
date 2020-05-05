package repository;

import dao.drugbank.DrugBankDao;
import dao.stitch.StitchDao;

/**
 * Drug repository providing entry points for Drug fetching and creation
 */
public class DrugRepository extends RepositoryBase {

    /**
     * Private DAO to query the drugbank sources
     */
    private final DrugBankDao _drugBankDao;

    private final StitchDao _stitchDAO;

    /**
     * Default constructor
     * Initialize the DAOs
     */
    public DrugRepository() {
        _drugBankDao = new DrugBankDao();
        _stitchDAO = new StitchDao();
    }

}
