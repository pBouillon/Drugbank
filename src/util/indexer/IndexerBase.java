package util.indexer;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Abstract data indexer
 * @param <T> data type to be indexed
 */
public abstract class IndexerBase<T> implements IIndexer<T> {

    /**
     * Folder in which all indexes will be stored
     */
    protected Path indexesDirectory;

    /**
     * Default constructor
     * @param indexesDirectoryPath Path to the index directory, create it if not exists
     */
    protected IndexerBase(Path indexesDirectoryPath) {
        indexesDirectory = indexesDirectoryPath;
        ensureDirectoryCreation();
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
                System.exit(1);
            }
        }
    }

    /**
     * Create a Lucene IndexWriter
     * @return A new instance of the IndexWriter
     * @throws IOException On non-existing index folder
     * @see IndexWriter
     */
    protected IndexWriter createIndexWriter() throws IOException {
        // Index destination
        Directory indexDirectory = FSDirectory.open(indexesDirectory);

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
    public abstract Document getAsDocument(T sourceObject);

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
