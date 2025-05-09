package gui;

import wiki.WikiPage;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AnalyticsViewerGUI extends JFrame {
    public AnalyticsViewerGUI(List<WikiPage> pages) {
        setTitle("Wiki Page Analytics");
        setSize(600, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JTextArea displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        for (WikiPage p : pages) {
            displayArea.append(
                String.format("Title: %-20s | Views: %4d | Likes: %3d | Dislikes: %3d%n",
                        p.getTitle(), p.getViewCount(), p.getLikeCount(), p.getDislikeCount())
            );
        }

        JScrollPane scrollPane = new JScrollPane(displayArea);
        add(scrollPane, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
