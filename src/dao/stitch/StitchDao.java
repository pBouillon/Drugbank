package dao.stitch;

import common.Configuration;
import common.pojo.Drug;
import dao.DataAccessObjectBase;
import dao.TextSourceDaoBase;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import util.lucene.indexer.ILuceneIndexer;

import java.nio.file.Paths;

/**
 * DAO for the Stitch data source
 */
public class StitchDao extends TextSourceDaoBase<Drug> implements ILuceneIndexer<Drug> {

    /**
     * Default constructor
     * @see DataAccessObjectBase
     */
    public StitchDao() {
        super(Paths.get(Configuration.Stitch.Paths.INDEX));
    }

    /**
     * @inheritDoc
     */
    @Override
    protected void initialize() {
        dataSource = Paths.get(Configuration.Stitch.Paths.SOURCE);
        parser = new StitchParser();
    }

    /**
     * @inheritDoc
     */
    @Override
    public Document getAsDocument(Drug sourceObject) {
        Document document = new Document();

        // Drug's ATC
        document.add(new StringField(
                Configuration.Lucene.IndexKey.Drug.ATC,
                sourceObject.getATC(),
                Field.Store.YES
        ));

        // Drug's compound
        document.add(new StringField(
                Configuration.Lucene.IndexKey.Drug.COMPOUND_ID,
                sourceObject.get_compoundId(),
                Field.Store.YES
        ));

        return document;
    }

}
