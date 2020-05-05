package dao.stitch;

import common.pojo.Drug;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

/**
 * Drug bank file parser
 */
public class StitchParser  {

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
