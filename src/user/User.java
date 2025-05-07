package user;

import wiki.WikiPage;
import wiki.Category;

public abstract class User {
    protected String username;

    public User(String username) {
        this.username = username;
    }

    public abstract void browseWiki(WikiPage page);

    public abstract void viewCategory(Category category);

    public String getUsername() {
        return username;
    }
}