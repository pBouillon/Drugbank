package factory;

/**
 * Singleton to provide a unique factory instance
 * @see RepositoryFactory
 */
public class RepositoryFactorySingleton {

    /**
     * Private constructor for singleton purposes
     */
    private RepositoryFactorySingleton() { }

    /**
     * Unique instance of the RepositoryFactory
     * @see RepositoryFactory
     */
    public static RepositoryFactory instance = RepositoryFactoryHolder.instance;

    /**
     * Inner private class for singleton generation purposes
     * See: https://stackoverflow.com/questions/15019306/regarding-static-holder-singleton-pattern
     */
    private static class RepositoryFactoryHolder {

        /**
         * Unique instance of the class held
         */
        private final static RepositoryFactory instance = new RepositoryFactory();

    }

}
