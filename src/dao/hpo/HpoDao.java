package dao.hpo;

import common.Configuration;
import common.pojo.Disease;
import dao.DataAccessObjectBase;
import dao.DatabaseDaoBase;
import lucene.indexer.ILuceneIndexer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;

import java.nio.file.Paths;

/**
 * DAO for the HPO data source
 */
public class HpoDao extends DatabaseDaoBase<Disease> implements ILuceneIndexer<Disease> {

    /**
     * Default constructor
     *
     * @see DataAccessObjectBase
     */
    public HpoDao() {
        super(Paths.get(Configuration.HPO.Paths.INDEX));
    }

    /**
     * @inheritDoc
     */
    @Override
    public Document getAsDocument(Disease sourceObject) {
        Document doc = new Document();

        // Add the disease name to the document
        doc.add(new TextField(
                Configuration.Lucene.IndexKey.Disease.NAME,
                sourceObject.getName(),
                Field.Store.YES));

        // Add the disease DB_NAME to the document
        doc.add(new StringField(
                Configuration.Lucene.IndexKey.Disease.HPO_DB_NAME,
                sourceObject.getHpoDbName(),
                Field.Store.YES));

        // Add the disease HPO_ID to the document
        doc.add(new StringField(
                Configuration.Lucene.IndexKey.Disease.HPO_ID,
                sourceObject.getHpoId(),
                Field.Store.YES));

        // Add the disease SIGN_ID to the document
        doc.add(new StringField(
                Configuration.Lucene.IndexKey.Disease.HPO_SIGN_ID,
                sourceObject.getHpoSignId(),
                Field.Store.YES));

        return doc;
    }

    /**
     * @inheritDoc
     */
    @Override
    protected void initialize() {
        extractor = new HpoExtractor();
    }

}
