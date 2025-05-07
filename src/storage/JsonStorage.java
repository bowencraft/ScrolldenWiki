package storage;

import wiki.WikiPage;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import feedback.FeedbackObserver;
import feedback.AnalyticsSystem;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class JsonStorage implements DataStorage {
    private static final String CONFIG_FILE = "config.properties";
    private final String filePath;
    private final Gson gson;

    public JsonStorage() {
        this.gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .registerTypeAdapter(FeedbackObserver.class, new FeedbackObserverCreator())
            .create();
        this.filePath = loadFilePathFromConfig();
    }

    private String loadFilePathFromConfig() {
        Properties properties = new Properties();
        try (InputStream input = new FileInputStream(CONFIG_FILE)) {
            properties.load(input);
            return properties.getProperty("FILE_PATH", "data/wiki_pages.json");
        } catch (IOException e) {
            e.printStackTrace();
            return "data/wiki_pages.json"; // 默认路径
        }
    }

    @Override
    public void savePages(List<WikiPage> pages) {
        try {
            File file = new File(filePath);
            file.getParentFile().mkdirs(); // 确保目录存在

            try (Writer writer = new FileWriter(file)) {
                gson.toJson(pages, writer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<WikiPage> loadPages() {
        File file = new File(filePath);

        if (!file.exists()) {
            System.out.println("JSON file not found. Creating a new one...");
            savePages(new ArrayList<>());  // 创建空文件
            return new ArrayList<>();
        }

        try (Reader reader = new FileReader(file)) {
            Type pageListType = new TypeToken<List<WikiPage>>() {}.getType();
            List<WikiPage> pages = gson.fromJson(reader, pageListType);
            return (pages != null) ? pages : new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private static class FeedbackObserverCreator implements InstanceCreator<FeedbackObserver> {
        @Override
        public FeedbackObserver createInstance(Type type) {
            return new AnalyticsSystem(); // 使用 AnalyticsSystem 作为默认实现
        }
    }
}