package nycuro.databasemanagement.config.data;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.Set;

@Getter
@Setter
public class DBSettingsObject {

    /**
     * The count of databases you want to have generate by plugin
     */
    protected int countDatabases;

    /**
     * The type of database. Actually only SQLITE3 and MySQL
     */
    protected Map<String, String> typeDatabase;

    /**
     * Settings of database
     */
    protected Map<String, Map<String, String>> databaseOptions;

    /**
     * Messages showed on console and gameplay.
     */
    protected Map<String, String> messages;

    public DBSettingsObject(int countDatabases, Map<String, String> typeDatabase, Map<String, Map<String, String>> databaseOptions, Map<String, String> messages) {
        this.countDatabases = countDatabases;
        this.typeDatabase = typeDatabase;
        this.messages = messages;
        this.databaseOptions = databaseOptions;
    }
}
