package dao.sider_4_1;

import dao.DataAccessObjectBase;

/**
 * DAO for the Sider data source
 */
public class SiderDao extends DataAccessObjectBase {

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
