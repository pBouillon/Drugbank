package repository;

import org.apache.lucene.index.IndexReader;
import util.lucene.searcher.LuceneSearcherBase;

import java.util.Map;


/**
 * Base class for repositories objects querying at least one DAO
 * to fetch data from several sources
 * @see dao.DataAccessObjectBase
 */
public abstract class RepositoryBase<T> extends LuceneSearcherBase<T> {

    /**
     * Default constructor
     * @param indexReaders IndexReaders on which the LuceneSearcherBase will operate
     * @see LuceneSearcherBase
     */
    protected RepositoryBase(IndexReader... indexReaders) {
        super(indexReaders);
    }

    /**
     * Merge an objects in a records collection
     * If already existing, add only the missing fields
     * @param recordsMap Map of all objects with their names as keys
     * @param toMerge Object to merge in the collection
     */
    protected abstract void mergeResult(Map<String, T> recordsMap, T toMerge);

}
