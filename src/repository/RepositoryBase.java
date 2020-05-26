package repository;

import common.pojo.Symptom;
import lucene.searcher.LuceneSearcherBase;
import lucene.searcher.SearchParam;
import org.apache.lucene.index.IndexReader;

import java.util.List;

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
     * From a Symptom generate a list of the SearchParam used to query the associated entity
     * from this repository
     * @param symptom The symptom to be associated with an entity
     * @return A list of the search param to be applied on the request
     * @see Symptom
     */
    public abstract List<SearchParam> generateSearchParamsFromSymptom(Symptom symptom);

}
