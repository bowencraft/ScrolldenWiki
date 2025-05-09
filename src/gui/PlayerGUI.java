package gui;

import user.Player;
import storage.DataStorage;
import wiki.WikiPage;
import review.Review;
import suggestion.Suggestion;
import suggestion.SuggestionManager;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PlayerGUI extends JFrame {
    private Player player;
    private List<WikiPage> allPages;
    private DataStorage storage;

    public PlayerGUI(Player player, List<WikiPage> allPages, DataStorage storage) {
        this.player = player;
        this.allPages = allPages;
        this.storage = storage;

        setTitle("ScrolldenWiki - Player Dashboard");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JButton browseButton = new JButton("Browse Pages");
        JButton suggestButton = new JButton("Submit Suggestion");

        buttonPanel.add(browseButton);
        buttonPanel.add(suggestButton);
        add(buttonPanel, BorderLayout.NORTH);

        browseButton.addActionListener(e -> openInteractiveBrowser());

        suggestButton.addActionListener(e -> {
            String suggestionText = JOptionPane.showInputDialog(this, "Enter your suggestion (general):");
            if (suggestionText != null && !suggestionText.trim().isEmpty()) {
                Suggestion suggestion = new Suggestion(player.getUsername(), suggestionText, null);
                SuggestionManager.getInstance().addSuggestion(suggestion);
                JOptionPane.showMessageDialog(this, "Suggestion submitted!");
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void openInteractiveBrowser() {
        if (allPages.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No pages available.");
            return;
        }

        JFrame browserFrame = new JFrame("Browse Wiki Pages");
        browserFrame.setSize(800, 600);
        browserFrame.setLayout(new BorderLayout());

        DefaultListModel<String> model = new DefaultListModel<>();
        for (WikiPage page : allPages) {
            model.addElement(page.getTitle());
        }

        JList<String> list = new JList<>(model);
        JScrollPane listScroll = new JScrollPane(list);
        listScroll.setPreferredSize(new Dimension(200, 0));

        JTextArea contentArea = new JTextArea();
        contentArea.setEditable(false);
        JScrollPane contentScroll = new JScrollPane(contentArea);

        JTextArea reviewArea = new JTextArea(5, 40);
        reviewArea.setEditable(false);
        reviewArea.setLineWrap(true);
        reviewArea.setWrapStyleWord(true);
        reviewArea.setBorder(BorderFactory.createTitledBorder("User Comments"));
        JScrollPane reviewScroll = new JScrollPane(reviewArea);

        JTextArea commentArea = new JTextArea(3, 40);
        JScrollPane commentScroll = new JScrollPane(commentArea);
        commentArea.setLineWrap(true);
        commentArea.setBorder(BorderFactory.createTitledBorder("Leave a comment"));

        JButton likeButton = new JButton("👍 Like");
        JButton dislikeButton = new JButton("👎 Dislike");
        JButton postCommentButton = new JButton("Post Comment");
        JButton deleteCommentButton = new JButton("Delete My Comment"); // NEW

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(likeButton);
        bottomPanel.add(dislikeButton);
        bottomPanel.add(postCommentButton);
        bottomPanel.add(deleteCommentButton); // NEW

        list.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selected = list.getSelectedValue();
                WikiPage selectedPage = getPageByTitle(selected);
                if (selectedPage != null) {
                    selectedPage.view();
                    refreshPageDisplay(selectedPage, contentArea, reviewArea);
                }
            }
        });

        likeButton.addActionListener(e -> {
            WikiPage page = getPageByTitle(list.getSelectedValue());
            if (page != null) {
                page.like();
                refreshPageDisplay(page, contentArea, reviewArea);
            }
        });

        dislikeButton.addActionListener(e -> {
            WikiPage page = getPageByTitle(list.getSelectedValue());
            if (page != null) {
                page.dislike();
                refreshPageDisplay(page, contentArea, reviewArea);
            }
        });

        postCommentButton.addActionListener(e -> {
            WikiPage page = getPageByTitle(list.getSelectedValue());
            String commentText = commentArea.getText().trim();

            if (page != null && !commentText.isEmpty()) {
                Review review = new Review(player.getUsername(), commentText);
                page.addReview(review);
                commentArea.setText("");
                JOptionPane.showMessageDialog(browserFrame, "Comment posted.");
                refreshPageDisplay(page, contentArea, reviewArea);
            }
        });

        deleteCommentButton.addActionListener(e -> {
            WikiPage page = getPageByTitle(list.getSelectedValue());
            if (page != null) {
                boolean removed = page.getReviews().removeIf(r -> r.getAuthor().equals(player.getUsername()));
                if (removed) {
                    JOptionPane.showMessageDialog(browserFrame, "Your comment was deleted.");
                } else {
                    JOptionPane.showMessageDialog(browserFrame, "You have no comment to delete.");
                }
                refreshPageDisplay(page, contentArea, reviewArea);
            }
        });

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.add(contentScroll);
        rightPanel.add(reviewScroll);
        rightPanel.add(commentScroll);

        browserFrame.add(listScroll, BorderLayout.WEST);
        browserFrame.add(rightPanel, BorderLayout.CENTER);
        browserFrame.add(bottomPanel, BorderLayout.SOUTH);

        browserFrame.setLocationRelativeTo(null);
        browserFrame.setVisible(true);
    }

    private void refreshPageDisplay(WikiPage page, JTextArea contentArea, JTextArea reviewArea) {
        contentArea.setText(
            page.getContent() + "\n\n---\nViews: " + page.getViewCount()
                    + " | Likes: " + page.getLikeCount()
                    + " | Dislikes: " + page.getDislikeCount()
        );

        StringBuilder reviewText = new StringBuilder();
        for (Review r : page.getReviews()) {
            reviewText.append(r.toString()).append("\n");
        }
        reviewArea.setText(reviewText.toString());
    }

    private WikiPage getPageByTitle(String title) {
        return allPages.stream()
                .filter(p -> p.getTitle().equals(title))
                .findFirst()
                .orElse(null);
    }
}
