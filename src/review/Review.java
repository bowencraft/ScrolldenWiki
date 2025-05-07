package review;

import java.time.LocalDateTime;

public class Review {
    private String author;
    private String content;
    private LocalDateTime timestamp;

    public Review(String author, String content) {
        this.author = author;
        this.content = content;
        this.timestamp = LocalDateTime.now();
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }
}