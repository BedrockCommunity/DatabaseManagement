package nycuro.databasemanagement.api;


import cn.nukkit.utils.TextFormat;
import nycuro.databasemanagement.RegisteryLoader;
import nycuro.databasemanagement.config.SettingsAPI;

public class DBAPI {

    public static RegisteryLoader mainAPI;

    public static SettingsAPI settingsAPI;

    public static void sendLog(String text) {
        mainAPI.getLogger().info(TextFormat.colorize("&a" + text));
    }
}
