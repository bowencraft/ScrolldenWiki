package storage;

import wiki.WikiPage;

import java.io.*;
import java.util.*;

public class YamlStorage implements DataStorage {
    private static final String FILE_PATH = System.getProperty("user.dir") + "/data/wiki_pages.yml";

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
}