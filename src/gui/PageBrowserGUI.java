package gui;

import wiki.WikiPage;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PageBrowserGUI extends JFrame {
    public PageBrowserGUI(List<WikiPage> pages) {
        setTitle("Browse Wiki Pages");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // List model with page titles
        DefaultListModel<String> model = new DefaultListModel<>();
        for (WikiPage p : pages) {
            model.addElement(p.getTitle());
        }

        // List and content area
        JList<String> list = new JList<>(model);
        JTextArea contentArea = new JTextArea();
        contentArea.setEditable(false);
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        JScrollPane contentScroll = new JScrollPane(contentArea);

        // When a page is selected from the list
        list.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selected = list.getSelectedValue();
                WikiPage selectedPage = pages.stream()
                        .filter(p -> p.getTitle().equals(selected))
                        .findFirst().orElse(null);
                if (selectedPage != null) {
                    contentArea.setText(selectedPage.getContent());
                    selectedPage.view(); // Increment view count
                }
            }
        });

        add(new JScrollPane(list), BorderLayout.WEST);
        add(contentScroll, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
