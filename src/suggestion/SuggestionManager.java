package suggestion;

import java.util.*;
import wiki.WikiPage;

public class SuggestionManager {
    private static SuggestionManager instance;
    private List<Suggestion> suggestions = new ArrayList<>();

    private SuggestionManager() {}

    public static SuggestionManager getInstance() {
        if (instance == null) {
            instance = new SuggestionManager();
        }
        return instance;
    }

    public void addSuggestion(Suggestion s) {
        suggestions.add(s);
    }

    public void printAllSuggestions() {
        for (Suggestion s : suggestions) {
            System.out.println("From " + s.getSenderUsername() + ": " + s.getContent());
        }
    }

    public List<Suggestion> getSuggestionsByPage(WikiPage page) {
        List<Suggestion> result = new ArrayList<>();
        for (Suggestion s : suggestions) {
            if (s.getRelatedPage().equals(page)) {
                result.add(s);
            }
        }
        return result;
    }
}