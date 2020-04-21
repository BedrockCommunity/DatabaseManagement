package nycuro.databasemanagement.databases.api;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

public class DatabaseAPI {

    public static HikariDataSource DATASOURCE_SQLITE;
    public static HikariDataSource DATASOURCE_MYSQL;

    public void createDatabaseSQLITE(String name, int poolsize, String... variables) {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("org.sqlite.JDBC");
        config.setJdbcUrl("jdbc:sqlite:plugins/DatabaseManagement/databases/sqlite3.db");
        config.addDataSourceProperty("databaseName", name);

        DATASOURCE_SQLITE = new HikariDataSource(config);
        DATASOURCE_SQLITE.setMaximumPoolSize(poolsize);

        createTableSQLITE(name, variables);
    }

    public void createDatabaseMYSQL(String name, int poolsize, Map<String, String> information, String... variables) {
        HikariConfig config = new HikariConfig();
        config.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
        config.addDataSourceProperty("serverName", information.get("adress"));
        config.addDataSourceProperty("port", information.get("port"));
        config.addDataSourceProperty("databaseName", name);
        config.addDataSourceProperty("user", information.get("username"));
        config.addDataSourceProperty("password", information.get("password"));

        DATASOURCE_MYSQL = new HikariDataSource(config);
        DATASOURCE_MYSQL.setMaximumPoolSize(poolsize);

        createTableMySQL(name, variables);
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

    /*
    Exemple of variable:
    (`uuid` varchar, `name` varchar, `language` int, `job` int, `kills` int, `deaths` int, `cooldown` INTEGER, `experience` INTEGER, `level` int, `necesary` INTEGER, `coins` REAL, `time` INTEGER)
    */
    private void createTableMySQL(String name, String... variables) {
        String query = "create table if not exists " + name +" variables";

        try (Connection connection = DATASOURCE_SQLITE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
