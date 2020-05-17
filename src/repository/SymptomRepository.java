package repository;

import dao.hp.HpDao;
import dao.sider_4_1.SiderDao;

/**
 * Symptom repository providing entry points for Symptom fetching and creation
 */
public class SymptomRepository extends RepositoryBase {

    /**
     * Private DAO to query the SIDER sources (MeDRA)
     */
    private final SiderDao _siderDao;

    /**
     * Private DAO to query the SIDER sources (MeDRA)
     */
    private final HpDao _hpDao;

    /**
     * Default constructor
     * Initialize the DAOs
     */
    public SymptomRepository() {
        _siderDao = new SiderDao();
        _hpDao = new HpDao();
    }

}
