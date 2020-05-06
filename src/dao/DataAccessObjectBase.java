package dao;

import util.indexer.IndexerBase;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Base class for Data Access Objects (DAO)
 * @param <T> The type of the data accessed
 */
public abstract class DataAccessObjectBase<T> extends IndexerBase<T> {

    /**
     * Default constructor
     * Check if the data source queried by the concrete DAO is already indexed
     * and if not starts the indexing
     */
    protected DataAccessObjectBase(Path indexDirectory) {
        super(indexDirectory);

        initialize();

        if (!isDataSourceIndexed()) {
            initializeIndexing();
        }
    }

    /**
     * Initialize the DAO paths or connexions
     */
    protected abstract void initialize();

    /**
     * Index the data source in the concrete DAO
     */
    protected abstract void initializeIndexing();

    /**
     * Check whether the data source queried is already indexed or not
     * @return True if the indexing is already done; False otherwise
     */
    protected boolean isDataSourceIndexed() {
        // Return true if the folder containing the indexes is not empty
        try {
            return Files.list(indexesDirectory)
                    .findAny()
                    .isPresent();
        } catch (IOException e) {
            return false;
        }
    }

}
