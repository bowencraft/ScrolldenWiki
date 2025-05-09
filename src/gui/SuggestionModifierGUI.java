package gui;

import user.Player;
import suggestion.Suggestion;
import suggestion.SuggestionManager;
import storage.DataStorage;

import javax.swing.*;

public class SuggestionModifierGUI extends JFrame {
    private DataStorage storage; // 添加存储字段

    public SuggestionModifierGUI(Player player, DataStorage storage) {
        this.storage = storage; // 初始化存储字段
        setTitle("Submit Suggestion");
        setSize(400, 200);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        JTextArea suggestionText = new JTextArea(4, 30);
        suggestionText.setBorder(BorderFactory.createTitledBorder("Enter your suggestion:"));

        JButton submit = new JButton("Submit");

        submit.addActionListener(e -> {
            String content = suggestionText.getText().trim();
            if (!content.isEmpty()) {
                Suggestion s = new Suggestion(player.getUsername(), content, null);
                SuggestionManager.getInstance().addSuggestion(s);
                storage.saveSuggestions(SuggestionManager.getInstance().getAllSuggestions()); // 保存建议
                JOptionPane.showMessageDialog(this, "Suggestion submitted.");
                dispose();
            }
        });

        add(suggestionText);
        add(submit);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
