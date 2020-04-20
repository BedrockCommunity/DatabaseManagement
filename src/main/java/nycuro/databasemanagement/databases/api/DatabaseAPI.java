package nycuro.databasemanagement.databases.api;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

public class DatabaseAPI {

    private static HikariDataSource DATASOURCE_SQLITE;
    private static HikariDataSource DATASOURCE_MYSQL;

    public void createDatabaseSQLITE(String name, int poolsize) {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("org.sqlite.JDBC");
        config.setJdbcUrl("jdbc:sqlite:plugins/DatabaseManagement/databases/sqlite3.db");
        DATASOURCE_SQLITE = new HikariDataSource(config);
        DATASOURCE_SQLITE.setMaximumPoolSize(poolsize);
    }

    public void createDatabaseMYSQL(String name, int poolsize, Map<String, String> information) {
        /*String address = "localhost";
        String name = "homeskyblock";
        String username = "root";
        String password = "";

        HikariConfig config = new HikariConfig();
        config.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
        config.addDataSourceProperty("serverName", address);
        config.addDataSourceProperty("port", "3306");
        config.addDataSourceProperty("databaseName", name);
        config.addDataSourceProperty("user", username);
        config.addDataSourceProperty("password", password);
        DATASOURCE_HOMESF = new HikariDataSource(config);

        DATASOURCE_HOMESF.setMaximumPoolSize(10);

        String query = "create table if not exists homes (`name` varchar(20), `x` int, `y` int, `z` int, `worldname` varchar(20), `homename` varchar(20))";

        try (Connection connection = DATASOURCE_HOMESF.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
    }

    /*
    Exemple of variable:
    (`uuid` varchar, `name` varchar, `language` int, `job` int, `kills` int, `deaths` int, `cooldown` INTEGER, `experience` INTEGER, `level` int, `necesary` INTEGER, `coins` REAL, `time` INTEGER)
    */
    private void createTableSQLITE(String name, String... variables) {
        String query = "create table if not exists " + name +" variables";

        try (Connection connection = DATASOURCE_SQLITE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
