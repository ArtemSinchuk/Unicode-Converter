package com.unicode.gui.event;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import com.unicode.core.Translate;
import com.unicode.core.UnicodeConverter;
import com.unicode.gui.UnicodeGUI;

public class EventHandling {

  public void addCopyButtonListener(JButton button, JTextArea textArea) {
    button.addActionListener(e -> {
        String text = textArea.getText();
        try {
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(text), null);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(textArea, "Failed to copy text: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    });}

  public void addThemeButtonListener(JButton button, UnicodeGUI gui){
  button.addActionListener(e -> {
      gui.changeTheme();
                });
              }

      private boolean updating = false;
    public void addDocListener(JTextArea leftTextArea, JTextArea rightTextArea, boolean translate) {
        leftTextArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateFields();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateFields();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateFields();
            }

            private void updateFields() {
                if (updating)
                    return; // If the update is already in progress, exit
                updating = true;

                String input = leftTextArea.getText();
                String translatedText = Translate.translateText(input);
                String unicodeText = UnicodeConverter.toUnicode(input);
                rightTextArea.setText(translate ? translatedText : unicodeText);

                updating = false;
            }
        });
    }
}


