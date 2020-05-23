package repository;

import common.Configuration;
import common.pojo.Disease;
import dao.hpo.HpoDao;
import dao.omim.OmimDao;
import org.apache.lucene.document.Document;

import java.io.IOException;
import java.util.Map;

/**
 * Disease repository providing entry points for Disease fetching and creation
 */
public class DiseaseRepository extends RepositoryBase<Disease> {

    /**
     * Default constructor
     */
    public DiseaseRepository() throws IOException {
        super(new OmimDao().createIndexReader(),
                new HpoDao().createIndexReader());
    }

    @Override
    protected void mergeResult(Map<String, Disease> recordsMap, Disease toMerge) {
        // Get current record or create it
        Disease currentDrug = recordsMap.getOrDefault(
                toMerge.getName(),
                new Disease(toMerge.getName()));

        // Merge data
        if (currentDrug.getHpoDbName() == null && toMerge.getHpoDbName() != null) {
            toMerge.setHpoDbName(currentDrug.getHpoDbName());
        }
        if (currentDrug.getHpoId() == null && toMerge.getHpoId() != null) {
            toMerge.setHpoId(currentDrug.getHpoId());
        }
        if (currentDrug.getHpoSignId() == null && toMerge.getHpoSignId() != null) {
            toMerge.setHpoSignId(currentDrug.getHpoSignId());
        }

        // "Save" results
        recordsMap.put(currentDrug.getName(), currentDrug);
    }

    @Override
    public Disease createFromDocument(Document document) {
        return new Disease(
                document.get(Configuration.Lucene.IndexKey.Disease.NAME),
                document.get(Configuration.Lucene.IndexKey.Disease.HPO_SIGN_ID),
                document.get(Configuration.Lucene.IndexKey.Disease.HPO_ID),
                document.get(Configuration.Lucene.IndexKey.Disease.HPO_DB_NAME)
        );
    }
}
