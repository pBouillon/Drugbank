package repository;

import lucene.searcher.LuceneSearcherBase;
import org.apache.lucene.index.IndexReader;


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

}
