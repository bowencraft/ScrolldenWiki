package user;

import wiki.WikiPage;
import wiki.Category;
import suggestion.SuggestionManager;

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

    public void createPage(WikiPage page, Category category) {
        category.addComponent(page);
    }

    public void deletePage(WikiPage page, Category category) {
        category.removeComponent(page);
    }

    public void viewSuggestions() {
        SuggestionManager.getInstance().printAllSuggestions();
    }
}