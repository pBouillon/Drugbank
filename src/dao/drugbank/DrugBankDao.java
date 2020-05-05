package dao.drugbank;

import common.Configuration;
import common.pojo.Drug;
import dao.DataAccessObjectBase;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import util.indexer.IIndexer;
import util.IParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * DAO for the DrugBank data source
 */
public class DrugBankDao extends  DataAccessObjectBase<Drug> implements IIndexer<Drug> {

    /**
     * Drug bank data source path
     */
    private Path _dataSource;

    /**
     * Default constructor, initialize the data source from the constants
     * @see Configuration
     */
    public DrugBankDao() { }

    /**
     * @inheritDoc
     */
    @Override
    public Document getAsDocument(Drug sourceObject) {
        Document document = new Document();

        // Drug's indication
        document.add(new TextField(
                Configuration.Lucene.IndexKey.Drug.INDICATION,
                sourceObject.getIndication(),
                Field.Store.YES
        ));

        // Drug's generic name
        document.add(new StringField(
            Configuration.Lucene.IndexKey.Drug.NAME,
            sourceObject.getName(),
            Field.Store.YES
        ));

        // Drug's synonyms
        document.add(new TextField(
                Configuration.Lucene.IndexKey.Drug.SYNONYMS,
                String.join(" ", sourceObject.getSynonyms()),
                Field.Store.YES
        ));

        // Drug's toxicity
        document.add(new TextField(
                Configuration.Lucene.IndexKey.Drug.TOXICITY,
                sourceObject.getToxicity(),
                Field.Store.YES
        ));

        return document;
    }

    /**
     * @inheritDoc
     */
    @Override
    protected void initialize() {
        // Prepare the data source
        _dataSource = Paths.get(Configuration.DrugBank.Paths.SOURCE);

        // Prepare the indexes directory
        indexesDirectory = Paths.get(Configuration.DrugBank.Paths.INDEX);
        if (Files.notExists(indexesDirectory)) {
            try {
                Files.createDirectory(indexesDirectory);
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
        List<Drug> drugs = null;

        // Retrieve the records from the file
        IParser<Drug> parser = new DrugBankParser();
        try {
            drugs = (List<Drug>) parser.extractData(_dataSource);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        // Index the fetched records
        try {
            // Create the index writer
            IndexWriter indexWriter = createIndexWriter();

            // Index the extracted drug objects
            indexSourceObjects(indexWriter, drugs);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

}
