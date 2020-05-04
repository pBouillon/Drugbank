package dao;

import org.apache.lucene.index.IndexWriter;

/**
 * Base class for Data Access Objects (DAO)
 */
public abstract class DataAccessObjectBase {

    /**
     * Default constructor
     * Check if the data source queried by the concrete DAO is already indexed
     * and if not starts the indexing
     */
    protected DataAccessObjectBase() {
        if (!isDataSourceIndexed()) {
            initializeIndexing();
        }
    }

    /**
     * Index the data source in the concrete DAO
     */
    protected abstract void initializeIndexing();

    /**
     * Check whether the data source queried is already indexed or not
     * @return True if the indexing is already done; False otherwise
     */
    protected abstract boolean isDataSourceIndexed();

}
