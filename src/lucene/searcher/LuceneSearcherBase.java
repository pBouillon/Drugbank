package lucene.searcher;

import common.Configuration;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;

import java.io.IOException;
import java.util.*;

/**
 * Abstract Lucene index searcher
 * @param <T> data type to be searched
 */
public abstract class LuceneSearcherBase<T> implements ILuceneSearcher<T> {

    /**
     * List all supported indexes of this repository
     */
    private final List<IndexReader> _handledIndexes;

    /**
     * Default constructor
     * @param indexReaders IndexReaders on which the LuceneSearcherBase will operate
     */
    protected LuceneSearcherBase(IndexReader... indexReaders) {
        _handledIndexes = Arrays.asList(indexReaders);
    }

    /**
     * @inheritDoc
     */
    @Override
    public abstract T createFromDocument(Document document);

    /**
     * Create a Lucene QueryParser and parse it
     * @param query Raw query to perform
     * @param fieldToSearch Field to on which performing the search
     * @return The parsed Query
     * @throws ParseException On Lucene exception
     */
    protected Query createParsedQuery(String query, String fieldToSearch) throws ParseException {
        return new QueryParser(fieldToSearch, new StandardAnalyzer())
                .parse(query);
    }

    /**
     * Fetch all entities indexed by Lucene from their name
     * @param searchParams A list of search params (results matching on all those params will be fetched)
     * @return A list of all Entities indexed by Lucene
     */
    public List<T> getEntities(SearchParam ... searchParams) {
        Map<String, T> entitiesMap = new HashMap<>();

        for (SearchParam searchParam: searchParams) {
            // Query all tracked drugs in the lucene indexes
            Query query = null;
            try {
                query = createParsedQuery(searchParam.fieldValue, searchParam.fieldToQuery);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            // Extract all records from the matches
            try {
                getMatchingEntities(query)
                        .forEach(entity
                                -> mergeResult(entitiesMap, entity));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return new ArrayList<>(entitiesMap.values());
    }

    /**
     * @inheritDoc
     */
    public List<T> getMatchingEntities(Query query) throws IOException {
        List<T> entities = new Stack<>();

        // For all handled indexes
        for (IndexReader reader : _handledIndexes) {
            // Extract the entities they are tracking
            entities.addAll(
                    getMatchingEntitiesOfIndex(query, new IndexSearcher(reader)));

        }
        return entities;
    }

    /**
     * Fetch the entities matched by the given query for a specific index
     * @param query Lucene query
     * @return A collection of the matching entities for this index
     * @see Configuration.Lucene.Search For settings
     */
    private List<T> getMatchingEntitiesOfIndex(Query query, IndexSearcher searcher) throws IOException {
        List<T> entities = new Stack<>();

        // Find the hits of the given query in the parsed index
        ScoreDoc[] hits = searcher.search(query, Configuration.Lucene.Search.HITS_PER_PAGES).scoreDocs;

        // Extract the entity contained by all hits
        for (ScoreDoc hit : hits) {
            entities.add(
                    getMatchingEntity(searcher, hit));
        }

        return entities;
    }

    /**
     * Extract the matching object from the document containing it
     * @param searcher Searched used for the search
     * @param hit Document matching the Lucene query
     * @return The newly created object from the document
     * @throws IOException On Lucene extraction
     */
    private T getMatchingEntity(IndexSearcher searcher, ScoreDoc hit) throws IOException {
        return createFromDocument(
                searcher.doc(hit.doc));
    }

    /**
     * Merge an objects in a records collection
     * If already existing, add only the missing fields
     * @param recordsMap Map of all objects with their names as keys
     * @param toMerge Object to merge in the collection
     */
    protected abstract void mergeResult(Map<String, T> recordsMap, T toMerge);

}
