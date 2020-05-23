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
        // Get current record or create it
        Drug currentDrug = recordsMap.getOrDefault(
                toMerge.getName(),
                new Drug(toMerge.getName()));

        // Merge data
        if(currentDrug.get_compoundId() == null && toMerge.get_compoundId() != null){
            toMerge.set_compoundId(currentDrug.get_compoundId());
        }
        if(currentDrug.getToxicity() == null && toMerge.getToxicity() != null){
            toMerge.setToxicity(currentDrug.getToxicity());
        }
        if(currentDrug.getATC() == null && toMerge.getATC() != null){
            toMerge.setATC(currentDrug.getATC());
        }
        if(currentDrug.getIndication() == null && toMerge.getIndication() != null){
            toMerge.setIndication(currentDrug.getIndication());
        }

        // "Save" results
        recordsMap.put(currentDrug.getName(), currentDrug);
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
