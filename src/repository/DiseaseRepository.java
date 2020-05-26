package repository;

import common.Configuration;
import common.pojo.Disease;
import common.pojo.Symptom;
import dao.hpo.HpoDao;
import dao.omim.csv.OmimCsvDao;
import dao.omim.txt.OmimTxtDao;
import lucene.searcher.LuceneSearcherBase;
import lucene.searcher.SearchParam;
import org.apache.lucene.document.Document;
import util.tree.TreeNode;

import java.io.IOException;
import java.util.ArrayList;
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
        super(new OmimTxtDao().createIndexReader(),
                new OmimCsvDao().createIndexReader(),
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

        if (currentDisease.getCuiList() == null
                && toMerge.getCuiList() != null) {
            currentDisease.setCuiList(toMerge.getCuiList());
        }
    }

    /**
     * From a Symptom generate a list of the SearchParam used to query the associated entity
     * from this repository
     *
     * @param symptoms The symptom to be associated with an entity
     * @return A list of the search param to be applied on the request
     * @see Symptom
     */
    public List<SearchParam> generateSearchParamsFromSymptom(List<List<Symptom>> symptoms) {
        List<SearchParam> searchParams = new Stack<>();
        if (symptoms.size() == 1) {
            for (Symptom symptom : symptoms.get(0)) {
                if (symptom.getHpoId() != null) {
                    searchParams.add(
                            new SearchParam(
                                    Configuration.Lucene.IndexKey.Disease.HPO_SIGN_ID,
                                    symptom.getHpoId()
                            ));
                }
            }

        }

        List<TreeNode[]> tmp = recursif(symptoms, 0);
        List<StringBuilder> cuis = new ArrayList<>();
        List<StringBuilder> names = new ArrayList<>();
        for (TreeNode[] tnt: tmp) {
            cuis.addAll(tnt[0].reduce());
            names.addAll(tnt[1].reduce());
        }

        for (StringBuilder cuiB : cuis) {
            String cuiQuery = cuiB.toString();
            searchParams.add(
                    new SearchParam(
                            Configuration.Lucene.IndexKey.Disease.CUI_LIST,
                            cuiQuery
                    ));

        }
        for (StringBuilder nameB : names) {
            String nameQuery = nameB.toString();
            searchParams.add(
                    new SearchParam(
                            Configuration.Lucene.IndexKey.Disease.SYMPTOMS,
                            nameQuery
                    ));
        }


        return searchParams;
    }

    private List<TreeNode[]> recursif(List<List<Symptom>> L, int idx) {
        if (L.size() - 1 == idx) {
            List<TreeNode[]> list = new ArrayList<>();
            for (Symptom s : L.get(idx)) {
                TreeNode[] res = new TreeNode[2];
                res[0] = new TreeNode(s.getCui());
                res[1] = new TreeNode(LuceneSearcherBase.getFieldForLuceneExactSearchOn(s.getName()));
                list.add(res);
            }
            return list;
        } else {
            List<TreeNode[]> list = new ArrayList<>();
            List<TreeNode[]> sons = recursif(L, idx + 1);
            for (Symptom s : L.get(idx)) {
                TreeNode[] res = new TreeNode[2];
                res[0] = new TreeNode(s.getCui());
                res[1] = new TreeNode(LuceneSearcherBase.getFieldForLuceneExactSearchOn(s.getName()));
                for (TreeNode[] tns: sons) {
                    res[0].sons.add(tns[0]);
                    res[1].sons.add(tns[1]);
                }
                list.add(res);
            }

            return list;
        }
    }

}
