package util.extractor;

import java.util.List;

/**
 * Abstract database extractor
 * @param <T> data type to be extracted
 */
public abstract class DatabaseExtractorBase<T> implements IDatabaseExtractor<T> {

    /**
     * @inheritDoc
     */
    @Override
    abstract public List<T> extract();

}
