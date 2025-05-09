import user.*;
import wiki.*;
import storage.*;

import gui.AdminGUI;
import gui.PlayerGUI;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import suggestion.Suggestion;
import suggestion.SuggestionManager;

public class AppMain {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            
            DataStorage storage = new YamlStorage(); // 直接使用 YamlStorage

            
            List<WikiPage> allPages = storage.loadPages();
            List<Suggestion> allSuggestions = storage.loadSuggestions(); 
            if (allPages == null || allPages.isEmpty()) {
                System.out.println("No pages found. Creating default data...");

                WikiPage page1 = new WikiPage("Getting Started", "Welcome to Scrollden!");
                allPages = new ArrayList<>();
                allPages.add(page1);
            }

            SuggestionManager.getInstance().initializeSuggestions(allSuggestions);

            Admin admin = new Admin("Admin");
            Player player = new Player("Player");

            String[] options = {"Admin", "Player"};
            int choice = JOptionPane.showOptionDialog(
                null,
                "Choose user type:",
                "ScrolldenWiki",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
            );

            if (choice == 0) {
                
                new AdminGUI(admin, allPages, storage);
            } else {
                
                new PlayerGUI(player, allPages, storage);
            }

            
        });
    }
}
