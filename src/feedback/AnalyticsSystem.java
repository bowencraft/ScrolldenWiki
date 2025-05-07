package feedback;

import wiki.WikiPage;

public class AnalyticsSystem implements FeedbackObserver {
    @Override
    public void onFeedbackReceived(WikiPage page) {
        // System.out.println("Analytics updated: " + page.getViewCount() + " views, " +
        //         page.getLikeCount() + " likes, " + page.getDislikeCount() + " dislikes.");
    }
}