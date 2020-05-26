package repository;

import common.Configuration;
import common.pojo.Disease;
import dao.hpo.HpoDao;
import dao.omim.csv.OmimCsvDao;
import dao.omim.txt.OmimTxtDao;
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
        super(new OmimTxtDao().createIndexReader(),
                new OmimCsvDao().createIndexReader(),
                new HpoDao().createIndexReader());
    }

    @Override
    protected void mergeResult(Map<String, Disease> recordsMap, Disease toMerge) {
        final String diseaseName = toMerge.getName();

        // Get current record or create it
        recordsMap.putIfAbsent(diseaseName, new Disease(diseaseName));
        Disease currentDisease = recordsMap.get(diseaseName);

        // Merge data
        if (currentDisease.getHpoDbName() == null
                && toMerge.getHpoDbName() != null) {
            currentDisease.setHpoDbName(toMerge.getHpoDbName());
        }

        if (currentDisease.getHpoId() == null
                && toMerge.getHpoId() != null) {
            currentDisease.setHpoId(toMerge.getHpoId());
        }

        if (currentDisease.getHpoSignId() == null
                && toMerge.getHpoSignId() != null) {
            currentDisease.setHpoSignId(toMerge.getHpoSignId());
        }
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
