package dao.sider_4_1;

import common.pojo.Drug;
import dao.DataAccessObjectBase;
import org.apache.lucene.document.Document;

import java.nio.file.Path;

/**
 * DAO for the Sider data source
 */
public class SiderDao extends DataAccessObjectBase<Drug> {

    /**
     * Default constructor
     * @see DataAccessObjectBase
     */
    protected SiderDao(Path indexDirectory) {
        super(indexDirectory);
    }

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

    @Override
    public Document getAsDocument(Drug sourceObject) {
        return null;
    }
}
