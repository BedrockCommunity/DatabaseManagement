package nycuro.databasemanagement.databases.objects;

import lombok.Getter;

public enum DatabaseType {
    SQLITE3("SQLITE3"),
    MYSQL("MYSQL");

    @Getter
    protected String type;

    DatabaseType(String type) {
        this.type = type;
    }
}
