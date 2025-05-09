package gui;

import wiki.WikiPage;
import storage.DataStorage;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PageEditorGUI extends JFrame {
    private DataStorage storage; 

    public PageEditorGUI(List<WikiPage> allPages, WikiPage pageToEdit, DataStorage storage) {
        this.storage = storage;
        setTitle(pageToEdit == null ? "Create New Wiki Page" : "Edit Wiki Page");
        setSize(600, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JTextField titleField = new JTextField(pageToEdit != null ? pageToEdit.getTitle() : "", 25);
        JTextArea contentArea = new JTextArea(pageToEdit != null ? pageToEdit.getContent() : "", 10, 40);
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(contentArea);

        JButton saveButton = new JButton("Save Page");
        JButton cancelButton = new JButton("Cancel");

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

            storage.savePages(allPages); 
            dispose();
        });

        cancelButton.addActionListener(e -> dispose());

        setLocationRelativeTo(null); 
        setVisible(true);
    }
}
