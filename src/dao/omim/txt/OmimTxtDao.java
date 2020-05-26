package dao.omim.txt;

import common.Configuration;
import common.pojo.Disease;
import dao.TextSourceDaoBase;
import lucene.indexer.ILuceneIndexer;
import lucene.indexer.LuceneIndexerBase;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;

import java.nio.file.Paths;

/**
 * DAO for the OMIM text data source
 */
public class OmimTxtDao extends TextSourceDaoBase<Disease> implements ILuceneIndexer<Disease> {

    /**
     * Default constructor
     */
    public OmimTxtDao() {
        super(Paths.get(Configuration.Omim.Txt.Paths.INDEX));
    }

    /**
     * @inheritDoc
     */
    @Override
    protected void initialize() {
        dataSource = Paths.get(Configuration.Omim.Txt.Paths.SOURCE);
        parser = new OmimTxtParser();
    }

    /**
     * @inheritDoc
     */
    @Override
    public Document getAsDocument(Disease sourceObject) {
        Document document = new Document();

        // Disease's name
        document.add(new TextField(
                Configuration.Lucene.IndexKey.Disease.NAME,
                sourceObject.getName(),
                Field.Store.YES
        ));

        // Disease's symptoms
        document.add(new TextField(
                Configuration.Lucene.IndexKey.Disease.SYMPTOMS,
                LuceneIndexerBase.getJoinedStringCollection(
                        sourceObject.getAssociatedSymptoms()),
                Field.Store.YES
        ));

        return document;
    }

}
