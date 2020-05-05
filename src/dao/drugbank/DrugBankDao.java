package dao.drugbank;

import common.Configuration;
import common.pojo.Drug;
import dao.DataAccessObjectBase;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import util.IParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Queue;

/**
 * DAO for the DrugBank data source
 */
public class DrugBankDao extends DataAccessObjectBase {

    /**
     * Drug bank data source path
     */
    private Path _dataSource;

    /**
     * Folder in which all indexes will be stored
     */
    private Path _indexesDirectory;

    /**
     * Default constructor, initialize the data source from the constants
     * @see Configuration
     */
    public DrugBankDao() { }

    /**
     * Create a Lucene IndexWriter
     * @return A new instance of the IndexWriter
     * @throws IOException On non-existing index folder
     * @see IndexWriter
     */
    private IndexWriter createIndexWriter() throws IOException {
        // Index destination
        Directory indexDirectory = FSDirectory.open(_indexesDirectory);

        // Create the index writer configuration
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(
                new StandardAnalyzer()
        );

        // Create the index writer
        return new IndexWriter(indexDirectory, indexWriterConfig);
    }

    /**
     * @inheritDoc
     */
    @Override
    protected void initialize() {
        // Prepare the data source
        _dataSource = Paths.get(Configuration.DrugBank.Paths.SOURCE);

        // Prepare the indexes directory
        _indexesDirectory = Paths.get(Configuration.DrugBank.Paths.INDEX);
        if (Files.notExists(_indexesDirectory)) {
            try {
                Files.createDirectory(_indexesDirectory);
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    protected void initializeIndexing() {
        Queue<Drug> drugs = null;

        // Retrieve the records from the file
        IParser<Drug> parser = new DrugBankParser();
        try {
            drugs = (Queue<Drug>) parser.extractData(_dataSource);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        // Index the fetched records
        IndexWriter indexWriter;
        try {
            indexWriter = createIndexWriter();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        // TODO: index the fetched rows
    }

    /**
     * @inheritDoc
     */
    @Override
    protected boolean isDataSourceIndexed() {
        // Return true if the folder containing the indexes is not empty
        try {
            return Files.list(_indexesDirectory)
                    .findAny()
                    .isPresent();
        } catch (IOException e) {
            return false;
        }
    }

}
