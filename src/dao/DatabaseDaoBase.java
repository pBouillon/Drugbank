package dao;

import common.pojo.Symptom;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import util.extractor.IDatabaseExtractor;
import util.parser.IParser;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/**
 * Represent a data that is coming from a queryable source
 * @param <T> The type of the data handled
 * @see dao.DataAccessObjectBase
 */
public abstract class DatabaseDaoBase<T> extends DataAccessObjectBase<T> {

    /**
     * Associated extractor for this data source
     */
    protected IDatabaseExtractor<T> extractor;

    /**
     * Default constructor
     */
    protected DatabaseDaoBase(Path indexDirectory) {
        super(indexDirectory);
    }

    /**
     * Must be override to provide the `dataSource` and the `util.parser`
     *
     * @inheritDoc
     */
    protected abstract void initialize();

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
        }

        // Extract the symptoms and create them
        List<T> extracted = extractor.extract();

        // Index the extracted Symptom objects
        try {
            indexSourceObjects(indexWriter, extracted);
            indexWriter.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @inheritDoc
     */
    public abstract Document getAsDocument(T sourceObject);

}
