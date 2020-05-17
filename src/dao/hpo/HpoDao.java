package dao.hpo;

import common.Configuration;
import common.pojo.Disease;
import dao.DataAccessObjectBase;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.IndexWriter;
import util.indexer.IIndexer;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

/**
 * DAO for the HPO data source
 */
public class HpoDao extends DataAccessObjectBase<Disease> implements IIndexer<Disease> {

    /**
     * Data extractor for MeDRA databases
     */
    private HpoExtractor _extractor;

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
        doc.add(new StringField(
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
        _extractor = new HpoExtractor();
    }

    /**
     * @inheritDoc
     */
    @Override
    protected void initializeIndexing() {
        // Create the index writer
        IndexWriter indexWriter = null;
        try {
            indexWriter = createIndexWriter();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        // Extract the symptoms and create them
        List<Disease> extracted = _extractor.extract();

        // Index the extracted Symptom objects
        try {
            indexSourceObjects(indexWriter, extracted);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

}
