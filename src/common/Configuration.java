package common;

/**
 * Project configuration constants
 */
public class Configuration {

    /**
     * ATC constants
     */
    public static class Atc {

        /**
         * ATC paths
         */
        public static class Paths {

            /**
             * Default data source location
             */
            public static final String SOURCE = "./data/stitch_atc/br08303.keg";

            /**
             * Lucene indexes paths
             */
            public static final String INDEX = "./src/dao/atc/indexes/";

        }

    }

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
                 * Drug's indication
                 */
                public static final String INDICATION = "indication";

                /**
                 * Generic name of the drug
                 */
                public static final String NAME = "name";

                /**
                 * Drug's synonyms
                 */
                public static final String SYNONYMS = "synonyms";

                /**
                 * Drug's toxicity
                 */
                public static final String TOXICITY = "toxicity";

                /**
                 * Drug's ATC
                 */
                public static final String ATC = "atc";

                /**
                 * Drug's compound id
                 */
                public static final String COMPOUND_ID = "compound_id";

            }

        }

    }

    /**
     * Sider constants
     */
    public static class Sider {

        /**
         * Block name in the .ini file containing the mysql information
         */
        public static final String INI_BLOCK_NAME = "mysql";

        /**
         * Sider paths
         */
        public static class Paths {

            /**
             * .ini configuration file for database access
             */
            public static final String INI = "./data/sider_4.1/database.ini";

            /**
             * Sider indexes paths
             */
            public static final String INDEX = "./src/dao/sider_4.1/indexes/";

        }

    }

    /**
     * Stitch constants
     */
    public static class Stitch {

        /**
         * Stitch paths
         */
        public static class Paths {

            /**
             * Default data source location
             */
            public static final String SOURCE = "./data/stitch_atc/chemical.sources.v5.0.tsv";

            /**
             * Lucene indexes paths
             */
            public static final String INDEX = "./src/dao/stitch/indexes/";

        }

    }
}
