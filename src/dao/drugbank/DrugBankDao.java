package dao.drugbank;

import dao.DataAccessObjectBase;

/**
 * DAO for the DrugBank data source
 */
public class DrugBankDao extends DataAccessObjectBase {

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
