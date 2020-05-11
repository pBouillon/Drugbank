package dao.hp;

import common.pojo.Symptom;
import util.parser.IParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Stack;

/**
 * Drug bank file util.parser
 * @see IParser
 */
public class HpParser implements IParser<Symptom> {

    /**
     * Represent the known fields of the parsed file
     */
    private static class Fields {

        /**
         * Used when beginning a new symptom card
         */
        public static final String BEGIN_CARD = "[Term]";

        /**
         * start of the id field
         */
        public static final String ID_FIELD = "id: ";

        /**
         * start of the name field
         */
        public static final String NAME_FIELD = "name: ";

        /**
         * start of the synonyms field
         */
        public static final String SYNONYMS_FIELD = "synonym: ";

        /**
         * Start of the cui field
         */
        public static final String CUI_FIELD = "xref: UMLS:";

    }

    /**
     * @inheritDoc
     */
    @Override
    public Iterable<Symptom> extractData(Path source) throws IOException {
        Stack<Symptom> symptoms = new Stack<>();

        Files.lines(source)
            .forEachOrdered(line -> {
                // Skip empty lines
                if (line.isEmpty()) {
                    return;
                }

                if(line.equals(Fields.BEGIN_CARD)){
                    symptoms.push(new Symptom());
                    symptoms.peek().set_cui("");
                    return;
                }

                // Skip until 1st card
                if(symptoms.empty()){
                    return;
                }

                if(line.startsWith(Fields.ID_FIELD)){
                    symptoms.peek().set_hpoId(line.replace(Fields.ID_FIELD, ""));
                    return;
                }

                if(line.startsWith(Fields.CUI_FIELD)){
                    symptoms.peek().set_cui(line.replace(Fields.CUI_FIELD, ""));
                    return;
                }

                if(line.startsWith(Fields.NAME_FIELD)){
                    symptoms.peek().setName(line.replace(Fields.NAME_FIELD, ""));
                }

            });

        return symptoms;
    }

}
