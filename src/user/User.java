package user;

import wiki.WikiPage;

public abstract class User {
    protected String username;

    public User(String username) {
        this.username = username;
    }

    public abstract void browseWiki(WikiPage page);


    public String getUsername() {
        return username;
    }
}