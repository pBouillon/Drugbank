package dao.sider_4_1;

import common.Configuration;
import common.pojo.Drug;
import dao.DataAccessObjectBase;
import org.apache.lucene.document.Document;
import util.indexer.IIndexer;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * DAO for the Sider data source
 */
public class SiderDao extends DataAccessObjectBase<Drug> implements IIndexer<Drug> {

    /**
     * Data extractor for MeDRA databases
     */
    private MeDRAExtractor _extractor;

    /**
     * Default constructor
     * @see DataAccessObjectBase
     */
    public SiderDao() {
        super(Paths.get(Configuration.Sider.Paths.INDEX));
    }

    /**
     * Initialize the data extractor from MeDRA databases
     *
     * @inheritDoc
     */
    @Override
    protected void initialize() {
        _extractor = new MeDRAExtractor();
    }

    /**
     * @inheritDoc
     */
    @Override
    protected void initializeIndexing() {
        List<Drug> drugs = _extractor.extract();
    }

    /**
     * @inheritDoc
     */
    @Override
    public Document getAsDocument(Drug sourceObject) {
        return null;
    }
}
