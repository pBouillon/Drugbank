package dao.omim.txt;

import common.pojo.Disease;
import util.parser.IParser;
import util.parser.UnstructuredTextParserBase;

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
         * Used when the following line will be the disease's name
         */
        private static final String DISEASE = "*FIELD* TI";

        /**
         * Used when the following line will be the disease's symptoms
         */
        private static final String ASSOCIATED_SYMPTOMS = "*FIELD* CS";

    }

    /**
     * @inheritDoc
     */
    @Override
    protected void handleMultilineFields(String field) {
        if (field.isEmpty()) {
            _isDiseaseSymptoms = false;
            return;
        }

        Disease currentDisease = parsedEntities.peek();

        if (_isDiseaseSymptoms) {
            currentDisease.getSynonyms().add(field);
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    protected void handleSingleLineFields(String field) {
        // Since there is no other flag, passing in this method means
        // that _isDiseaseName is truthy
        parsedEntities.add(new Disease(field));
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
        if (field.contains(Fields.DISEASE)) {
            _isDiseaseName = true;
        }

        else if (field.contains(Fields.ASSOCIATED_SYMPTOMS)) {
            _isDiseaseSymptoms = true;
        }
    }

}
