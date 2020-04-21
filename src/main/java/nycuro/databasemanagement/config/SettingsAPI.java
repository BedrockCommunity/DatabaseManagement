package nycuro.databasemanagement.config;

import cn.nukkit.utils.TextFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import nycuro.databasemanagement.api.RegistryAPI;
import nycuro.databasemanagement.config.data.DBSettingsObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

import static nycuro.databasemanagement.api.RegistryAPI.mainAPI;

/**
 * author: NycuRO
 * DatabaseManagement Project
 * API 1.0.0
 */
public class SettingsAPI {

    public DBSettingsObject dbSettingsObject;

    public void init() {
        try {
            File file = new File(mainAPI.getDataFolder() + "/settings.json");

            RegistryAPI.sendLog(TextFormat.YELLOW, "Checking for existance of settings file... ");

            if (!file.exists()) {
                RegistryAPI.sendLog(TextFormat.YELLOW, "I've not found a file... Let's put all information..");

                FileWriter fileWriter = new FileWriter(file);

                RegistryAPI.sendLog(TextFormat.YELLOW, "Setting Usage of Databases...");

                Map<String, Boolean> used = new HashMap<>();
                used.put("1", true); // Choose if database 1 is enabled or not.
                used.put("2", true); // Choose if database 2 is enabled or not

                RegistryAPI.sendLog(TextFormat.YELLOW, "Setting Type of Databases...");

                Map<String, String> databaseType = new HashMap<>();
                databaseType.put("1", "SQLITE3"); // Type of Database with Name 1
                databaseType.put("2", "MySQL"); // Type of Database with Name 2

                Map<String, Map<String, String>> databaseOptions = new HashMap<>();

                // Database options. Only for MySQL.
                Map<String, String> options = new HashMap<>();
                options.put("adress", "localhost");
                options.put("username", "username");
                options.put("password", "password");
                options.put("port", "3306");

                databaseOptions.put("2", options);

                RegistryAPI.sendLog(TextFormat.YELLOW, "Setting Poolsize of Databases...");

                Map<String, Integer> poolSizeOptions = new HashMap<>();
                poolSizeOptions.put("1", 10);
                poolSizeOptions.put("2", 10);

                RegistryAPI.sendLog(TextFormat.YELLOW, "Setting Plugin Messages...");

                Map<String, String> pluginMessages = new HashMap<>();
                pluginMessages.put("nycuro.prefix", RegistryAPI.prefix); // Message Data and actual string of message

                RegistryAPI.sendLog(TextFormat.YELLOW, "Writing data...");

                fileWriter.write(serializeData(used, databaseType, databaseOptions, poolSizeOptions, pluginMessages));
                fileWriter.close();

                RegistryAPI.sendLog(TextFormat.GREEN, "Finished!");
            } else {
                RegistryAPI.sendLog(TextFormat.YELLOW, "I've found an file... Let's get all information..");

                byte[] jsonData = Files.readAllBytes(file.toPath());

                ObjectMapper objectMapper = new ObjectMapper();

                JsonNode rootNode = objectMapper.readTree(jsonData);

                RegistryAPI.sendLog(TextFormat.YELLOW, "Creating maps..");

                Map<String, Boolean> usedDatabases = new HashMap<>();
                Map<String, String> typeDatabase = new HashMap<>();
                Map<String, String> messages = new HashMap<>();
                Map<String, Map<String, String>> databaseOptions = new HashMap<>();
                Map<String, String> options = new HashMap<>();
                Map<String, Integer> poolsize = new HashMap<>();

                RegistryAPI.sendLog(TextFormat.YELLOW, "Input information..");

                Iterator<Map.Entry<String, JsonNode>> jsonNodeTypeDatabase = rootNode.get("usedDatabases").fields();
                while (jsonNodeTypeDatabase.hasNext()) {
                    Map.Entry<String, JsonNode> entry = jsonNodeTypeDatabase.next();
                    usedDatabases.put(entry.getKey(), entry.getValue().asBoolean());
                }

                Iterator<Map.Entry<String, JsonNode>> jsonNodeDatabaseOptions = rootNode.get("databaseOptions").fields();
                while (jsonNodeDatabaseOptions.hasNext()) {
                    Map.Entry<String, JsonNode> entry = jsonNodeDatabaseOptions.next();
                    Iterator<Map.Entry<String, JsonNode>> iter = entry.getValue().fields();
                    options.clear();
                    while (iter.hasNext()) {
                        Map.Entry<String, JsonNode> entryValues = iter.next();
                        if (entryValues.getKey().equals("password")) {
                            options.put("password", entryValues.getValue().asText());
                        }
                        if (entryValues.getKey().equals("port")) {
                            options.put("port", entryValues.getValue().asText());
                        }
                        if (entryValues.getKey().equals("adress")) {
                            options.put("adress", entryValues.getValue().asText());
                        }
                        if (entryValues.getKey().equals("username")) {
                            options.put("username", entryValues.getValue().asText());
                        }
                    }
                    databaseOptions.put(entry.getKey(), options);
                }

                Iterator<Map.Entry<String, JsonNode>> jsonNodePoolsize = rootNode.get("poolsize").fields();
                while (jsonNodePoolsize.hasNext()) {
                    Map.Entry<String, JsonNode> entry = jsonNodePoolsize.next();
                    Iterator<Map.Entry<String, JsonNode>> iter = entry.getValue().fields();
                    while (iter.hasNext()) {
                        Map.Entry<String, JsonNode> entryValues = iter.next();
                        poolsize.put(entryValues.getKey(), entryValues.getValue().asInt());
                    }
                }
                for (Map.Entry<String, Integer> entry : poolsize.entrySet()) {
                    RegistryAPI.sendLog(TextFormat.AQUA, "Poolsize: " + entry.getKey() + " " + entry.getValue());
                }

                Iterator<Map.Entry<String, JsonNode>> jsonNodeMessages = rootNode.get("messages").fields();
                while (jsonNodeMessages.hasNext()) {
                    Map.Entry<String, JsonNode> entry = jsonNodeMessages.next();
                    messages.put(entry.getKey(), entry.getValue().toString());
                }

                RegistryAPI.sendLog(TextFormat.YELLOW, "Add information to DBSettingsObject..");

                dbSettingsObject = new DBSettingsObject(usedDatabases, typeDatabase, databaseOptions, poolsize, messages);

                RegistryAPI.sendLog(TextFormat.GREEN, "Done!");
            }
        } catch (IOException e) {
            e.fillInStackTrace();
        }
    }


    private String serializeData(Map<String, Boolean> usedDatabases, Map<String, String> typeDatabase, Map<String, Map<String, String>> databaseOptions, Map<String, Integer> poolsize, Map<String, String> messages) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        DBSettingsObject dbSettingsObject = new DBSettingsObject(usedDatabases, typeDatabase, databaseOptions, poolsize, messages);

        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(dbSettingsObject);
    }
}