package util;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface IParser<T> {

    List<T> extractData(Path source) throws IOException;

}
