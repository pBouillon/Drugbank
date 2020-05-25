package repository;

import common.Configuration;
import common.pojo.Symptom;
import dao.hp.HpDao;
import dao.sider_4_1.SiderDao;
import org.apache.lucene.document.Document;

import java.io.IOException;
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
        final String symptomName = toMerge.getName();

        // Get current record or create it
        symptomsMap.putIfAbsent(symptomName, new Symptom(symptomName));
        Symptom currentSymptom = symptomsMap.get(symptomName);

        // Merge data
        if (currentSymptom.getCui() == null
                && toMerge.getCui() != null) {
            currentSymptom.setCui(toMerge.getCui());
        }

        if (currentSymptom.getHpoId() == null
                && toMerge.getHpoId() != null) {
            currentSymptom.setHpoId(toMerge.getHpoId());
        }
    }

}
