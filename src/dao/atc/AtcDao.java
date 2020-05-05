package dao.atc;

import dao.DataAccessObjectBase;

/**
 * DAO for the ATC data source
 */
public class AtcDao extends DataAccessObjectBase {

    /**
     * @inheritDoc
     */
    @Override
    protected void initialize() { }

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
