package gui;

import wiki.WikiPage;
import storage.DataStorage;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PageEditorGUI extends JFrame {
    private DataStorage storage; // 添加存储字段

    public PageEditorGUI(List<WikiPage> allPages, WikiPage pageToEdit, DataStorage storage) {
        this.storage = storage; // 初始化存储字段
        setTitle(pageToEdit == null ? "Create New Wiki Page" : "Edit Wiki Page");
        setSize(600, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Input fields
        JTextField titleField = new JTextField(pageToEdit != null ? pageToEdit.getTitle() : "", 25);
        JTextArea contentArea = new JTextArea(pageToEdit != null ? pageToEdit.getContent() : "", 10, 40);
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(contentArea);

        JButton saveButton = new JButton("Save Page");
        JButton cancelButton = new JButton("Cancel");

        // Layout for fields and buttons
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        inputPanel.add(new JLabel("Title:"));
        inputPanel.add(titleField);
        inputPanel.add(Box.createVerticalStrut(10));
        inputPanel.add(new JLabel("Content:"));
        inputPanel.add(scrollPane);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Button actions
        saveButton.addActionListener(e -> {
            String title = titleField.getText().trim();
            String content = contentArea.getText().trim();

            if (title.isEmpty() || content.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Title and content cannot be empty.");
                return;
            }

            if (pageToEdit != null) {
                pageToEdit.setTitle(title);
                pageToEdit.setContent(content);
                JOptionPane.showMessageDialog(this, "Page updated successfully.");
            } else {
                WikiPage newPage = new WikiPage(title, content);
                allPages.add(newPage);
                JOptionPane.showMessageDialog(this, "New page created successfully.");
            }

            storage.savePages(allPages); // 保存数据
            dispose(); // Close the editor
        });

        cancelButton.addActionListener(e -> dispose());

        setLocationRelativeTo(null); // Center the window
        setVisible(true);
    }
}
