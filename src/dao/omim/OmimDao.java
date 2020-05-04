package dao.omim;

import dao.DataAccessObjectBase;

/**
 * DAO for the OMIM data source
 */
public class OmimDao extends DataAccessObjectBase {

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
