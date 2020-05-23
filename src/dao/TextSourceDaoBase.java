package dao;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import util.parser.IParser;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/**
 * Represent a data that is coming from a text source (files, etc.)
 * @param <T> The type of the data handled
 * @see dao.DataAccessObjectBase
 */
public abstract class TextSourceDaoBase<T> extends DataAccessObjectBase<T> {

    /**
     * Path to the textual source
     */
    protected Path dataSource;

    /**
     * Associated util.parser for this data source
     */
    protected IParser<T> parser;

    /**
     * Default constructor
     */
    protected TextSourceDaoBase(Path indexDirectory) {
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
        List<T> parsed = null;

        // Retrieve the records from the file
        try {
            parsed = (List<T>) parser.extractData(dataSource);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        // Index the fetched records
        try {
            // Create the index writer
            IndexWriter indexWriter = createIndexWriter();

            // Index the extracted drug objects
            indexSourceObjects(indexWriter, parsed);

            indexWriter.commit();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * @inheritDoc
     */
    public abstract Document getAsDocument(T sourceObject);

}
