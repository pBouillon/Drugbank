package dao.stitch;

import common.pojo.Drug;
import util.IParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Drug bank file parser
 * @see IParser
 */
public class StitchParser implements IParser<Drug> {

    /**
     * Represent the known fields of the parsed file
     */
    private static class Fields {

        /**
         * Used when beginning a new drug card
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
        AtomicBoolean isNewDrugCardField = new AtomicBoolean(false);

        Files.lines(source)
                .forEachOrdered(line -> {
                    // Skip empty lines
                    if (line.isEmpty() || line.startsWith(Fields.BEGIN_COMMENT)) {
                        return;
                    }
                    //Skip first line
                    if (isFirstLine.get()) {
                        isFirstLine.set(false);
                        return;
                    }

                    String[] lineTab = line.split("\t");
                    if(!lineTab[2].equals("ATC")){
                        return;
                    }

                    Drug currentDrug = new Drug();
                    currentDrug.set_ATC(lineTab[3]);
                    currentDrug.set_compoundId(lineTab[0]);
                    drugs.add(currentDrug);

                });

        return drugs;
    }

}
