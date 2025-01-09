package com.unicode;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class EventHandling {
  
  public static void addCopyButtonListener(JButton button, JTextArea textArea) {
    button.addActionListener(e -> {
        String text = textArea.getText();
        try {
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(text), null);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(textArea, "Failed to copy text: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    });}
      public void addThemeButtonListener(JButton button){
      button.addActionListener(e -> {
          this.changeTheme();
              });
            }
                private void changeTheme() {
                }
}
