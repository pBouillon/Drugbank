package dao.drugbank;

import common.pojo.Drug;
import util.IParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.Queue;
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
         * Used when the following line will be the drug's generic name
         */
        public static final String GENERIC_NAME = "# Generic_Name";

    }

    /**
     * @inheritDoc
     */
    @Override
    public Iterable<Drug> extractData(Path source) throws IOException {
        Queue<Drug> drugs = new LinkedList<>();

        // Atomic boolean for file parsing
        AtomicBoolean isGenericNameField = new AtomicBoolean(false);
        AtomicBoolean isNewDrugCardField = new AtomicBoolean(false);

        // Supplier to indicate if any flag is raised
        BooleanSupplier isAnyFlagRaised = ()
            -> isGenericNameField.get() || isNewDrugCardField.get();

        // Set the flags according to the currently read field
        Consumer<String> setFlags = (field) -> {
            if (field.contains(Fields.BEGIN_DRUGCARD)) {
                isNewDrugCardField.set(true);
                return;
            }

            if (field.contains(Fields.GENERIC_NAME)) {
                isGenericNameField.set(true);
            }
        };

        Files.lines(source)
            .forEachOrdered(line -> {
                // Skip empty lines
                if (line.isEmpty()) {
                    return;
                }

                // If no flags are raised try to set them instead of processing
                if (!isAnyFlagRaised.getAsBoolean()) {
                    setFlags.accept(line);
                    return;
                }

                // Set the generic name of the lastly added Drug
                if (isGenericNameField.get()) {
                    // Drug should not be null here
                    // If the parsed file is correctly formatted, there should be a new drug card before anything else
                    // which will create a new Drug instance in the Queue
                    //noinspection ConstantConditions
                    drugs.peek()
                        .setName(line);
                    isGenericNameField.set(false);
                    return;
                }

                // Create a new instance of Drug for this new card
                if (isNewDrugCardField.get()) {
                    drugs.add(new Drug());
                    isNewDrugCardField.set(false);
                }
            });

        return drugs;
    }

}
