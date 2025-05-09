package storage;

import wiki.WikiPage;
import suggestion.Suggestion;
import java.util.List;

public interface DataStorage {
    void savePages(List<WikiPage> pages);
    List<WikiPage> loadPages();

    void saveSuggestions(List<Suggestion> suggestions); 
    List<Suggestion> loadSuggestions(); 
}