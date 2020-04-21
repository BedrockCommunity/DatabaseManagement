package nycuro.databasemanagement;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.TextFormat;
import nycuro.databasemanagement.api.RegistryAPI;
import nycuro.databasemanagement.config.SettingsAPI;

import java.io.File;
import java.nio.file.Files;

import static nycuro.databasemanagement.api.RegistryAPI.settingsAPI;

public class RegistryLoader extends PluginBase {

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
        RegistryAPI.mainAPI = this;
        RegistryAPI.settingsAPI = new SettingsAPI();
    }

    private void settingsConfig() {
        final File principalFile = new File(this.getDataFolder().toString());
        if (!principalFile.exists()) {
            try {
                Files.createDirectory(principalFile.toPath());
                RegistryAPI.sendLog(TextFormat.GREEN, "Principal Folder succesfully created!");
            } catch (Exception e) {
                RegistryAPI.sendLog(TextFormat.RED, "There was a problem in creating Principal Folder..");
                e.printStackTrace();
            }
        } else {
            RegistryAPI.sendLog(TextFormat.YELLOW, "Principal Folder already exist..");
        }
        final File databaseFile = new File(this.getDataFolder().toString() + "/databases/");
        if (!databaseFile.exists()) {
            try {
                Files.createDirectory(databaseFile.toPath());
                RegistryAPI.sendLog(TextFormat.GREEN, "Databases Folder succesfully created!");
            } catch (Exception e) {
                RegistryAPI.sendLog(TextFormat.RED, "There was a problem in creating Databases Folder..");
                e.printStackTrace();
            }
        } else {
            RegistryAPI.sendLog(TextFormat.YELLOW, "Databases Folder already exists..");
        }
        settingsAPI.init();
    }
}
