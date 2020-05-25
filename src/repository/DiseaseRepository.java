package repository;

import common.Configuration;
import common.pojo.Disease;
import common.pojo.Symptom;
import dao.hpo.HpoDao;
import dao.omim.OmimDao;
import lucene.searcher.SearchParam;
import org.apache.lucene.document.Document;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Stack;

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

    /**
     * @inheritDoc
     */
    @Override
    public Disease createFromDocument(Document document) {
        return new Disease(
                document.get(Configuration.Lucene.IndexKey.Disease.NAME),
                document.get(Configuration.Lucene.IndexKey.Disease.HPO_SIGN_ID),
                document.get(Configuration.Lucene.IndexKey.Disease.HPO_ID),
                document.get(Configuration.Lucene.IndexKey.Disease.HPO_DB_NAME),
                document.get(Configuration.Lucene.IndexKey.Disease.CUI_LIST)
        );
    }

    /**
     * @inheritDoc
     */
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

        if (currentDisease.getUnifiedCuiList() == null
                && toMerge.getUnifiedCuiList() != null) {
            currentDisease.setCuiList(toMerge.getUnifiedCuiList());
        }
    }

    /**
     * From a Symptom generate a list of the SearchParam used to query the associated Diseases
     * from this repository
     * @param symptom The symptom to be associated with a disease
     * @return A list of the search param to be applied on the request
     * @see Symptom
     */
    public static List<SearchParam> generateSearchParamsFromSymptom(Symptom symptom) {
        List<SearchParam> searchParams = new Stack<>();

        if (symptom.getHpoId() != null) {
            searchParams.add(
                    new SearchParam(
                            Configuration.Lucene.IndexKey.Disease.HPO_SIGN_ID,
                            symptom.getHpoId()
            ));
        }

        if (symptom.getCui() != null) {
            searchParams.add(
                    new SearchParam(
                            Configuration.Lucene.IndexKey.Disease.CUI_LIST,
                            symptom.getCui()
            ));
        }

        return searchParams;
    }

}
