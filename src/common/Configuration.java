package common;

/**
 * Project configuration constants
 */
public class Configuration {

    /**
     * Drugbank constants
     */
    public static class DrugBank {

        /**
         * Drugbank paths
         */
        public static class Paths {

            /**
             * Default data source location
             */
            public static final String SOURCE = "./data/drugbank/drugbank.txt";

            /**
             * Lucene indexes paths
             */
            public static final String INDEX = "./src/dao/drugbank/indexes/";

        }

    }

    /**
     * Lucene constants
     */
    public static class Lucene {

        /**
         * Key used for indexing field per entity
         */
        public static class IndexKey {

            /**
             * Keys of the Drug entity
             * @see Drug
             */
            public static class Drug {

                /**
                 * Generic name of the drug
                 */
                public static final String NAME = "name";

            }

        }

    }

}
