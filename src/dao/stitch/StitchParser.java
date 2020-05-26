package dao.stitch;

import common.pojo.Drug;
import util.parser.IParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Stitch file util.parser
 * @see IParser
 */
public class StitchParser implements IParser<Drug> {

    /**
     * Represent the known fields of the parsed file
     */
    private static class Fields {

        /**
         * Used when beginning a comment
         */
        public static final String BEGIN_COMMENT = "#";

    }

    /**
     * @inheritDoc
     */
    @Override
    public Iterable<Drug> extractData(Path source) throws IOException {
        Stack<Drug> drugs = new Stack<>();

        // Atomic boolean for file parsing
        AtomicBoolean isFirstLine = new AtomicBoolean(true);

        Files.lines(source)
                .forEachOrdered(line -> {
                    // Skip empty lines and comments
                    if (line.isEmpty() || line.startsWith(Fields.BEGIN_COMMENT)) {
                        return;
                    }

                    // Skip first line
                    if (isFirstLine.get()) {
                        isFirstLine.set(false);
                        return;
                    }

                    String[] lineTab = line.split("\t");
                    if (!lineTab[2].equals("ATC")) {
                        return;
                    }

                    Drug currentDrug = new Drug();
                    currentDrug.setATC(lineTab[3]);
                    currentDrug.setCompoundId(lineTab[0]);
                    drugs.add(currentDrug);
                });

        return drugs;
    }

}
