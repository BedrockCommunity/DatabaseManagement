package nycuro.databasemanagement.config;

import cn.nukkit.utils.TextFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import nycuro.databasemanagement.api.RegisteryAPI;
import nycuro.databasemanagement.config.data.DBSettingsObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

import static nycuro.databasemanagement.api.RegisteryAPI.mainAPI;

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

            RegisteryAPI.sendLog(TextFormat.YELLOW, "Checking for existance of settings file... ");

            if (!file.exists()) {
                RegisteryAPI.sendLog(TextFormat.YELLOW, "I've not found a file... Let's put all information..");

                FileWriter fileWriter = new FileWriter(file);

                RegisteryAPI.sendLog(TextFormat.YELLOW, "Setting Prefix...");

                RegisteryAPI.sendLog(TextFormat.YELLOW, "Setting Count of Databases..");

                int databaseCount = 2;

                RegisteryAPI.sendLog(TextFormat.YELLOW, "Setting Type of Databases...");

                Map<String, String> databaseType = new HashMap<>();
                databaseType.put("1", "SQLITE3"); // Type of Database with Name 1
                databaseType.put("2", "MySQL"); // Type of Database with Name 2

                Map<String, Map<String, String>> databaseOptions = new HashMap<>();
                Map<String, String> options = new HashMap<>();
                // Database options. Only for MySQL.
                options.put("adress", "localhost");
                options.put("username", "username");
                options.put("password", "password");
                options.put("port", "3306");
                databaseOptions.put("2", options); // 2 means name of database

                RegisteryAPI.sendLog(TextFormat.YELLOW, "Setting Plugin Messages...");

                Map<String, String> pluginMessages = new HashMap<>();
                pluginMessages.put("nycuro.prefix", RegisteryAPI.prefix); // Message Data and actual string of message

                RegisteryAPI.sendLog(TextFormat.YELLOW, "Writing data...");

                fileWriter.write(serializeData(databaseCount, databaseType, databaseOptions, pluginMessages));
                fileWriter.close();

                RegisteryAPI.sendLog(TextFormat.GREEN, "Finished!");
            } else {
                RegisteryAPI.sendLog(TextFormat.YELLOW, "I've found an file... Let's get all information..");

                byte[] jsonData = Files.readAllBytes(file.toPath());

                ObjectMapper objectMapper = new ObjectMapper();

                JsonNode rootNode = objectMapper.readTree(jsonData);

                RegisteryAPI.sendLog(TextFormat.YELLOW, "Creating maps..");

                Map<String, String> typeDatabase = new HashMap<>();
                Map<String, String> messages = new HashMap<>();
                Map<String, Map<String, String>> databaseOptions = new HashMap<>();
                Map<String, String> options = new HashMap<>();

                RegisteryAPI.sendLog(TextFormat.YELLOW, "Input information..");

                int countDatabases = rootNode.get("countDatabases").asInt();

                Iterator<Map.Entry<String, JsonNode>> jsonNodeTypeDatabase = rootNode.get("typeDatabase").fields();
                while (jsonNodeTypeDatabase.hasNext()) {
                    Map.Entry<String, JsonNode> entry = jsonNodeTypeDatabase.next();
                    typeDatabase.put(entry.getKey(), entry.getValue().toString());
                }

                Iterator<Map.Entry<String, JsonNode>> jsonNodeMessages = rootNode.get("messages").fields();
                while (jsonNodeMessages.hasNext()) {
                    Map.Entry<String, JsonNode> entry = jsonNodeMessages.next();
                    messages.put(entry.getKey(), entry.getValue().toString());
                }

                RegisteryAPI.sendLog(TextFormat.YELLOW, "Add information to DBSettingsObject..");

                dbSettingsObject = new DBSettingsObject(countDatabases, typeDatabase, databaseOptions, messages);

                RegisteryAPI.sendLog(TextFormat.GREEN, "Done!");
            }
        } catch (IOException e) {
            e.fillInStackTrace();
        }
    }


    private String serializeData(int countDatabases, Map<String, String> typeDatabase, Map<String, Map<String, String>> databaseOptions, Map<String, String> messages) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        DBSettingsObject dbSettingsObject = new DBSettingsObject(countDatabases, typeDatabase, databaseOptions, messages);

        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(dbSettingsObject);
    }
}
