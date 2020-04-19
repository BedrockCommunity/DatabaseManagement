package nycuro.databasemanagement.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import nycuro.databasemanagement.api.DBAPI;
import nycuro.databasemanagement.config.data.DBSettingsObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

/**
 * author: NycuRO
 * DatabaseManagement Project
 * API 1.0.0
 */
public class SettingsAPI {

    public DBSettingsObject dbSettingsObject;
    
    private String prefix = "&a[DatabaseManagement] ";

    public void init() {
        try {
            File file = new File(DBAPI.mainAPI.getDataFolder() + "/settings.json");

            DBAPI.sendLog(prefix + "Checking for existance of settings file... ");

            if (!file.exists()) {
                DBAPI.sendLog(prefix + "I've not found a file... Let's put all information..");

                FileWriter fileWriter = new FileWriter(file);

                DBAPI.sendLog(prefix + "Setting Prefix...");

                DBAPI.sendLog(prefix + "Setting Count of Databases..");

                int databaseCount = 2;

                DBAPI.sendLog(prefix + "Setting Type of Databases...");

                Map<String, String> databaseType = new HashMap<>();
                databaseType.put("1", "SQLITE3"); // Type of Database with Name 1
                databaseType.put("2", "MySQL"); // Type of Database with Name 2

                DBAPI.sendLog(prefix + "Setting Plugin Messages...");

                Map<String, String> pluginMessages = new HashMap<>();
                pluginMessages.put("nycuro.prefix", prefix); // Message Data and actual string of message

                DBAPI.sendLog(prefix + "Writing data...");

                fileWriter.write(serializeData(databaseCount, databaseType, pluginMessages));
                fileWriter.close();

                DBAPI.sendLog(prefix + "Finished!");
            } else {
                DBAPI.sendLog(prefix + "I've found an file... Let's get all information..");

                byte[] jsonData = Files.readAllBytes(file.toPath());

                ObjectMapper objectMapper = new ObjectMapper();

                JsonNode rootNode = objectMapper.readTree(jsonData);

                DBAPI.sendLog(prefix + "Creating maps..");

                Map<String, String> typeDatabase = new HashMap<>();
                Map<String, String> messages = new HashMap<>();

                DBAPI.sendLog(prefix + "Input information..");

                int countDatabases = rootNode.get("countDatabases").asInt();

                Iterator<Map.Entry<String, JsonNode>> jsonNodeTypeDatabase = rootNode.get("typeDatabase").fields();
                while (jsonNodeTypeDatabase.hasNext()) {
                    Map.Entry<String, JsonNode> entry = jsonNodeTypeDatabase.next();
                    DBAPI.sendLog(entry.getKey());
                    DBAPI.sendLog(entry.getValue().asText());
                }

                DBAPI.sendLog(prefix + "Input information from Abuse Blocks to Map..");

                Iterator<Map.Entry<String, JsonNode>> jsonNodeMessages = rootNode.get("messages").fields();
                while (jsonNodeMessages.hasNext()) {
                    Map.Entry<String, JsonNode> entry = jsonNodeMessages.next();
                    DBAPI.sendLog(entry.getKey());
                    DBAPI.sendLog(entry.getValue().asText());
                }

                DBAPI.sendLog(prefix + "Add information to DBSettingsObject..");

                dbSettingsObject = new DBSettingsObject(countDatabases, typeDatabase, messages);

                DBAPI.sendLog(prefix + "Done!");

                dbSettingsObject.getMessages().forEach( (d, a) -> {
                    DBAPI.sendLog(d + a + "\n");
                });
            }
        } catch (IOException e) {
            e.fillInStackTrace();
        }
    }


    private String serializeData(int countDatabases, Map<String, String> typeDatabase, Map<String, String> messages) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        DBSettingsObject dbSettingsObject = new DBSettingsObject(countDatabases, typeDatabase, messages);

        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(dbSettingsObject);
    }
}
