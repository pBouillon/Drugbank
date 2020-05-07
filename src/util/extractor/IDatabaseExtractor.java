package util.extractor;

import java.util.List;

/**
 * Represent an data extractor for a database
 * @param <T> Data type to be extracted
 */
public interface IDatabaseExtractor<T> {

    /**
     * Fetch the database and build the requested object
     * @return A List of the extracted objects
     */
    List<T> extract();

}
