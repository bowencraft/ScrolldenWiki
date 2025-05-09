package storage;

import wiki.WikiPage;
import suggestion.Suggestion;
import java.util.List;

public interface DataStorage {
    void savePages(List<WikiPage> pages);
    List<WikiPage> loadPages();

    void saveSuggestions(List<Suggestion> suggestions); // 新增方法
    List<Suggestion> loadSuggestions(); // 新增方法
}