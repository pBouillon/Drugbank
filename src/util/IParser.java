package util;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Represent a parser
 * @param <T> The type of the element to produce on parsing
 */
public interface IParser<T> {

    /**
     * Parse a source and extract the desired elements from it
     * @param source Source to parse
     * @return An Iterable of the targeted elements extracted
     * @throws IOException If the provided path does not exists
     */
    Iterable<T> extractData(Path source) throws IOException;

}
