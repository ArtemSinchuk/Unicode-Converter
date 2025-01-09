package com.unicode;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


public class UnicodeGUI extends JFrame {
    private JTextArea InputTextArea, translatedTextArea, unicodeTextArea;
    private JButton copyTranslationButton, copyUnicodeButton, changeThemeButton = new JButton();
    private JLabel englishTextLabel, rusTextLabel, unicodeCodeLabel;
    private ImageIcon themeMoonIcon = new ImageIcon("C:\\Users\\GameTwo\\Desktop\\Projects\\programs\\Unicode coder\\unicode\\src\\main\\resources\\moon.png");
    private ImageIcon themeSunIcon = new ImageIcon("C:\\Users\\GameTwo\\Desktop\\Projects\\programs\\Unicode coder\\unicode\\src\\main\\resources\\sun.png");
    private ImageIcon scaledMoonImage = new ImageIcon(themeMoonIcon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));
    private ImageIcon scaledSunImage = new ImageIcon(themeSunIcon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));

    public UnicodeGUI() {
        initialization();
        config();
        addDocListeners();
        addKeyListenerToWindow();

        isDarkTheme = ThemeManager.loadTheme();
        if (isDarkTheme) {
            setDarkTheme();
        } else {
            setLightTheme();
        }
    }

    public void configureFrame() {
        setTitle("Unicode Converter");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(575, 325);
        getContentPane().setBackground(ThemeManager.LIGHT_GRAY);
        setLocationRelativeTo(null);
        setLayout(null);
        addComponents();
        setVisible(true);
    }

    private void addComponents() {
        add(englishTextLabel);
        add(rusTextLabel);
        add(unicodeCodeLabel);
        add(InputTextArea);
        add(translatedTextArea);
        add(unicodeTextArea);
        add(copyTranslationButton);
        add(copyUnicodeButton);
        add(changeThemeButton);
    }

    public void configureButtons() {
        configureButtons(copyTranslationButton, 200, 225, 150, 30);
        configureButtons(copyUnicodeButton, 400, 225, 150, 30);
        configureButtons(changeThemeButton, 10, 10, 25, 25);
        changeThemeButton.setIcon(scaledMoonImage);
        changeThemeButton.setOpaque(false);
        changeThemeButton.setContentAreaFilled(false);
        
        EventHandling.addCopyButtonListener(copyTranslationButton, unicodeTextArea);
        EventHandling.addCopyButtonListener(copyUnicodeButton, translatedTextArea);
    }

    public void configureLabels() {
        englishTextLabel.setBounds(10, 50, 150, 30);
        rusTextLabel.setBounds(200, 50, 150, 30);
        unicodeCodeLabel.setBounds(400, 50, 150, 30);
    }
    public void imageConfig() {
        
    }

    public void configureTextArea() {
        configureTextArea(InputTextArea, 10, 100, 150, 100);
        configureTextArea(translatedTextArea, 200, 100, 150, 100);
        configureTextArea(unicodeTextArea, 400, 100, 150, 100);
        unicodeTextArea.setEditable(false);
    }

    // TODO: Get rid of code duplication
    public void configureButtons(JButton btn, int x, int y, int width, int height) {
        btn.setBounds(x, y, width, height);
        btn.setBorderPainted(false);
        btn.setBackground(Color.WHITE);
    }
    public void configureTextArea(JTextArea area, int x, int y, int width, int height) {
        area.setBounds(x, y, width, height);
        area.setFont(new Font("Arial", Font.PLAIN, 12));
        area.setLineWrap(true);
    }
    //TODO: Separate GUI logic from event handling

    private void config() {
        configureButtons();
        configureTextArea();
        configureFrame();
        configureLabels();
        imageConfig();
    }

    private void initialization() {
        InputTextArea = new JTextArea(5, 30);
        translatedTextArea = new JTextArea(5, 30);
        unicodeTextArea = new JTextArea(5, 30);
        copyTranslationButton = new JButton("Select and Copy");
        copyUnicodeButton = new JButton("Select and Copy");
        changeThemeButton = new JButton();
        englishTextLabel = new JLabel("Enter Text");
        rusTextLabel  = new JLabel("Translate");
        unicodeCodeLabel  = new JLabel("Unicode");
    }

    public void initializeLabels(JLabel lbl, String text) {
        lbl.setText(text);
    }

    private boolean updating = false;
    private void addDocListeners() {
        InputTextArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateFieldsFromLeft();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateFieldsFromLeft();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateFieldsFromLeft();
            }

            private void updateFieldsFromLeft() {
                if (updating)
                    return; // If the update is already in progress, exit
                updating = true;

                String input = InputTextArea.getText();
                translatedTextArea.setText(Translate.translateText(input));
                unicodeTextArea.setText(UnicodeConverter.toUnicode(input));

                updating = false;
            }
        });

        translatedTextArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateFieldsFromMiddle();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateFieldsFromMiddle();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateFieldsFromMiddle();
            }

            private void updateFieldsFromMiddle() {
                if (updating)
                    return;
                updating = true;

                String input = translatedTextArea.getText();
                unicodeTextArea.setText(UnicodeConverter.toUnicode(input));

                updating = false;
            }
        });
    }

    private void addKeyListenerToWindow() {
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_F10) {
                    UnicodeMain.main(null);
                }
            }
        });
        this.setFocusable(true);
    }
    //TODO: Put theme management in a separate class
    private boolean isDarkTheme;
    public void changeTheme() {
        if (!isDarkTheme) {
            setDarkTheme();
        } else {
            setLightTheme();
        }
        isDarkTheme = !isDarkTheme;
        ThemeManager.saveTheme(isDarkTheme);
    }

    public void setDarkTheme() {
        configureTheme(ThemeManager.NERO, ThemeManager.SILVER, ThemeManager.DARK_NERO, scaledSunImage);
    }

    public void setLightTheme() {
        configureTheme(ThemeManager.LIGHT_GRAY, Color.BLACK, Color.WHITE, scaledMoonImage);
    }
    public void configureTheme(Color bg, Color fg, Color textAreaBg, ImageIcon icon) {
        getContentPane().setBackground(bg);
        copyUnicodeButton.setBackground(textAreaBg);
        copyUnicodeButton.setForeground(fg);
        copyTranslationButton.setBackground(textAreaBg);
        copyTranslationButton.setForeground(fg);
        InputTextArea.setBackground(textAreaBg);
        InputTextArea.setForeground(fg);
        translatedTextArea.setBackground(textAreaBg);
        translatedTextArea.setForeground(fg);
        unicodeTextArea.setBackground(textAreaBg);
        unicodeTextArea.setForeground(fg);
        englishTextLabel.setForeground(fg);
        rusTextLabel.setForeground(fg);
        unicodeCodeLabel.setForeground(fg);
        changeThemeButton.setIcon(icon);
    }
}