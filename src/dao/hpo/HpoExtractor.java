package dao.hpo;

import common.Configuration;
import common.pojo.Disease;
import dao.sider_4_1.SiderDao;
import util.extractor.SQLExtractorBase;

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
public class HpoExtractor extends SQLExtractorBase<Disease> {

    /**
     * @inheritDoc
     */
    @Override
    public List<Disease> extract() {
        Stack<Disease> diseases = new Stack<>();
        // SQL query to fetch all side effects name
        final String sqlQuery ="SELECT\n" +
                "    disease_db,\n" +
                "    disease_id,\n" +
                "    disease_label,\n" +
                "    sign_id\n" +
                "FROM\n" +
                "    phenotype_annotation;";


        // Perform query and process the results
        try (Connection connection = getConnection();
                 Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(sqlQuery)) {
            // Result buffers
            Disease newDisease;

            // Process each result
            while (resultSet.next()) {

                newDisease = new Disease();
                newDisease.setName(resultSet.getString("disease_label"));
                newDisease.set_hpoId(resultSet.getString("disease_id"));
                newDisease.set_hpoDbName(resultSet.getString("disease_db"));
                newDisease.set_hpoSignId(resultSet.getString("sign_id"));
                diseases.add(newDisease);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            System.exit(1);
        }

        return diseases;
    }

    /**
     * Create the SQLite connection string from the configuration constants
     *
     * @inheritDoc
     */
    @Override
    public String getConnectionString() {
        return String.format("jdbc:sqlite:%s",Configuration.HPO.Paths.PATH);
    }

}
