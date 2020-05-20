package repository;

import common.Configuration;
import common.pojo.Symptom;
import dao.hp.HpDao;
import dao.sider_4_1.SiderDao;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

import java.io.IOException;
import java.util.*;

/**
 * Symptom repository providing entry points for Symptom fetching and creation
 */
public class SymptomRepository extends RepositoryBase {

    /**
     * Default constructor
     * Initialize the DAOs
     */
    public SymptomRepository() throws IOException {
        handledIndexes = Arrays.asList(
                new SiderDao().createIndexReader(),
                new HpDao().createIndexReader());
    }

    /**
     * Extract and create a Symptom from a Lucene document
     * @param document Document in which the data will be extracted
     * @return The newly created symptom
     * @see Symptom
     */
    private Symptom createSymptomFromDocument(Document document) {
        return new Symptom(
                document.get(Configuration.Lucene.IndexKey.Symptom.NAME),
                document.get(Configuration.Lucene.IndexKey.Symptom.CUI),
                document.get(Configuration.Lucene.IndexKey.Symptom.HPO_ID)
        );
    }

    /**
     * Merge a Symptom in a symptom list
     * If already existing, add only the missing fields
     * @param symptomsMap Map of all symptoms with their names as keys
     * @param toMerge Symptom to merge in the collection
     */
    private void mergeResult(Map<String, Symptom> symptomsMap, Symptom toMerge) {
        // Get current record or create it
        Symptom currentSymptom = symptomsMap.getOrDefault(
                toMerge.getName(), new Symptom(toMerge.getName()));

        // Merge data
        if (currentSymptom.getCui() == null
                && toMerge.getCui() != null) {
            toMerge.setCui(currentSymptom.getCui());
        }

        if (currentSymptom.getHpoId() == null
                && toMerge.getHpoId() != null) {
            toMerge.setHpoId(currentSymptom.getHpoId());
        }

        // "Save" results
        symptomsMap.put(currentSymptom.getName(), currentSymptom);
    }

    /**
     * Fetch all symptoms indexed by Lucene from their name
     * @param symptomName Name of the symptom to be matched
     * @return A list of all Symptoms indexed by Lucene
     * @throws ParseException On Lucene parsing
     * @throws IOException On Lucene parsing
     */
    public List<Symptom> getSymptomsByName(String symptomName) throws ParseException, IOException {
        Map<String, Symptom> symptomMap = new HashMap<>();

        // Query all tracked symptoms in the lucene indexes
        Query query = createAndParseQuery(
                symptomName, Configuration.Lucene.IndexKey.Symptom.NAME);

        // Initialize used objects
        IndexSearcher searcher;
        TopDocs docs;
        Document document;

        // Buffer for the symptoms found
        Symptom currentSymptom;

        // For each DAO handled by the repository
        for (IndexReader reader : handledIndexes) {
            // Search for tracked symptoms
            searcher = new IndexSearcher(reader);
            docs = searcher.search(query, Configuration.Lucene.Search.HITS_PER_PAGES);

            // For each documents found
            for (ScoreDoc hit : docs.scoreDocs) {
                // Extract the symptom it contains
                document = searcher.doc(hit.doc);
                currentSymptom = createSymptomFromDocument(document);

                // Merge its content with all the other symptoms found
                mergeResult(symptomMap, currentSymptom);
            }
        }

        return (List<Symptom>) symptomMap.values();
    }

}
