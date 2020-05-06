package dao.drugbank;

import common.Configuration;
import common.pojo.Drug;
import dao.DataAccessObjectBase;
import dao.TextSourceDaoBase;
import dao.stitch.StitchParser;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import util.indexer.IIndexer;

import java.nio.file.Paths;

/**
 * DAO for the DrugBank data source
 */
public class DrugBankDao extends TextSourceDaoBase<Drug> implements IIndexer<Drug> {

    /**
     * Default constructor
     * @see DataAccessObjectBase
     */
    public DrugBankDao() {
        super(Paths.get(Configuration.DrugBank.Paths.INDEX));
    }

    /**
     * @inheritDoc
     */
    @Override
    protected void initialize() {
        dataSource = Paths.get(Configuration.DrugBank.Paths.SOURCE);
        parser = new DrugBankParser();
    }

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

}
