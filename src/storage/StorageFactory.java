package storage;

public class StorageFactory {
    public static DataStorage getStorage() {
        return new YamlStorage(); // 直接返回 YAML 存储实现
    }
}