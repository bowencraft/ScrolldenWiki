package storage;

import wiki.WikiPage;
import java.util.List;

public interface DataStorage {
    void savePages(List<WikiPage> pages);
    List<WikiPage> loadPages();
}