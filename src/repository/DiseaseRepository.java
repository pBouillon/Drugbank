package repository;

import dao.hpo.HpoDao;

/**
 * Disease repository providing entry points for Disease fetching and creation
 */
public class DiseaseRepository extends RepositoryBase {
    /**
     * Private DAO to query the HPO sources
     */
    private final HpoDao _hpoDAO;

    /**
     * Default constructor
     * Initialize the DAOs
     */
    public DiseaseRepository() {
        _hpoDAO = new HpoDao();
    }
}
