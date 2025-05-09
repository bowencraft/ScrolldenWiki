package user;

import wiki.WikiPage;
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


    public List<Suggestion> getSuggestions() {
        return SuggestionManager.getInstance().getAllSuggestions();
    }

    public void viewSuggestions() {
        SuggestionManager.getInstance().printAllSuggestions();
    }
}
