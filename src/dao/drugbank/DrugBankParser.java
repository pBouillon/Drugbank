package dao.drugbank;

import common.pojo.Drug;
import util.parser.IParser;
import util.parser.UnstructuredTextParserBase;

/**
 * Drug bank file parser
 * @see IParser
 */
public class DrugBankParser extends UnstructuredTextParserBase<Drug> {

    /**
     * Flag raised when the following field is the drug's generic name
     */
    private boolean _isGenericNameField = false;

    /**
     * Flag raised when the following field is the drug's indications
     */
    private boolean _isIndicationField = false;

    /**
     * Flag raised when the following field marks the beginning of a new drug card
     */
    private boolean _isNewDrugCardField = false;

    /**
     * Multiline flag raised when the following fields are synonyms of the current drug
     */
    private boolean _isSynonymField = false;

    /**
     * Flag raised when the following field is the drug's toxicity
     */
    private boolean _isToxicityField = false;

    /**
     * Represent the known fields of the parsed file
     */
    private static class Fields {

        /**
         * Used when beginning a new drug card
         */
        public static final String BEGIN_DRUGCARD = "#BEGIN_DRUGCARD";

        /**
         * Used when the following line will be the drug's indication
         */
        public static final String INDICATION = "# Indication";

        /**
         * Used when the following line will be the drug's generic name
         */
        public static final String GENERIC_NAME = "# Generic_Name";

        /**
         * Used when the following line will be the drug's synonyms
         */
        public static final String SYNONYMS = "# Synonyms";

        /**
         * Used when the following line will be the drug's toxicity
         */
        public static final String TOXICITY = "# Toxicity";

    }

    @Override
    protected void handleMultilineFields(String field) {
        // If the line is empty, lower all flags
        if (field.isEmpty()) {
            _isSynonymField = false;
            return;
        }

        Drug currentDrug = parsedEntities.peek();

        if (_isSynonymField) {
            currentDrug.getSynonyms().add(field);
        }
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void handleSingleLineFields(String field) {
        Drug currentDrug = null;

        if (!parsedEntities.isEmpty()) {
            currentDrug = parsedEntities.peek();
        }

        // Set the indication of the lastly added Drug
        if (_isIndicationField) {
            currentDrug.setIndication(field);
            _isIndicationField = false;
            return;
        }

        // Set the generic name of the lastly added Drug
        if (_isGenericNameField) {
            currentDrug.setName(field);
            _isGenericNameField = false;
            return;
        }

        // Set the toxicity of the lastly added Drug
        if (_isToxicityField) {
            currentDrug.setToxicity(field);
            _isToxicityField = false;
            return;
        }

        // Create a new instance of Drug for this new card
        if (_isNewDrugCardField) {
            parsedEntities.add(new Drug());
            _isNewDrugCardField = false;
        }
    }

    @Override
    protected boolean isAnyFlagRaised() {
        return _isGenericNameField || _isIndicationField
                || _isNewDrugCardField ||_isToxicityField;
    }

    @Override
    protected boolean isAnyMultiLineFieldRaised() {
        return _isSynonymField;
    }

    @Override
    protected void setFlags(String field) {
        if (field.contains(Fields.BEGIN_DRUGCARD)) {
            _isNewDrugCardField = true;
        }

        else if (field.contains(Fields.INDICATION)) {
            _isIndicationField = true;
        }

        else if (field.contains(Fields.GENERIC_NAME)) {
            _isGenericNameField = true;
        }

        else if (field.contains(Fields.SYNONYMS)) {
            _isSynonymField = true;
        }

        else if (field.contains(Fields.TOXICITY)) {
            _isToxicityField = true;
        }
    }

}
