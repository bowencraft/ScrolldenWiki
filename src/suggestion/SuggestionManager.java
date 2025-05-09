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

    // Initialize suggestions from storage
    public void initializeSuggestions(List<Suggestion> loadedSuggestions) {
        if (loadedSuggestions != null) {
            suggestions.addAll(loadedSuggestions);
        }
    }

    // Add a suggestion to the global list
    public void addSuggestion(Suggestion s) {
        suggestions.add(s);
    }

    // Print all suggestions to console (CLI fallback)
    public void printAllSuggestions() {
        for (Suggestion s : suggestions) {
            System.out.println("From " + s.getSenderUsername() + ": " + s.getContent());
        }
    }

    // For Admin GUI to fetch all suggestions
    public List<Suggestion> getAllSuggestions() {
        return new ArrayList<>(suggestions);
    }

    // For viewing suggestions specific to one page
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
