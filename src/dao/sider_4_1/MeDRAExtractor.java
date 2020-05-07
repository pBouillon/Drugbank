package dao.sider_4_1;

import common.Configuration;
import common.pojo.Drug;
import org.ini4j.Wini;
import util.extractor.MySQLExtractorBase;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Extract information from the MeDRA databases
 * @see SiderDao
 */
public class MeDRAExtractor extends MySQLExtractorBase<Drug> {

    /**
     * @inheritDoc
     */
    @Override
    public List<Drug> extract() {
        Connection connection = getConnection();

        Statement statement = null;
        ResultSet resultSet = null;

        // TODO: data extraction
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(
                    "select cui from meddra limit 3");

            while (resultSet.next()) {
                System.out.println(resultSet.getString("cui"));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.exit(1);
        } finally {
            // it is a good idea to release
            // resources in a finally{} block
            // in reverse-order of their creation
            // if they are no-longer needed

            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException ignored) { } // ignore

                resultSet = null;
            }

            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ignored) { } // ignore

                statement = null;
            }

            if (connection != null)
            {
                try {
                    connection.close();
                } catch (SQLException ignored) { }

                connection = null;
            }
        }

        return null;
    }

    /**
     * Create the MySQL connection string from the configuration constants
     * For its format, see: https://dev.mysql.com/doc/connector-j/5.1/en/connector-j-usagenotes-connect-drivermanager.html
     *
     * @inheritDoc
     */
    @Override
    public String getConnectionString() {
        // Retrieve .ini file
        Wini ini = null;
        try {
            ini = new Wini(new File(Configuration.Sider.Paths.INI));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        // Retrieve the relevant fields
        String host = ini.get(
                Configuration.Sider.INI_BLOCK_NAME,
                "host");

        String database = ini.get(
                Configuration.Sider.INI_BLOCK_NAME,
                "database");

        String user = ini.get(
                Configuration.Sider.INI_BLOCK_NAME,
                "user");

        String password = ini.get(
                Configuration.Sider.INI_BLOCK_NAME,
                "password");

        // Create the connection string
        return String.format(
                "jdbc:mysql://%s/%s?user=%s&password=%s" +
                        "&useUnicode=true" +
                        "&useJDBCCompliantTimezoneShift=true" +
                        "&useLegacyDatetimeCode=false" +
                        "&serverTimezone=UTC",
                host, database, user, password);
    }

}
