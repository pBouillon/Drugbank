package dao.atc;

import common.pojo.Drug;
import dao.DataAccessObjectBase;
import org.apache.lucene.document.Document;

import java.nio.file.Path;

/**
 * DAO for the ATC data source
 */
public class AtcDao extends DataAccessObjectBase<Drug> {

    /**
     * Default constructor
     * @see DataAccessObjectBase
     */
    protected AtcDao(Path indexDirectory) {
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
