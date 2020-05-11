package dao.sider_4_1;

import common.Configuration;
import common.pojo.Symptom;
import org.ini4j.Wini;
import util.extractor.MySQLExtractorBase;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Stack;

/**
 * Extract information from the MeDRA databases
 * @see SiderDao
 */
public class MeDRAExtractor extends MySQLExtractorBase<Symptom> {

    /**
     * @inheritDoc
     */
    @Override
    public List<Symptom> extract() {
        Stack<Symptom> symptoms = new Stack<>();

        // SQL query to fetch all side effects name
        final String sqlQuerySideEffectsName =
                "SELECT DISTINCT " +
                "    m_se.side_effect_name " +
                "FROM " +
                "    meddra_all_se m_se " +
                "WHERE " +
                "    m_se.meddra_concept_type = 'PT';";

        // Perform query and process the results
        try (Connection connection = getConnection();
                 Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(sqlQuerySideEffectsName)) {
            // Result buffers
            String sideEffectName;
            Symptom newSideEffect;

            // Process each result
            while (resultSet.next()) {
                sideEffectName = resultSet.getString("side_effect_name");

                newSideEffect = new Symptom();
                newSideEffect.setName(sideEffectName);

                symptoms.add(newSideEffect);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            System.exit(1);
        }

        return symptoms;
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
