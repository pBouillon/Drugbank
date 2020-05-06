package dao.drugbank;

import common.pojo.Drug;
import parser.IParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

/**
 * Drug bank file parser
 * @see IParser
 */
public class DrugBankParser implements IParser<Drug> {

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

    /**
     * @inheritDoc
     */
    @SuppressWarnings("ConstantConditions")
    @Override
    public Iterable<Drug> extractData(Path source) throws IOException {
        Stack<Drug> drugs = new Stack<>();

        // Atomic boolean for file parsing
        AtomicBoolean isGenericNameField = new AtomicBoolean(false);
        AtomicBoolean isIndicationField = new AtomicBoolean(false);
        AtomicBoolean isNewDrugCardField = new AtomicBoolean(false);
        AtomicBoolean isSynonymField = new AtomicBoolean(false);
        AtomicBoolean isToxicityField = new AtomicBoolean(false);

        // Supplier to indicate if any flag is raised
        BooleanSupplier isAnyFlagRaised = ()
            -> isGenericNameField.get() || isIndicationField.get()
                || isNewDrugCardField.get() ||isToxicityField.get();

        // Check if any multi-line field flag is raised
        BooleanSupplier isAnyMultiLineFieldRaised = isSynonymField::get;

        // Handle fields split among several lines
        Consumer<String> handleMultilinesFields = (field) -> {
            // Skip handling if no flag is raised
            if (!isAnyMultiLineFieldRaised.getAsBoolean()) {
                return;
            }

            // If the line is empty, lower all flags
            if (field.isEmpty()) {
                isSynonymField.set(false);
                return;
            }

            Drug currentDrug = drugs.peek();

            if (isSynonymField.get()) {
                currentDrug.getSynonyms().add(field);
            }
        };

        Consumer<String> handleSingleLineFields = (field) -> {
            Drug currentDrug = null;
            if (!drugs.isEmpty()) {
                currentDrug = drugs.peek();
            }

            // Set the indication of the lastly added Drug
            if (isIndicationField.get()) {
                currentDrug.setIndication(field);
                isIndicationField.set(false);
                return;
            }

            // Set the generic name of the lastly added Drug
            if (isGenericNameField.get()) {
                currentDrug.setName(field);
                isGenericNameField.set(false);
                return;
            }

            // Set the toxicity of the lastly added Drug
            if (isToxicityField.get()) {
                currentDrug.setToxicity(field);
                isToxicityField.set(false);
                return;
            }

            // Create a new instance of Drug for this new card
            if (isNewDrugCardField.get()) {
                drugs.add(new Drug());
                isNewDrugCardField.set(false);
            }
        };

        // Set the flags according to the currently read field
        Consumer<String> setFlags = (field) -> {
            if (field.contains(Fields.BEGIN_DRUGCARD)) {
                isNewDrugCardField.set(true);
            }

            else if (field.contains(Fields.INDICATION)) {
                isIndicationField.set(true);
            }

            else if (field.contains(Fields.GENERIC_NAME)) {
                isGenericNameField.set(true);
            }

            else if (field.contains(Fields.SYNONYMS)) {
                isSynonymField.set(true);
            }

            else if (field.contains(Fields.TOXICITY)) {
                isToxicityField.set(true);
            }
        };

        Files.lines(source)
            .forEachOrdered(line -> {
                // Handle fields split among several lines
                handleMultilinesFields.accept(line);

                // Skip empty lines
                if (line.isEmpty()) {
                    return;
                }

                // If no flags are raised try to set them instead of processing
                if (!isAnyFlagRaised.getAsBoolean()) {
                    setFlags.accept(line);
                    return;
                }

                handleSingleLineFields.accept(line);
            });

        return drugs;
    }

}
