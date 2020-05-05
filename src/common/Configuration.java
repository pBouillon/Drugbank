package common;

/**
 * Project configuration constants
 */
public class Configuration {

    public static class DrugBank {

        /**
         * Drugbank paths constants
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
    public static class Lucene { }

}
