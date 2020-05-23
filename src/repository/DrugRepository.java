package repository;

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


    @Override
    protected void mergeResult(Map<String, Drug> recordsMap, Drug toMerge) {

    }

    @Override
    public Drug createFromDocument(Document document) {
        return null;
    }
}
