package dao.omim;

import common.pojo.Drug;
import dao.DataAccessObjectBase;
import org.apache.lucene.document.Document;

import java.nio.file.Path;

/**
 * DAO for the OMIM data source
 */
public class OmimDao extends DataAccessObjectBase<Drug> {

    /**
     * Default constructor
     * @see DataAccessObjectBase
     */
    protected OmimDao(Path indexDirectory) {
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
