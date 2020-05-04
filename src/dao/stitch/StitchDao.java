package dao.stitch;

import dao.DataAccessObjectBase;

/**
 * DAO for the STITCH data source
 */
public class StitchDao extends DataAccessObjectBase {

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
