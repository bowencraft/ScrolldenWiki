package gui;

import user.Player;
import review.Review;
import wiki.WikiPage;
import storage.DataStorage;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PageBrowserPlayerGUI extends JFrame {
    private DataStorage storage; // 添加存储字段

    public PageBrowserPlayerGUI(Player player, List<WikiPage> pages, DataStorage storage) {
        this.storage = storage; // 初始化存储字段
        setTitle("Browse Wiki Pages");
        setSize(800, 600);
        setLayout(new BorderLayout());

        DefaultListModel<String> model = new DefaultListModel<>();
        for (WikiPage p : pages) {
            model.addElement(p.getTitle());
        }

        JList<String> list = new JList<>(model);
        JScrollPane listScroll = new JScrollPane(list);
        listScroll.setPreferredSize(new Dimension(200, 0));

        JTextArea contentArea = new JTextArea();
        contentArea.setEditable(false);
        JScrollPane contentScroll = new JScrollPane(contentArea);

        JTextArea reviewArea = new JTextArea(5, 40);
        reviewArea.setEditable(false);
        reviewArea.setBorder(BorderFactory.createTitledBorder("User Comments"));

        JTextArea commentArea = new JTextArea(3, 40);
        JScrollPane commentScroll = new JScrollPane(commentArea);
        commentArea.setBorder(BorderFactory.createTitledBorder("Leave a comment"));

        JButton likeButton = new JButton("👍 Like");
        JButton dislikeButton = new JButton("👎 Dislike");
        JButton postCommentButton = new JButton("Post Comment");
        JButton deleteCommentButton = new JButton("Delete My Comment");

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(likeButton);
        bottomPanel.add(dislikeButton);
        bottomPanel.add(postCommentButton);
        bottomPanel.add(deleteCommentButton);

        list.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                WikiPage selected = getPageByTitle(pages, list.getSelectedValue());
                if (selected != null) {
                    selected.view();
                    refreshDisplay(selected, contentArea, reviewArea);
                }
            }
        });

        likeButton.addActionListener(e -> {
            WikiPage page = getPageByTitle(pages, list.getSelectedValue());
            if (page != null) {
                page.like();
                refreshDisplay(page, contentArea, reviewArea);
                storage.savePages(pages); // 保存数据
            }
        });

        dislikeButton.addActionListener(e -> {
            WikiPage page = getPageByTitle(pages, list.getSelectedValue());
            if (page != null) {
                page.dislike();
                refreshDisplay(page, contentArea, reviewArea);
                storage.savePages(pages); // 保存数据
            }
        });

        postCommentButton.addActionListener(e -> {
            WikiPage page = getPageByTitle(pages, list.getSelectedValue());
            String text = commentArea.getText().trim();
            if (page != null && !text.isEmpty()) {
                page.addReview(new Review(player.getUsername(), text));
                commentArea.setText("");
                refreshDisplay(page, contentArea, reviewArea);
                storage.savePages(pages); // 保存数据
            }
        });

        deleteCommentButton.addActionListener(e -> {
            WikiPage page = getPageByTitle(pages, list.getSelectedValue());
            if (page != null) {
                boolean removed = page.getReviews().removeIf(r -> r.getAuthor().equals(player.getUsername()));
                JOptionPane.showMessageDialog(this, removed ? "Comment deleted." : "No comment to delete.");
                refreshDisplay(page, contentArea, reviewArea);
                storage.savePages(pages); // 保存数据
            }
        });

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.add(contentScroll);
        rightPanel.add(new JScrollPane(reviewArea));
        rightPanel.add(commentScroll);

        add(listScroll, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private WikiPage getPageByTitle(List<WikiPage> list, String title) {
        return list.stream().filter(p -> p.getTitle().equals(title)).findFirst().orElse(null);
    }

    private void refreshDisplay(WikiPage page, JTextArea content, JTextArea reviews) {
        content.setText(page.getContent() + "\n\n---\nViews: " + page.getViewCount()
                + " | Likes: " + page.getLikeCount()
                + " | Dislikes: " + page.getDislikeCount());
        StringBuilder sb = new StringBuilder();
        for (Review r : page.getReviews()) sb.append(r).append("\n");
        reviews.setText(sb.toString());
    }
}
