package dao.atc;

import common.pojo.Drug;
import util.parser.IParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Stack;
import java.util.regex.Pattern;

/**
 * Atc file util.parser
 * @see IParser
 */
public class AtcParser implements IParser<Drug> {

    /**
     * @inheritDoc
     */
    @Override
    public Iterable<Drug> extractData(Path source) throws IOException {
        // Compile commented lines pattern
        final String commentFormat = "[#!%+].*";
        final Pattern commentPattern = Pattern.compile(commentFormat);

        // Parse the source file
        Stack<Drug> drugs = new Stack<>();
        Files.lines(source)
                .forEachOrdered(line -> {
                    // Skip empty lines and comments
                    if (line.isEmpty() || commentPattern.matcher(line).matches()) {
                        return;
                    }

                    // Extract the two relevant fields
                    String[] lineTab = line.substring(1)
                            .trim()
                            .split(" *", 2);

                    // Create the object from the extracted fields
                    Drug currentDrug = new Drug();
                    currentDrug.setATC(lineTab[0]);
                    currentDrug.setName(lineTab[1]);
                    drugs.add(currentDrug);
                });

        return drugs;
    }

}
