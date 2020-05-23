package dao.omim;

import common.pojo.Disease;
import util.parser.IParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Stack;
import java.util.regex.Pattern;

/**
 * OMIM file parser
 * @see IParser
 */
public class OmimParser implements IParser<Disease> {

    /**
     * Common CSV separator
     */
    final private String DEFAULT_CSV_SEPARATOR = ",";

    /**
     * OMIM synonyms separator
     */
    final private String SYNONYMS_FIELD_SEPARATOR = "|";

    /**
     * OMIM fields definition
     */
    private static class Fields {

        /**
         * "Preferred Label" column
         */
        private static class Name {

            /**
             * Index of the name value in the CSV
             */
            public static final int Index = 1;

        }

        /**
         * "Synonyms" column
         */
        private static class Synonyms {

            /**
             * Index of the synonyms value in the CSV
             */
            public static final int Index = 2;

            /**
             * Synonyms field separator
             */
            public static final String Separator = "|";

        }

    }

    /**
     * @inheritDoc
     */
    @Override
    public Iterable<Disease> extractData(Path source) throws IOException {
        Stack<Disease> diseases = new Stack<>();

        // Parse each line of the source file
        Files.lines(source)
                .forEachOrdered(line -> {
                    // Extract each values in the current line
                    String[] values = line.split(DEFAULT_CSV_SEPARATOR);

                    // Extract the relevant fields
                    String name = values[Fields.Name.Index];
                    String[] synonyms = values[Fields.Synonyms.Index]
                            .split(Pattern
                                    .quote(Fields.Synonyms.Separator));

                    // Instantiate the new disease
                    Disease disease = new Disease();
                    disease.setName(name);
                    disease.setSynonyms(Arrays.asList(synonyms));

                    diseases.add(disease);
                });

        return diseases;
    }

}
