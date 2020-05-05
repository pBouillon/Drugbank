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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Queue;

/**
 * DAO for the DrugBank data source
 */
public class DrugBankDao extends DataAccessObjectBase {

    /**
     * Drug bank data source path
     */
    private final Path _dataSource;

    /**
     * Folder in which all indexes will be stored
     */
    private final Path _indexesFolder;

    /**
     * Lucene IndexWriter
     * @see IndexWriter
     */
    private final IndexWriter _indexWriter;

    /**
     * Default constructor
     * @throws IOException on missing index folder
     */
    public DrugBankDao(Path dataSource) throws IOException {
        _dataSource = dataSource;

        // Index destination
        Path indexDest = Paths.get(Configuration.Lucene.Paths.INDEX);
        _indexesFolder = indexDest;
        Directory indexDirectory = FSDirectory.open(indexDest);

        // Create the index writer configuration
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(
                new StandardAnalyzer()
        );

        // Create the index writer
        _indexWriter = new IndexWriter(indexDirectory, indexWriterConfig);
    }

    /**
     * @inheritDoc
     */
    @Override
    protected void initializeIndexing() {

        Iterable<Drug> drugs;

        // Retrieve the records from the file
        IParser<Drug> parser = new DrugBankParser();
        try {
            drugs = parser.extractData(_dataSource);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        // Index the fetched records
        // TODO: index the fetched rows

    }

    /**
     * @inheritDoc
     */
    @Override
    protected boolean isDataSourceIndexed() {
        return _indexesFolder.iterator().hasNext();
    }

}
