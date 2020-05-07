package dao.sider_4_1;

import common.Configuration;
import org.ini4j.Wini;

import java.io.File;
import java.io.IOException;

/**
 * Extract information from the MeDRA databases
 * @see SiderDao
 */
public class MeDRAExtractor {

    /**
     * Create the MySQL connection string from the configuration constants
     * For its format, see: https://dev.mysql.com/doc/connector-j/5.1/en/connector-j-usagenotes-connect-drivermanager.html
     * @return The correctly formatted connection string
     */
    private static String getConnectionString() {
        // Retrieve .ini file
        Wini ini = null;
        try {
            ini = new Wini(new File(Configuration.Sider.Paths.INI));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

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

        return String.format("jdbc:mysql://%s/%s?user=%s&password=%s", host, database, user, password);
    }

}
