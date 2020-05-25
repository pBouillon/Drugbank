package dao.omim;

import common.pojo.Disease;
import util.parser.IParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
    private static final char DEFAULT_CSV_SEPARATOR = ',';

    /**
     * Common Csv quoting
     */
    private static final char DEFAULT_QUOTE = '"';

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
         * "Cui" column
         */
        private static class Cui {

            /**
             * Index of the Cui value in the CSV
             */
            public static final int Index = 5;

            /**
             * CUI field separator
             */
            public static final String Separator = "|";
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
                    String[] values = parseLine(line,DEFAULT_CSV_SEPARATOR,DEFAULT_QUOTE).toArray(String[]::new);

                    // Malformed lines may not fit in the model and are considered as corrupted data
                    if (values.length <= Fields.Cui.Index) {
                        return;
                    }

                    // Extract the relevant fields
                    String name = values[Fields.Name.Index];
                    String[] cui = values[Fields.Cui.Index].split(Pattern.quote(Fields.Cui.Separator));

                    String[] synonyms = values[Fields.Synonyms.Index]
                            .split(Pattern.quote(Fields.Synonyms.Separator));

                    // Instantiate the new disease
                    Disease disease = new Disease();
                    disease.setName(name);
                    disease.setCuiList(Arrays.asList(cui));
                    disease.setSynonyms(Arrays.asList(synonyms));

                    diseases.add(disease);
                });

        return diseases;
    }

    private static List<String> parseLine(String cvsLine, char separators, char customQuote) {

        List<String> result = new ArrayList<>();

        //if empty, return!
        if (cvsLine == null || cvsLine.isEmpty()) {
            return result;
        }

        StringBuffer curVal = new StringBuffer();
        boolean inQuotes = false;
        boolean startCollectChar = false;
        boolean doubleQuotesInColumn = false;

        char[] chars = cvsLine.toCharArray();

        for (char ch : chars) {

            if (inQuotes) {
                startCollectChar = true;
                if (ch == customQuote) {
                    inQuotes = false;
                    doubleQuotesInColumn = false;
                } else {

                    //Fixed : allow "" in custom quote enclosed
                    if (ch == '\"') {
                        if (!doubleQuotesInColumn) {
                            curVal.append(ch);
                            doubleQuotesInColumn = true;
                        }
                    } else {
                        curVal.append(ch);
                    }

                }
            } else {
                if (ch == customQuote) {

                    inQuotes = true;

                    //Fixed : allow "" in empty quote enclosed
                    if (chars[0] != '"' && customQuote == '\"') {
                        curVal.append('"');
                    }

                    //double quotes in column will hit this!
                    if (startCollectChar) {
                        curVal.append('"');
                    }

                } else if (ch == separators) {

                    result.add(curVal.toString());

                    curVal = new StringBuffer();
                    startCollectChar = false;

                } else if (ch == '\r') {
                    //ignore LF characters
                    continue;
                } else if (ch == '\n') {
                    //the end, break!
                    break;
                } else {
                    curVal.append(ch);
                }
            }

        }

        result.add(curVal.toString());

        return result;
    }

}
