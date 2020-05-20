package repository;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;

import java.util.List;

/**
 * Base class for repositories objects querying at least one DAO
 * to fetch data from several sources
 * @see dao.DataAccessObjectBase
 */
public abstract class RepositoryBase {

    /**
     * List all supported indexes of this repository
     */
    protected List<IndexReader> handledIndexes;

    /**
     * Create a Lucene query and parse it
     * @param query Raw query to perform
     * @param fieldToSearch Field to on which performing the search
     * @return The parsed Query
     * @throws ParseException On Lucene exception
     */
    protected Query createAndParseQuery(String query, String fieldToSearch) throws ParseException {
        return new QueryParser(fieldToSearch, new StandardAnalyzer())
                .parse(query);
    }

}
