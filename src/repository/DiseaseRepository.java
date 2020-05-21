package repository;

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

    }

    @Override
    public Disease createFromDocument(Document document) {
        return null;
    }
}
