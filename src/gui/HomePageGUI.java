package gui;

import user.Admin;
import user.Player;
import storage.DataStorage;
import wiki.WikiPage;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import javax.swing.border.EmptyBorder;

public class HomePageGUI extends JFrame {
    public HomePageGUI(Admin admin, Player player, List<WikiPage> allPages, DataStorage storage) {
        setTitle("ScrolldenWiki Home");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("ScrolldenWiki", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 28));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(30, 10, 30, 10));
        add(titleLabel, BorderLayout.NORTH);

        JButton adminButton = new JButton("Enter as Admin");
        JButton playerButton = new JButton("Enter as Player");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.add(adminButton);
        buttonPanel.add(playerButton);
        add(buttonPanel, BorderLayout.CENTER);

        adminButton.addActionListener(e -> {
            new AdminGUI(admin, allPages, storage);
            dispose(); // Close home screen
        });

        playerButton.addActionListener(e -> {
            new PlayerGUI(player, allPages, storage);
            dispose(); // Close home screen
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
