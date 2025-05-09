package storage;

public class StorageFactory {
    public static DataStorage getStorage() {
        return new YamlStorage();
    }
}