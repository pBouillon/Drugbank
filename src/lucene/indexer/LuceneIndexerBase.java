package lucene.indexer;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Abstract data indexer
 * @param <T> data type to be indexed
 */
public abstract class LuceneIndexerBase<T> implements ILuceneIndexer<T> {

    /**
     * Folder in which all indexes will be stored
     */
    protected Path indexesDirectory;

    /**
     * Associated index writer
     */
    protected IndexWriter indexWriter;

    /**
     * Default constructor
     *
     * @param indexesDirectoryPath Path to the index directory, create it if not exists
     */
    protected LuceneIndexerBase(Path indexesDirectoryPath) {
        indexesDirectory = indexesDirectoryPath;

        ensureDirectoryCreation();

        // Index destination
        Directory indexDirectory;
        try {
            indexDirectory = FSDirectory.open(indexesDirectory);
            // Create the index writer configuration
            IndexWriterConfig indexWriterConfig = new IndexWriterConfig(
                    new StandardAnalyzer()
            );
            indexWriterConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);

            // Create the index writer
            indexWriter = new IndexWriter(indexDirectory, indexWriterConfig);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create a Lucene IndexReader
     *
     * @return A new instance of the IndexReader
     * @throws IOException On non-existing index folder
     * @see IndexReader
     */
    public IndexReader createIndexReader() throws IOException {
        return DirectoryReader.open(indexWriter);
    }

    /**
     * Create a Lucene IndexWriter
     *
     * @return A new instance of the IndexWriter
     * @see IndexWriter
     */
    protected IndexWriter createIndexWriter() {
        return indexWriter;
    }

    /**
     * Ensure that the index directory is created
     * If not created, creates it
     */
    private void ensureDirectoryCreation() {
        if (Files.notExists(indexesDirectory)) {
            try {
                Files.createDirectory(indexesDirectory);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public abstract Document getAsDocument(T sourceObject);

    /**
     * Join a String list
     * @param array The String list to join
     * @return A String containing all elements joined with a whitespace; null if `array` is null
     */
    public static String getJoinedStringCollection(List<String> array) {
        return array == null
            ? null
            : String.join(" ", array);
    }

    /**
     * Split a joined list from LuceneIndexerBase.getJoinedStringCollection
     * @param toSplit String to split
     * @return A list of the split strings; null if `toSplit` is null
     */
    public static List<String> getSplitStringCollection(String toSplit) {
        return toSplit == null
            ? null
            : Arrays.asList(toSplit.split(" "));
    }

    /**
     * @inheritDoc
     */
    @Override
    public void indexSourceObjects(IndexWriter writer, List<T> sourceObjects) throws IOException {
        List<Document> documents = sourceObjects.stream()
                .map(this::getAsDocument)
                .collect(Collectors.toList());

        writer.addDocuments(documents);
    }

}
