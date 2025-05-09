package user;

import wiki.WikiPage;
import review.Review;
import suggestion.Suggestion;
import suggestion.SuggestionManager;

public class Player extends User {

    public Player(String username) {
        super(username);
    }

    @Override
    public void browseWiki(WikiPage page) {
        page.display();
        page.view();
    }


    public void likePage(WikiPage page) {
        page.like();
    }

    public void dislikePage(WikiPage page) {
        page.dislike();
    }

    public void createReview(WikiPage page, String content) {
        Review review = new Review(username, content);
        page.addReview(review);
    }

    public void postSuggestion(WikiPage page, String content) {
        Suggestion s = new Suggestion(this.username, content, page);
        SuggestionManager.getInstance().addSuggestion(s);
    }

    
}