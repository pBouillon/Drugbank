package repository;

import common.Configuration;
import common.pojo.Symptom;
import dao.hp.HpDao;
import dao.sider_4_1.SiderDao;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Symptom repository providing entry points for Symptom fetching and creation
 */
public class SymptomRepository extends RepositoryBase {

    /**
     * Private DAO to query the SIDER sources (MeDRA)
     */
    private final SiderDao _siderDao;

    /**
     * Private DAO to query the SIDER sources (MeDRA)
     */
    private final HpDao _hpDao;

    /**
     * Default constructor
     * Initialize the DAOs
     */
    public SymptomRepository() {
        _siderDao = new SiderDao();
        _hpDao = new HpDao();
    }

    public Iterable<Symptom> getSymptomByName(String name) throws ParseException, IOException {
        ArrayList<Symptom> symptomList = new ArrayList<>();
        StandardAnalyzer analyzer = new StandardAnalyzer();

        Query q = new QueryParser(Configuration.Lucene.IndexKey.Symptom.NAME, analyzer).parse(name);
        int hitsPerPage = 10;

        List<IndexReader> idxReaders= new ArrayList<>();
        idxReaders.add(_siderDao.createIndexReader());
        idxReaders.add(_hpDao.createIndexReader());

        for (IndexReader reader: idxReaders) {
            IndexSearcher searcher = new IndexSearcher(reader);
            TopDocs docs = searcher.search(q, hitsPerPage);
            ScoreDoc[] hits = docs.scoreDocs;
            Symptom currentSymptom;
            for (ScoreDoc hit : hits) {
                int docId = hit.doc;
                Document d = searcher.doc(docId);
                currentSymptom = new Symptom();
                currentSymptom.setName(d.get(Configuration.Lucene.IndexKey.Symptom.NAME));
                currentSymptom.setCui(d.get(Configuration.Lucene.IndexKey.Symptom.CUI));
                currentSymptom.setHpoId(d.get(Configuration.Lucene.IndexKey.Symptom.HPO_ID));
                System.out.println(currentSymptom.getName());
                symptomList.add(currentSymptom);
            }
        }



        return symptomList;
    }

}
