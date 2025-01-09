package com.unicode;

import java.awt.Color;
import javax.swing.ImageIcon;

public class ThemeConfiguration {

  public Color bg;
  public Color fg;
  public Color textAreaBg;
  public ImageIcon icon;

  public ThemeConfiguration(Color bg, Color fg, Color textAreaBg, ImageIcon icon) {
    this.bg = bg;
    this.fg = fg;
    this.textAreaBg = textAreaBg;
    this.icon = icon;
  }
}
