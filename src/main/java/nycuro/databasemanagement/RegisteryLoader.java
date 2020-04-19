package nycuro.databasemanagement;

import cn.nukkit.plugin.PluginBase;
import nycuro.databasemanagement.api.DBAPI;
import nycuro.databasemanagement.config.SettingsAPI;

import java.io.File;

import static nycuro.databasemanagement.api.DBAPI.settingsAPI;

public class RegisteryLoader extends PluginBase {

    @Override
    public void onLoad() {
        // Called once the plugin is LOADED.
        // The point during startup that the plugin is loaded is
        // based on the setting of the load key in the plugin.yml

    }

    @Override
    public void onEnable() {
        registerAPI();
        settingsConfig();
    }

    @Override
    public void onDisable() {
        // Called when plugin is disabled. This would be done by the server when it shuts down
        // so this is a good idea to save any persistant data you need.
        // May also be called if an exception is called during loading/enabling of your plugin
    }

    private void registerAPI() {
        DBAPI.mainAPI = this;
        DBAPI.settingsAPI = new SettingsAPI();
    }

    @SuppressWarnings("unused")
    private void settingsConfig() {
        this.getDataFolder().mkdirs(); // Create folder of DatabaseManagement
        File file = new File(DBAPI.mainAPI.getDataFolder() + "/databases");
        file.mkdirs(); // Create folder where databases are supposed to be
        settingsAPI.init();
    }
}
