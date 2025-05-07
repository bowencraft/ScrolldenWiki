package feedback;

import wiki.WikiPage;

public interface FeedbackObserver {
    void onFeedbackReceived(WikiPage page);
}