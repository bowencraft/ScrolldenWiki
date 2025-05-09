package review;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    // Optional: formatted time string
    public String getFormattedTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return timestamp.format(formatter);
    }

    @Override
    public String toString() {
        return "[" + getFormattedTimestamp() + "] " + author + ": " + content;
    }
}
