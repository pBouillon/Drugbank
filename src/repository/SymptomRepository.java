package repository;

import common.Configuration;
import common.pojo.Symptom;
import dao.hp.HpDao;
import dao.sider_4_1.SiderDao;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.Query;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Symptom repository providing entry points for Symptom fetching and creation
 */
public class SymptomRepository extends RepositoryBase<Symptom> {

    /**
     * Default constructor
     */
    public SymptomRepository() throws IOException {
        super(new SiderDao().createIndexReader(),
                new HpDao().createIndexReader());
    }

    /**
     * @inheritDoc
     */
    @Override
    public Symptom createFromDocument(Document document) {
        return new Symptom(
                document.get(Configuration.Lucene.IndexKey.Symptom.NAME),
                document.get(Configuration.Lucene.IndexKey.Symptom.CUI),
                document.get(Configuration.Lucene.IndexKey.Symptom.HPO_ID)
        );
    }

    /**
     * @inheritDoc
     */
    @Override
    protected void mergeResult(Map<String, Symptom> symptomsMap, Symptom toMerge) {
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
        Query query = createParsedQuery(
                symptomName, Configuration.Lucene.IndexKey.Symptom.NAME);

        // Extract all records from the matches
        getMatchingEntities(query)
                .forEach(symptom
                        -> mergeResult(symptomMap, symptom));

        return (List<Symptom>) symptomMap.values();
    }

}
