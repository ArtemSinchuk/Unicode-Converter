package com.ascii;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class AsciiGUI extends JFrame {
    private JTextArea InputTextArea, translatedTextArea, asciiTextArea;
    private JButton copyTranslationButton, copyAsciiButton, changeThemeButton = new JButton();
    private JLabel englishTextLabel, rusTextLabel, asciiCodeLabel;
    private ImageIcon themeMoonIcon = new ImageIcon("C:\\Users\\GameTwo\\Desktop\\Projects\\programs\\Ascii coder\\ascii\\src\\main\\resources\\moon.png");
    private ImageIcon themeSunIcon = new ImageIcon("C:\\Users\\GameTwo\\Desktop\\Projects\\programs\\Ascii coder\\ascii\\src\\main\\resources\\sun.png");
    private ImageIcon scaledMoonImage = new ImageIcon(themeMoonIcon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));
    private ImageIcon scaledSunImage = new ImageIcon(themeSunIcon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));

    public AsciiGUI() {
        initialization();
        config();
        addCopyButtonListeners();
        addDocListeners();
        addKeyListenerToWindow();
    }

    public void configureFrame() {
        setTitle("ASCII Converter");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(575, 325);
        getContentPane().setBackground(new Color(0xd3d3d3));
        setLocationRelativeTo(null);
        setLayout(null);
        addComponents();
        setVisible(true);
    }

    private void addComponents() {
        add(englishTextLabel);
        add(rusTextLabel);
        add(asciiCodeLabel);
        add(InputTextArea);
        add(translatedTextArea);
        add(asciiTextArea);
        add(copyTranslationButton);
        add(copyAsciiButton);
        add(changeThemeButton);
    }

    public void configureButtons() {
        configureButtons(copyTranslationButton, 200, 225, 150, 30);
        configureButtons(copyAsciiButton, 400, 225, 150, 30);
        configureButtons(changeThemeButton, 10, 10, 25, 25);
        changeThemeButton.setIcon(scaledMoonImage);
        changeThemeButton.setOpaque(false);
        changeThemeButton.setContentAreaFilled(false);
    }

    public void configureLabels() {
        englishTextLabel.setBounds(10, 50, 150, 30);
        rusTextLabel.setBounds(200, 50, 150, 30);
        asciiCodeLabel.setBounds(400, 50, 150, 30);
    }
    public void imageConfig() {
        
    }

    public void configureTextArea() {
        configureTextArea(InputTextArea, 10, 100, 150, 100);
        configureTextArea(translatedTextArea, 200, 100, 150, 100);
        configureTextArea(asciiTextArea, 400, 100, 150, 100);
        asciiTextArea.setEditable(false);
    }

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

    public void addCopyButtonListeners() {
        copyTranslationButton.addActionListener(e -> {
            String text = translatedTextArea.getText();
            try {
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(text), null);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Failed to copy text: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        copyAsciiButton.addActionListener(e -> {
            String text = asciiTextArea.getText();
            try {
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(text), null);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Failed to copy text: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        changeThemeButton.addActionListener(e -> {
            changeTheme();
        });
    }

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
        asciiTextArea = new JTextArea(5, 30);
        copyTranslationButton = new JButton("Select and Copy");
        copyAsciiButton = new JButton("Select and Copy");
        changeThemeButton = new JButton();
        englishTextLabel = new JLabel("Enter Text");
        rusTextLabel  = new JLabel("Translate");
        asciiCodeLabel  = new JLabel("ASCII Code");
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
                asciiTextArea.setText(AsciiConverter.toAscii(input));

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
                asciiTextArea.setText(AsciiConverter.toAscii(input));

                updating = false;
            }
        });
    }

    private void addKeyListenerToWindow() {
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_F10) {
                    AsciiMain.main(null);
                }
            }
        });
        this.setFocusable(true);
    }

    private boolean isDarkTheme;
    public void changeTheme() {
        if (!isDarkTheme) {
            setDarkTheme();
        } else {
            setLightTheme();
        }
        isDarkTheme = !isDarkTheme;
    }

    public void setDarkTheme() {
        configureTheme(new Color(0x181818), new Color(0xc3c3c3), new Color(0x1f1f1f), scaledSunImage);
    }

    public void setLightTheme() {
        configureTheme(new Color(0xd3d3d3), Color.BLACK, Color.WHITE, scaledMoonImage);
    }
    public void configureTheme(Color bg, Color fg, Color textAreaBg, ImageIcon icon) {
        getContentPane().setBackground(bg);
        copyAsciiButton.setBackground(textAreaBg);
        copyAsciiButton.setForeground(fg);
        copyTranslationButton.setBackground(textAreaBg);
        copyTranslationButton.setForeground(fg);
        InputTextArea.setBackground(textAreaBg);
        InputTextArea.setForeground(fg);
        translatedTextArea.setBackground(textAreaBg);
        translatedTextArea.setForeground(fg);
        asciiTextArea.setBackground(textAreaBg);
        asciiTextArea.setForeground(fg);
        englishTextLabel.setForeground(fg);
        rusTextLabel.setForeground(fg);
        asciiCodeLabel.setForeground(fg);
        changeThemeButton.setIcon(icon);
    }
}
