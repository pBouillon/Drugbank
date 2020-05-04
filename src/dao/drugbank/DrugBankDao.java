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

/**
 * DAO for the DrugBank data source
 */
public class DrugBankDao extends DataAccessObjectBase {

    /**
     * Lucene IndexWriter
     * @see IndexWriter
     */
    private IndexWriter _indexWriter;

    /**
     * Default constructor
     * @throws IOException on missing index folder
     */
    public DrugBankDao() throws IOException {
        // Index destination
        Path indexDest = Paths.get(Configuration.Lucene.Paths.INDEX);
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
    protected void initializeIndexing() { }

    /**
     * @inheritDoc
     */
    @Override
    protected boolean isDataSourceIndexed() {
        return false;
    }

}
