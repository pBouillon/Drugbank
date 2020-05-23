package lucene.indexer;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;

import java.io.IOException;
import java.util.List;

/**
 * Represent an indexer for a specific data type
 * @param <T> Data type to be indexed
 */
public interface ILuceneIndexer<T> {

    /**
     * Default file name generated by Lucene in its created
     * index folder
     */
    String INDEXER_LOCK_FILE = "write.lock";

    /**
     * Add a collection of documents to the indexing
     * @param writer Lucene index writer
     * @param sourceObjects Collection of
     */
    void indexSourceObjects(IndexWriter writer, List<T> sourceObjects) throws IOException;

    /**
     * Convert a source object to a Lucene document
     * @param sourceObject Source object to be converted
     * @return A Lucene Document
     * @see Document
     */
    Document getAsDocument(T sourceObject);

}