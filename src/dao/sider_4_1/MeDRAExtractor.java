package dao.sider_4_1;

import common.Configuration;
import common.pojo.Symptom;
import org.ini4j.Wini;
import util.extractor.SQLExtractorBase;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Extract information from the MeDRA databases
 * @see SiderDao
 */
public class MeDRAExtractor extends SQLExtractorBase<Symptom> {

    /**
     * @inheritDoc
     */
    @Override
    public List<Symptom> extract() {
        List<Symptom> symptoms = new ArrayList<>();

        // SQL query to fetch all side effects name
        final String sqlQuerySideEffectsName = "SELECT" +
                " m_se.side_effect_name as name," +
                " m_se.cui_of_meddra_term," +
                " '' as stitch_list_in," +
                " GROUP_CONCAT(stitch_compound_id1)  as stitch_list_se" +
                " FROM" +
                " meddra_all_se m_se" +
                " WHERE" +
                " m_se.meddra_concept_type = 'PT'" +
                " group by  m_se.cui_of_meddra_term;";

        final String sqlQuerySideIndicationsName = "SELECT" +
                " m_ai.meddra_concept_name as name," +
                " m_ai.cui_of_meddra_term," +
                " '' as stitch_list_se," +
                " GROUP_CONCAT(m_ai.stitch_compound_id) as stitch_list_in" +
                " FROM" +
                " meddra_all_indications m_ai" +
                " WHERE" +
                " m_ai.meddra_concept_type = 'PT'" +
                " group by  m_ai.cui_of_meddra_term;";

        populateSymptomsFromQuery(sqlQuerySideEffectsName, symptoms);
        populateSymptomsFromQuery(sqlQuerySideIndicationsName, symptoms);
        return symptoms;
    }

    private void populateSymptomsFromQuery(String query, List<Symptom> symptoms) {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            // Result buffers
            String sideEffectName;
            String sideEffectCUI;
            String[] stitchCompoundIn;
            String[] stitchCompoundSe;
            Symptom newSideEffect;

            // Process each result
            while (resultSet.next()) {
                sideEffectName = resultSet.getString("name");
                sideEffectCUI = resultSet.getString("cui_of_meddra_term");
                stitchCompoundSe = resultSet.getString("stitch_list_se").split(",");
                stitchCompoundIn  = resultSet.getString("stitch_list_in").split(",");
                newSideEffect = new Symptom();
                newSideEffect.setName(sideEffectName);
                newSideEffect.setCui(sideEffectCUI);
                newSideEffect.setSideEffectOf(Arrays.asList(stitchCompoundSe));
                newSideEffect.setIndicationOf(Arrays.asList(stitchCompoundIn));
                symptoms.add(newSideEffect);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
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
