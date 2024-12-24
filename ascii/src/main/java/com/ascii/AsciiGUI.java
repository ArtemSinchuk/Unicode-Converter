package com.ascii;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;

public class AsciiGUI extends JFrame {
    private JTextArea InputTextArea = new JTextArea(5, 30), translatedTextArea = new JTextArea(5, 30), asciiTextArea = new JTextArea(5, 30);
    private JButton copyTranslationButton = new JButton("Select and Copy"), copyAsciiButton = new JButton("Select and Copy"), changeThemeButton = new JButton();
    private JLabel englishTextLabel = new JLabel("Enter Text"), rusTextLabel = new JLabel("Translate"), asciiCodeLabel = new JLabel("ASCII Code"),imageTest = new JLabel();
    ImageIcon themeMoon = new ImageIcon("moon.png");

    public AsciiGUI() {
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
        add(imageTest);
    }

    public void configureButtons() {
        configureButtons(copyTranslationButton, 200, 225, 150, 30);
        configureButtons(copyAsciiButton, 400, 225, 150, 30);
        configureButtons(changeThemeButton, 10, 10, 150, 30);
        // changeTheme.setOpaque(false);
        // changeTheme.setContentAreaFilled(false);
        changeThemeButton.setIcon(themeMoon);
    }

    public void configureLabels() {
        englishTextLabel.setBounds(10, 50, 150, 30);
        rusTextLabel.setBounds(200, 50, 150, 30);
        asciiCodeLabel.setBounds(400, 50, 150, 30);
        imageTest.setBounds(200, 10, 150, 30);
        imageTest.setIcon(themeMoon);
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
        configureTheme(new Color(0x181818), new Color(0xc3c3c3), new Color(0x1f1f1f));
    }

    public void setLightTheme() {
        configureTheme(new Color(0xd3d3d3), Color.BLACK, Color.WHITE);
    }
    public void configureTheme(Color bg, Color fg, Color textAreaBg) {
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
    }
}
