package repository;

import dao.atc.AtcDao;
import dao.omim.OmimDao;

/**
 * Disease repository providing entry points for Disease fetching and creation
 */
public class DiseaseRepository extends RepositoryBase {

    /**
     * Private DAO to query the ATC sources
     */
    private final OmimDao _omimDAO;

    public DiseaseRepository() {
        _omimDAO = new OmimDao();
    }

}
