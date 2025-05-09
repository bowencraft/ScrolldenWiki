package gui;

import user.Player;
import storage.DataStorage;
import wiki.WikiPage;

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

        browseButton.addActionListener(e -> new PageBrowserPlayerGUI(player, allPages));
        suggestButton.addActionListener(e -> new SuggestionModifierGUI(player));

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
