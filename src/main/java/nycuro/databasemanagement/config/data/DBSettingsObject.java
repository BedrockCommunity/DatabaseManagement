package nycuro.databasemanagement.config.data;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class DBSettingsObject {

    /**
     * Count of databases
     */
    protected Map<String, Boolean> usedDatabases;

    /**
     * The type of database. Actually only SQLITE3 and MySQL
     */
    protected Map<String, String> typeDatabase;

    /**
     * Settings of database
     */
    protected Map<String, Map<String, String>> databaseOptions;

    /**
     * Poolsize of Databases
     */
    protected Map<String, Integer> poolsize;

    /**
     * Messages showed on console and gameplay.
     */
    protected Map<String, String> messages;

    public DBSettingsObject(Map<String, Boolean> countDatabases, Map<String, String> typeDatabase, Map<String, Map<String, String>> databaseOptions, Map<String, Integer> poolsize, Map<String, String> messages) {
        this.usedDatabases = countDatabases;
        this.typeDatabase = typeDatabase;
        this.messages = messages;
        this.poolsize = poolsize;
        this.databaseOptions = databaseOptions;
    }
}
