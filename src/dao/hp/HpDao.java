package dao.hp;

import common.Configuration;
import common.pojo.Symptom;
import dao.TextSourceDaoBase;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import util.indexer.IIndexer;

import java.nio.file.Paths;

/**
 * DAO for the HPO data source
 */
public class HpDao extends TextSourceDaoBase<Symptom> implements IIndexer<Symptom> {

    public HpDao() {
        super(Paths.get(Configuration.Hp.Paths.INDEX));
    }

    /**
     * @inheritDoc
     */
    @Override
    protected void initialize() {
        dataSource = Paths.get(Configuration.Hp.Paths.SOURCE);
        parser = new HpParser();
    }

    /**
     * @inheritDoc
     */
    @Override
    public Document getAsDocument(Symptom sourceObject) {
        Document document = new Document();

        // Symptom's name
        document.add(new TextField(
                Configuration.Lucene.IndexKey.Symptom.NAME,
                sourceObject.getName(),
                Field.Store.YES
        ));

        // Symptom's CUI
        document.add(new StringField(
                Configuration.Lucene.IndexKey.Symptom.CUI,
                sourceObject.getCui(),
                Field.Store.YES
        ));

        // Symptom's hpoId
        document.add(new StringField(
                Configuration.Lucene.IndexKey.Symptom.HPO_ID,
                sourceObject.getHpoId(),
                Field.Store.YES
        ));

        return document;
    }

}
