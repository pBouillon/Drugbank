package dao.sider_4_1;

import common.pojo.Drug;
import dao.DataAccessObjectBase;
import org.apache.lucene.document.Document;

/**
 * DAO for the Sider data source
 */
public class SiderDao extends DataAccessObjectBase<Drug> {

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

    @Override
    public Document getAsDocument(Drug sourceObject) {
        return null;
    }
}
