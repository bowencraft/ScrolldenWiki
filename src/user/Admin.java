package user;

import wiki.WikiPage;
import wiki.Category;
import suggestion.Suggestion;
import suggestion.SuggestionManager;

import java.util.List;

public class Admin extends User {

    public Admin(String username) {
        super(username);
    }

    @Override
    public void browseWiki(WikiPage page) {
        page.display();
    }

    @Override
    public void viewCategory(Category category) {
        category.display();
    }

    // Add a new page to a category
    public void createPage(WikiPage page, Category category) {
        category.addComponent(page);
    }

    // Remove a page from a category
    public void deletePage(WikiPage page, Category category) {
        category.removeComponent(page);
    }

    // Used by GUI to get all suggestions from SuggestionManager
    public List<Suggestion> getSuggestions() {
        return SuggestionManager.getInstance().getAllSuggestions();
    }

    // Console fallback (optional, for debugging)
    public void viewSuggestions() {
        SuggestionManager.getInstance().printAllSuggestions();
    }
}
