package repository;

import common.Configuration;
import common.pojo.Drug;
import common.pojo.Symptom;
import dao.atc.AtcDao;
import dao.drugbank.DrugBankDao;
import dao.stitch.StitchDao;
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
     * From a Symptom generate a list of the SearchParam used to query the associated entity
     * from this repository
     * @param symptoms The symptom to be associated with an entity
     * @return A list of the search param to be applied on the request
     * @see Symptom
     */
    public List<SearchParam> generateSearchParamsFromSymptom(List<List<Symptom>> symptoms) {
        List<SearchParam> searchParams = new Stack<>();

        List<TreeNode> tmp = recursif(symptoms, 0);
        List<StringBuilder> names = new ArrayList<>();
        for (TreeNode t:tmp) {
            names.addAll(t.reduce());
        }
/*
        if (symptom.getSideEffectOf() != null) {
            searchParams.add(
                    new SearchParam(
                            Configuration.Lucene.IndexKey.Drug.COMPOUND_ID,
                            symptom.getSideEffectOf()
                                    .stream()
                                    .reduce("", (acc, elem) -> acc + " " + elem)
                    ));
        }
        */

        for (StringBuilder nameB : names) {
            String nameQuery = nameB.toString();
            searchParams.add(
                    new SearchParam(
                            Configuration.Lucene.IndexKey.Drug.TOXICITY,
                            nameQuery
                    ));
        }

        return searchParams;
    }

    private List<TreeNode> recursif(List<List<Symptom>> L, int idx) {
        if (L.size() - 1 == idx) {
            List<TreeNode> list = new ArrayList<>();
            for (Symptom s : L.get(idx)) {
                TreeNode res = new TreeNode(LuceneSearcherBase.getFieldForLuceneExactSearchOn(s.getName()));
                list.add(res);
            }
            return list;
        } else {
            List<TreeNode> list = new ArrayList<>();
            List<TreeNode> sons = recursif(L, idx + 1);
            for (Symptom s : L.get(idx)) {
                TreeNode res = new TreeNode(LuceneSearcherBase.getFieldForLuceneExactSearchOn(s.getName()));
                res.sons.addAll(sons);
                list.add(res);
            }

            return list;
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    protected void mergeResult(Map<String, Drug> recordsMap, Drug toMerge) {
        Drug currentDrug = null;
        String drugName = toMerge.getName();
        String drugATC = toMerge.getATC();

        boolean validDrugName = drugName != null && !drugName.equals("");
        boolean validDrugATC = drugATC != null && !drugATC.equals("");

        if (validDrugName && validDrugATC) {
            currentDrug = recordsMap.get(drugName) == null
                    ? recordsMap.get(drugATC)
                    : recordsMap.get(drugName);

            if (currentDrug == null) {
                currentDrug = new Drug();
            }

            currentDrug.setName(drugName);
            currentDrug.setATC(drugATC);

            recordsMap.putIfAbsent(drugName, currentDrug);
            recordsMap.putIfAbsent(drugATC, currentDrug);

        } else if (validDrugATC) {
            currentDrug = recordsMap.get(drugATC);
            
            if (currentDrug == null) {
                currentDrug = new Drug();
                currentDrug.setATC(drugATC);
                recordsMap.put(drugATC, currentDrug);
            }

        } else if (validDrugName) {

            currentDrug = recordsMap.get(drugName);

            if (currentDrug == null) {
                currentDrug = new Drug();
                currentDrug.setName(drugName);
                recordsMap.put(drugName, currentDrug);
            }
        }

        // Get current record or create it
        if (currentDrug == null) {
            System.err.println("A Drug doesn't have name nor ATC");
            return;
        }

        // Merge data
        if (currentDrug.get_compoundId() == null
                && toMerge.get_compoundId() != null) {
            currentDrug.set_compoundId(toMerge.get_compoundId());
        }

        if (currentDrug.getToxicity() == null
                && toMerge.getToxicity() != null) {
            currentDrug.setToxicity(toMerge.getToxicity());
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
