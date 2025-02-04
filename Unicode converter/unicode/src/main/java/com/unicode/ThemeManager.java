package com.unicode.theme;

import java.awt.Color;
import java.awt.Component;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class ThemeManager {
  
  public static final Color LIGHT_GRAY = new Color(0xd3d3d3);
  public static final Color NERO = new Color(0x181818);
  public static final Color DARK_NERO = new Color(0x1f1f1f);
  public static final Color SILVER = new Color(0xc3c3c3);
  private static final String THEME_FILE = "theme.txt";

  public static void saveTheme(boolean isDarkTheme) {
    try {
      String theme = isDarkTheme? "dark" : "light";
      Files.writeString(Path.of(THEME_FILE), theme, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    } catch (Exception e) {
      System.err.println("Failed to save theme: " + e.getMessage());
    }
  }

  public static boolean loadTheme() {
    try {
      String theme = Files.readString(Path.of(THEME_FILE)).trim();
      return "dark".equalsIgnoreCase(theme);
    } catch (Exception e) {
      System.err.println("Failed to load theme, defaulting to light: " + e.getMessage());
      return false;
    }
  }

  public void configureTheme(List<Component> list, Color bg, Color fg, Color textAreaBg, ImageIcon icon) {
   for (Component comp : list) {
        if (comp instanceof JFrame) {
          ((JFrame)comp).getContentPane().setBackground(bg);
        } else if (comp instanceof JTextArea || comp instanceof JButton) {
          comp.setBackground(textAreaBg);
          comp.setForeground(fg);
          if ("changeThemeButton".equals(comp.getName())) {
            ((JButton)comp).setIcon(icon);
        }
        } else if (comp instanceof JLabel) {
          comp.setForeground(fg);
      }
    }
  }
}
