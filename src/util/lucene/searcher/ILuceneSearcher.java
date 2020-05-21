package util.lucene.searcher;

import common.Configuration;
import org.apache.lucene.document.Document;
import org.apache.lucene.search.Query;

import java.io.IOException;
import java.util.List;

/**
 * Defines a Lucene searcher
 * @param <T> Data type to be searched
 */
public interface ILuceneSearcher<T> {

    /**
     * Create the entity from the Lucene document containing it
     * @param document Document in which the data will be extracted
     * @return The newly created entity
     */
    T createFromDocument(Document document);

    /**
     * Fetch the entities matched by the given query
     * @param query Lucene query
     * @return A collection of the matching entities
     * @see Configuration.Lucene.Search For settings
     */
    List<T> getMatchingEntities(Query query) throws IOException;

}
