package repository;

import dao.hpo.HpoDao;
import dao.omim.OmimDao;

/**
 * Disease repository providing entry points for Disease fetching and creation
 */
public class DiseaseRepository extends RepositoryBase {

    /**
     * Private DAO to query the ATC sources
     */
    private final OmimDao _omimDAO;

    private final HpoDao _hpoDAO;

    public DiseaseRepository() {
        _omimDAO = new OmimDao();
        _hpoDAO = new HpoDao();
    }

}
