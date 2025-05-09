package wiki;

import java.util.*;

import review.Review;

public class WikiPage implements WikiComponent {
    private String title;
    private String content;
    private int likes = 0;
    private int dislikes = 0;
    private int views = 0;
    private List<Review> reviews = new ArrayList<>();


    public WikiPage(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void display() {
        System.out.println("Title: " + title);
        System.out.println("Content: " + content);
        System.out.println("Views: " + views + ", Likes: " + likes + ", Dislikes: " + dislikes);
        for (Review r : reviews) {
            System.out.println("Review by " + r.getAuthor() + ": " + r.getContent());
        }
    }

    public void like() {
        likes++;
    }

    public void view() {
        views++;
    }

    public void dislike() {
        dislikes++;
    }

    public void addReview(Review r) {
        reviews.add(r);
    }

    // ✅ Expose the list of reviews
    public List<Review> getReviews() {
        return reviews;
    }

    public int getViewCount() {
        return views;
    }

    public int getLikeCount() {
        return likes;
    }

    public int getDislikeCount() {
        return dislikes;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
