package storage;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class StorageFactory {
    public static DataStorage getStorage() {
        try {
            Properties prop = new Properties();
            prop.load(new FileInputStream("config.properties"));
            String type = prop.getProperty("storage");

            if (type.equalsIgnoreCase("MYSQL")) {
                return new MySQLStorage();
            } else {
                return new JsonStorage();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new JsonStorage(); // fallback
        }
    }
}