package dao.hpo;

import dao.DataAccessObjectBase;

/**
 * DAO for the HPO data source
 */
public class HpoDao extends DataAccessObjectBase {

    /**
     * @inheritDoc
     */
    @Override
    protected void initializeIndexing() { }

    /**
     * @inheritDoc
     */
    @Override
    protected boolean isDataSourceIndexed() {
        return false;
    }

}
