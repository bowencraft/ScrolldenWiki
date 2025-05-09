package gui;

import user.Player;
import suggestion.Suggestion;
import suggestion.SuggestionManager;

import javax.swing.*;

public class SuggestionModifierGUI extends JFrame {
    public SuggestionModifierGUI (Player player) {
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
