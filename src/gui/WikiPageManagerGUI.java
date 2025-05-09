package gui;

import wiki.WikiPage;
import storage.DataStorage;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class WikiPageManagerGUI extends JFrame {
    private DataStorage storage; // 添加存储字段

    public WikiPageManagerGUI(List<WikiPage> allPages, DataStorage storage) {
        this.storage = storage; // 初始化存储字段
        setTitle("Manage Wiki Pages");
        setSize(700, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Model for page titles
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (WikiPage page : allPages) {
            listModel.addElement(page.getTitle());
        }

        JList<String> pageList = new JList<>(listModel);
        JScrollPane listScroll = new JScrollPane(pageList);
        listScroll.setPreferredSize(new Dimension(200, 0));

        // Content display
        JTextArea contentArea = new JTextArea();
        contentArea.setEditable(false);
        JScrollPane contentScroll = new JScrollPane(contentArea);

        // Buttons
        JButton addBtn = new JButton("Create New Page");
        JButton editBtn = new JButton("Edit Selected");
        JButton deleteBtn = new JButton("Delete Selected");
        JButton analyticsBtn = new JButton("View Analytics"); // 🔍 NEW BUTTON

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addBtn);
        buttonPanel.add(editBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(analyticsBtn); // Add to panel

        // Add components to frame
        add(listScroll, BorderLayout.WEST);
        add(contentScroll, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Show content on selection
        pageList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedTitle = pageList.getSelectedValue();
                WikiPage selectedPage = allPages.stream()
                        .filter(p -> p.getTitle().equals(selectedTitle))
                        .findFirst().orElse(null);
                if (selectedPage != null) {
                    contentArea.setText(selectedPage.getContent());
                }
            }
        });

        // Create new page
        addBtn.addActionListener(e -> {
            new PageEditorGUI(allPages, null, storage); // 添加 storage 参数
            storage.savePages(allPages); // 保存数据
            dispose();
        });

        // Edit selected page
        editBtn.addActionListener(e -> {
            String selectedTitle = pageList.getSelectedValue();
            if (selectedTitle == null) return;

            WikiPage selectedPage = allPages.stream()
                    .filter(p -> p.getTitle().equals(selectedTitle))
                    .findFirst().orElse(null);

            if (selectedPage != null) {
                new PageEditorGUI(allPages, selectedPage, storage); // 添加 storage 参数
                storage.savePages(allPages); // 保存数据
                dispose();
            }
        });

        // Delete selected page
        deleteBtn.addActionListener(e -> {
            String selectedTitle = pageList.getSelectedValue();
            if (selectedTitle == null) return;

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Delete page \"" + selectedTitle + "\"?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                WikiPage pageToDelete = allPages.stream()
                        .filter(p -> p.getTitle().equals(selectedTitle))
                        .findFirst().orElse(null);
                if (pageToDelete != null) {
                    allPages.remove(pageToDelete);
                    listModel.removeElement(selectedTitle);
                    contentArea.setText("");
                    storage.savePages(allPages); // 保存数据
                }
            }
        });

        // 🔍 View analytics of selected page
        analyticsBtn.addActionListener(e -> {
            String selectedTitle = pageList.getSelectedValue();
            if (selectedTitle == null) return;

            WikiPage selectedPage = allPages.stream()
                    .filter(p -> p.getTitle().equals(selectedTitle))
                    .findFirst().orElse(null);

            if (selectedPage != null) {
                String stats = String.format(
                        "Title: %s\nViews: %d\nLikes: %d\nDislikes: %d",
                        selectedPage.getTitle(),
                        selectedPage.getViewCount(),
                        selectedPage.getLikeCount(),
                        selectedPage.getDislikeCount()
                );

                JOptionPane.showMessageDialog(this, stats, "Page Analytics", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
