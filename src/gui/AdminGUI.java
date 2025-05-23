package gui;

import user.Admin;
import wiki.WikiPage;
import storage.DataStorage;
import suggestion.Suggestion;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AdminGUI extends JFrame {
    private Admin admin;
    private List<WikiPage> allPages;
    private DataStorage storage;

    public AdminGUI(Admin admin, List<WikiPage> allPages, DataStorage storage) {
        this.admin = admin;
        this.allPages = allPages;
        this.storage = storage;

        setTitle("ScrolldenWiki - Admin Dashboard");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JButton manageWikiButton = new JButton("Manage Wiki Pages");
        JButton viewSuggestionsButton = new JButton("View Suggestions");

        buttonPanel.add(manageWikiButton);
        buttonPanel.add(viewSuggestionsButton);
        add(buttonPanel, BorderLayout.NORTH);

        manageWikiButton.addActionListener(e -> {
            new WikiPageManagerGUI(allPages, storage); 
            storage.savePages(allPages); 
        });

        viewSuggestionsButton.addActionListener(e -> {
            List<Suggestion> suggestions = admin.getSuggestions();
            new SuggestionViewerGUI(suggestions);
            storage.savePages(allPages); 
        });

        setLocationRelativeTo(null); 
        setVisible(true);
    }
}
