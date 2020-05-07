package repository;

import dao.atc.AtcDao;
import dao.drugbank.DrugBankDao;
import dao.stitch.StitchDao;

/**
 * Drug repository providing entry points for Drug fetching and creation
 */
public class DrugRepository extends RepositoryBase {

    /**
     * Private DAO to query the ATC sources
     */
    private final AtcDao _atcDAO;

    /**
     * Private DAO to query the drugbank sources
     */
    private final DrugBankDao _drugBankDao;

    /**
     * Private DAO to query the STITCH sources
     */
    private final StitchDao _stitchDAO;

    /**
     * Default constructor
     * Initialize the DAOs
     */
    public DrugRepository() {
        _atcDAO = new AtcDao();
        _drugBankDao = new DrugBankDao();
        _stitchDAO = new StitchDao();
    }

}
