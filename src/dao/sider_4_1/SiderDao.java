package dao.sider_4_1;

import common.Configuration;
import common.pojo.Symptom;
import dao.DataAccessObjectBase;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import util.lucene.indexer.ILuceneIndexer;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

/**
 * DAO for the Sider data source
 */
public class SiderDao extends DataAccessObjectBase<Symptom> implements ILuceneIndexer<Symptom> {

    /**
     * Data extractor for MeDRA databases
     */
    private MeDRAExtractor _extractor;

    /**
     * Default constructor
     * @see DataAccessObjectBase
     */
    public SiderDao() {
        super(Paths.get(Configuration.Sider.Paths.INDEX));
    }

    /**
     * Initialize the data extractor from MeDRA databases
     *
     * @inheritDoc
     */
    @Override
    protected void initialize() {
        _extractor = new MeDRAExtractor();
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
        List<Symptom> extracted = _extractor.extract();

        // Index the extracted Symptom objects
        try {
            indexSourceObjects(indexWriter, extracted);
            indexWriter.commit();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public Document getAsDocument(Symptom sourceObject) {
        Document symptomDocument = new Document();

        // Add the side effect's name to the document
        symptomDocument.add(new TextField(
                Configuration.Lucene.IndexKey.Symptom.NAME,
                sourceObject.getName(),
                Field.Store.YES));

        return symptomDocument;
    }
}
