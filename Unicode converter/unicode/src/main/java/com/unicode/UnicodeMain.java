package com.unicode;

import javax.swing.SwingUtilities;

/**
 * The UnicodeMain class serves as the entry point for the Unicode converter
 * application.
 * It initializes the GUI for the application.
 * 
 * @author Artem Sinchuk
 * @version 1.1
 */
public class UnicodeMain {
  public static void main(String[] args) {
     SwingUtilities.invokeLater(()-> new UnicodeGUI());
  }
}