package nycuro.databasemanagement.api;

import cn.nukkit.utils.TextFormat;
import nycuro.databasemanagement.RegisteryLoader;
import nycuro.databasemanagement.config.SettingsAPI;

public class RegisteryAPI {

    public static RegisteryLoader mainAPI;

    public static SettingsAPI settingsAPI;

    public static String prefix = "&7[&aDatabaseManagement&7]&r";

    public static void sendLog(TextFormat color, String message) {
        mainAPI.getLogger().info(TextFormat.colorize(prefix + " " + color + message));
    }
}
