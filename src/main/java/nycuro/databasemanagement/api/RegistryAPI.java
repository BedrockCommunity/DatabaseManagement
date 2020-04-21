package nycuro.databasemanagement.api;

import cn.nukkit.utils.TextFormat;
import nycuro.databasemanagement.RegistryLoader;
import nycuro.databasemanagement.config.SettingsAPI;
import nycuro.databasemanagement.databases.api.DatabaseAPI;

public class RegistryAPI {

    public static RegistryLoader mainAPI;

    public static SettingsAPI settingsAPI;

    public static DatabaseAPI databaseAPI;

    public static String prefix = "&7[&aDatabaseManagement&7]&r";

    public static void sendLog(TextFormat color, String message) {
        mainAPI.getLogger().info(TextFormat.colorize(prefix + " " + color + message));
    }
}
