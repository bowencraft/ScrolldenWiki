package gui;

import suggestion.Suggestion;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SuggestionViewerGUI extends JFrame {
    public SuggestionViewerGUI(List<Suggestion> suggestions) {
        setTitle("Submitted Suggestions");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        DefaultListModel<String> model = new DefaultListModel<>();
        for (Suggestion s : suggestions) {
            model.addElement("From: " + s.getSenderUsername() + " → " + s.getContent());
        }

        JList<String> suggestionList = new JList<>(model);
        suggestionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        suggestionList.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(suggestionList);

        add(scrollPane, BorderLayout.CENTER);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
