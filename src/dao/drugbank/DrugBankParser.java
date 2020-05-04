package dao.drugbank;

import common.pojo.Drug;
import util.IParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class DrugBankParser implements IParser<Drug> {

    private static class Fields {

        public static final String BEGIN_DRUGCARD = "#BEGIN_DRUGCARD";

        public static final String GENERIC_NAME = "# Generic_Name";

    }

    /**
     * @inheritDoc
     */
    @Override
    public List<Drug> extractData(Path source) throws IOException {
        List<Drug> drugs = new ArrayList<>();

        AtomicBoolean isGenericNameField = new AtomicBoolean(false);

        Files.lines(source)
            .forEachOrdered(line -> {
                if (line.isEmpty()) {
                    resetFlags(isGenericNameField);
                    return;
                }

                if (isGenericNameField.get()) {
                    drugs.get(drugs.size() - 1).setName(line);
                    return;
                }

                switch (line) {
                    case Fields.BEGIN_DRUGCARD:
                        drugs.add(new Drug());
                        break;

                    case Fields.GENERIC_NAME:
                        isGenericNameField.set(true);
                        break;
                }

            });

        return drugs;
    }

    private void resetFlags(AtomicBoolean... atomicBooleans) {
        for (AtomicBoolean atomicBoolean : atomicBooleans) {
            atomicBoolean.set(false);
        }
    }

}
