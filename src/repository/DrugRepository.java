package repository;

import common.Configuration;
import common.pojo.Drug;
import dao.atc.AtcDao;
import dao.drugbank.DrugBankDao;
import dao.stitch.StitchDao;
import org.apache.lucene.document.Document;

import java.io.IOException;
import java.util.Map;

/**
 * Drug repository providing entry points for Drug fetching and creation
 */
public class DrugRepository extends RepositoryBase<Drug> {

    /**
     * Default constructor
     */
    public DrugRepository() throws IOException {
        super(new AtcDao().createIndexReader(),
                new DrugBankDao().createIndexReader(),
                new StitchDao().createIndexReader());
    }

    /**
     * @inheritDoc
     */
    @Override
    protected void mergeResult(Map<String, Drug> recordsMap, Drug toMerge) {
        final String drugName = toMerge.getName();

        // Get current record or create it
        recordsMap.putIfAbsent(drugName, new Drug(drugName));
        Drug currentDrug = recordsMap.get(drugName);

        // Merge data
        if (currentDrug.get_compoundId() == null
                && toMerge.get_compoundId() != null) {
            currentDrug.set_compoundId(toMerge.get_compoundId());
        }

        if (currentDrug.getToxicity() == null
                && toMerge.getToxicity() != null) {
            currentDrug.setToxicity(toMerge.getToxicity());
        }

        if (currentDrug.getATC() == null
                && toMerge.getATC() != null) {
            currentDrug.setATC(toMerge.getATC());
        }

        if (currentDrug.getIndication() == null
                && toMerge.getIndication() != null) {
            currentDrug.setIndication(toMerge.getIndication());
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public Drug createFromDocument(Document document) {
        return new Drug(
                document.get(Configuration.Lucene.IndexKey.Drug.NAME),
                document.get(Configuration.Lucene.IndexKey.Drug.TOXICITY),
                document.get(Configuration.Lucene.IndexKey.Drug.ATC),
                document.get(Configuration.Lucene.IndexKey.Drug.COMPOUND_ID),
                document.get(Configuration.Lucene.IndexKey.Drug.INDICATION)
        );
    }
}
