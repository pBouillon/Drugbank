package util.extractor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public abstract class MySQLExtractorBase<T> extends DatabaseExtractorBase<T> {

    /**
     * @inheritDoc
     */
    @Override
    abstract public List<T> extract();

    /**
     * Initialize and return a new connection to the MySQL database
     * @return The SQL Connection object
     */
    protected Connection getConnection() {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(getConnectionString());
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());

            System.exit(1);
        }

        return connection;
    }

    /**
     * Create and return the appropriate connection string
     * @return The correctly formatted connection string
     */
    abstract protected String getConnectionString();

}
