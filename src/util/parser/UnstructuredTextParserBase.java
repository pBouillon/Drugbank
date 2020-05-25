package util.parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Stack;

/**
 * Represent a parser for an unstructured text file
 * @param <T> The type of the element to produce on parsing
 */
public abstract class UnstructuredTextParserBase<T> implements IParser<T> {

    /**
     * Collection of the parsed elements
     */
    protected final Stack<T> parsedEntities = new Stack<>();

    /**
     * Process a field when a multiline field flag is raised
     */
    protected abstract void handleMultilineFields(String field);

    /**
     * Process a field when a flag is raised
     * Operate after `handleMultilineFields`
     */
    protected abstract void handleSingleLineFields(String field);

    /**
     * Check if any flag is raised
     */
    protected abstract boolean isAnyFlagRaised();

    /**
     * Check if any flag designating a multiline field flag is raised
     */
    protected abstract boolean isAnyMultiLineFieldRaised();

    /**
     * Raise the appropriate flags when matching the current field
     * Does not operate if any flag is currently raised
     */
    protected abstract void setFlags(String field);

    /**
     * @inheritDoc
     */
    @Override
    public Iterable<T> extractData(Path source) throws IOException {

        // Parse each line of the source file
        Files.lines(source)
            .forEachOrdered(line -> {
                // Handle fields split among several lines
                if (isAnyMultiLineFieldRaised()) {
                    handleMultilineFields(line);
                }

                // Skip empty lines
                if (line.isEmpty()) {
                    return;
                }

                // If no flags are raised try to set them instead of processing
                if (!isAnyFlagRaised()) {
                    setFlags(line);
                    return;
                }

                handleSingleLineFields(line);
            });

        return parsedEntities;
    }

}
