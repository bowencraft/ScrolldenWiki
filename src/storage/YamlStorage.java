package storage;

import wiki.WikiPage;
import suggestion.Suggestion;

import java.io.*;
import java.util.*;

public class YamlStorage implements DataStorage {
    private static final String FILE_PATH = System.getProperty("user.dir") + "/data/wiki_pages.yml";
    private static final String SUGGESTIONS_FILE_PATH = System.getProperty("user.dir") + "/data/suggestions.yml";

    @Override
    public void savePages(List<WikiPage> pages) {
        File file = new File(FILE_PATH);
        try {
            // 确保父目录存在
            if (!file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
                System.err.println("Failed to create directory: " + file.getParentFile().getAbsolutePath());
                return;
            }
            // 写入文件
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                for (WikiPage page : pages) {
                    writer.write("- title: " + page.getTitle() + "\n");
                    writer.write("  content: " + page.getContent() + "\n");
                    writer.write("  views: " + page.getViewCount() + "\n");
                    writer.write("  likes: " + page.getLikeCount() + "\n");
                    writer.write("  dislikes: " + page.getDislikeCount() + "\n");
                    writer.write("---\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<WikiPage> loadPages() {
        File file = new File(FILE_PATH);
        List<WikiPage> pages = new ArrayList<>();
        if (!file.exists()) {
            // 如果文件不存在，返回空列表
            return pages;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            WikiPage page = null;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("- title: ")) {
                    if (page != null) {
                        pages.add(page);
                    }
                    page = new WikiPage(line.substring(9), "");
                } else if (line.startsWith("  content: ")) {
                    if (page != null) {
                        page.setContent(line.substring(11));
                    }
                } else if (line.startsWith("  views: ")) {
                    if (page != null) {
                        page.setViews(Integer.parseInt(line.substring(9)));
                    }
                } else if (line.startsWith("  likes: ")) {
                    if (page != null) {
                        page.setLikes(Integer.parseInt(line.substring(9)));
                    }
                } else if (line.startsWith("  dislikes: ")) {
                    if (page != null) {
                        page.setDislikes(Integer.parseInt(line.substring(12)));
                    }
                }
            }
            if (page != null) {
                pages.add(page);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pages;
    }

    @Override
    public List<Suggestion> loadSuggestions() {
        File file = new File(SUGGESTIONS_FILE_PATH);
        List<Suggestion> suggestions = new ArrayList<>();
        if (!file.exists()) {
            return suggestions;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            Suggestion suggestion = null;
            String sender = null, content = null;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("- sender: ")) {
                    sender = line.substring(10);
                } else if (line.startsWith("  content: ")) {
                    content = line.substring(11);
                } else if (line.equals("---")) {
                    if (sender != null && content != null) {
                        suggestions.add(new Suggestion(sender, content, null));
                    }
                    sender = null;
                    content = null;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return suggestions;
    }

    @Override
    public void saveSuggestions(List<Suggestion> suggestions) {
        File file = new File(SUGGESTIONS_FILE_PATH);
        try {
            if (!file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
                System.err.println("Failed to create directory: " + file.getParentFile().getAbsolutePath());
                return;
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                for (Suggestion suggestion : suggestions) {
                    writer.write("- sender: " + suggestion.getSenderUsername() + "\n");
                    writer.write("  content: " + suggestion.getContent() + "\n");
                    writer.write("---\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}