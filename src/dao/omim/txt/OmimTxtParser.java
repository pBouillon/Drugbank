package dao.omim.txt;

import common.pojo.Disease;
import lucene.indexer.LuceneIndexerBase;
import util.parser.IParser;
import util.parser.UnstructuredTextParserBase;

import java.util.Arrays;

/**
 * OMIM text file parser
 * @see IParser
 */
public class OmimTxtParser extends UnstructuredTextParserBase<Disease> {

    /**
     * Multiline flag raised when the following fields are symptoms of the current disease
     */
    private boolean _isDiseaseSymptoms = false;

    /**
     * Flag raised when the following field is the disease's name
     */
    private boolean _isDiseaseName = false;

    /**
     * OMIM fields definition
     */
    private static class Fields {

        /**
         * Constants for the disease's name parsing
         */
        private static class Name {

            /**
             * Used when the following line will be the disease's name
             */
            public static final String FIELD = "*FIELD* TI";

            /**
             * Used to split the name with other synonyms
             */
            public static final String SYNONYMS_SEPARATOR = ";";

        }

        /**
         * Constants for the disease's symptoms parsing
         */
        private static class Symptoms {

            /**
             * Used when the following line will be the disease's symptoms
             */
            public static final String FIELD = "*FIELD* CS";

            /**
             * Prefix of all symptoms when defined
             */
            public static final String SYMPTOMS_BEGINNING = "   ";

            /**
             * First char on the line ending the symptoms enumeration
             */
            public static final String SYMPTOMS_ENDING_CHAR = "*";

            /**
             * Prefix of all subtitles, e.g. '['
             */
            public static final String SYMPTOMS_SUBTITLE_BEGINNING = "[";

        }

    }

    /**
     * @inheritDoc
     */
    @Override
    protected void handleMultilineFields(String field) {
        // Reset the flag when beginning a new disease definition
        if (field.startsWith(Fields.Symptoms.SYMPTOMS_ENDING_CHAR)) {
            _isDiseaseSymptoms = false;
            return;
        }

        // Ignore symptom's categories and subtitles
        if (!field.startsWith(Fields.Symptoms.SYMPTOMS_BEGINNING)
            || field.contains(Fields.Symptoms.SYMPTOMS_SUBTITLE_BEGINNING)) {
            return;
        }

        Disease currentDisease = parsedEntities.peek();

        if (_isDiseaseSymptoms) {
            // Remove trailing spaces
            String symptom = field.strip();

            // Then remove the trailing line ending
            symptom = symptom.replace(";", "");

            // Add the newly formatted symptom
            currentDisease.getAssociatedSymptoms().add(symptom);
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    protected void handleSingleLineFields(String field) {
        // Since there is no other flag, passing in this method means
        // that _isDiseaseName is truthy

        // Sanitize the field by removing identifier and trailing data since the field
        // is full of irrelevant data, e.g:
        // *100660 ALDEHYDE DEHYDROGENASE, FAMILY 3, SUBFAMILY A, MEMBER 1; ALDH3A1

        // Retrieving only the first part of the field, e.g:
        // *100660 ALDEHYDE DEHYDROGENASE, FAMILY 3, SUBFAMILY A, MEMBER 1
        String rawDiseaseName = field.split(Fields.Name.SYNONYMS_SEPARATOR)[0];

        // Split each of its tokens, e.g:
        // [*100660] [ALDEHYDE] [DEHYDROGENASE,] [FAMILY 3,] [SUBFAMILY A,] [MEMBER 1]
        String[] rawDiseaseNameTokens = rawDiseaseName.split(" ");

        // Join the name without the first identifier, e.g:
        // "ALDEHYDE DEHYDROGENASE, FAMILY 3, SUBFAMILY A, MEMBER 1"
        String diseaseName = LuceneIndexerBase.getJoinedStringCollection(
                Arrays.asList(rawDiseaseNameTokens)
                    .subList(1, rawDiseaseNameTokens.length)
        );

        parsedEntities.add(new Disease(diseaseName));
        _isDiseaseName = false;
    }

    /**
     * @inheritDoc
     */
    @Override
    protected boolean isAnyFlagRaised() {
        return _isDiseaseName;
    }

    /**
     * @inheritDoc
     */
    @Override
    protected boolean isAnyMultiLineFieldRaised() {
        return _isDiseaseSymptoms;
    }

    /**
     * @inheritDoc
     */
    @Override
    protected void setFlags(String field) {
        if (field.contains(Fields.Name.FIELD)) {
            _isDiseaseName = true;
        }

        else if (field.contains(Fields.Symptoms.FIELD)) {
            _isDiseaseSymptoms = true;
        }
    }

}
