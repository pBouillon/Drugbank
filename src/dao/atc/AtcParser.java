package dao.atc;

import common.pojo.Drug;
import dao.stitch.StitchParser;
import util.IParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;

/**
 * Atc file parser
 * @see IParser
 */
public class AtcParser implements IParser<Drug> {

    /**
     * Represent the known fields of the parsed file
     */
    private static class Fields {

        /**
         * Used when beginning a comment
         */
        public static final String BEGIN_COMMENT = "[#!%+].*";

    }

    /**
     * @inheritDoc
     */
    @Override
    public Iterable<Drug> extractData(Path source) throws IOException {
        Stack<Drug> drugs = new Stack<>();
        Pattern commentPattern = Pattern.compile(Fields.BEGIN_COMMENT);
        Files.lines(source)
                .forEachOrdered(line -> {
                    // Skip empty lines and comments
                    if (line.isEmpty() || commentPattern.matcher(line).matches()) {
                        return;
                    }
                    String[] lineTab = line.substring(1).trim().split(" *", 2);
                    Drug currentDrug = new Drug();
                    currentDrug.setATC(lineTab[0]);
                    currentDrug.setName(lineTab[1]);
                    drugs.add(currentDrug);
                });

        return drugs;
    }

}
