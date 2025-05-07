package suggestion;

import java.time.LocalDateTime;
import wiki.WikiPage;

public class Suggestion {
    private String senderUsername;
    private String content;
    private WikiPage relatedPage;
    private LocalDateTime time;

    public Suggestion(String sender, String content, WikiPage page) {
        this.senderUsername = sender;
        this.content = content;
        this.relatedPage = page;
        this.time = LocalDateTime.now();
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public String getContent() {
        return content;
    }

    public WikiPage getRelatedPage() {
        return relatedPage;
    }
}