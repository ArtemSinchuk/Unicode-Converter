package com.ascii;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;

public class AsciiGUI extends JFrame {
    private JTextArea textField0 = new JTextArea(5, 30);
    private JTextArea textField1 = new JTextArea(5, 30);
    private JTextArea asciiField = new JTextArea(5, 30);
    private JButton copyButton = new JButton("Select and Copy");
    private JButton copyButton2 = new JButton("Select and Copy");
    private JButton changeTheme = new JButton();
    private JLabel englishText = new JLabel("Enter Text");
    private JLabel rusText = new JLabel("Translate");
    private JLabel asciiCode = new JLabel("ASCII Code");

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
        setVisible(true);
        addComponents();
    }

    private void addComponents() {
        add(englishText);
        add(rusText);
        add(asciiCode);
        add(textField0);
        add(textField1);
        add(asciiField);
        add(copyButton);
        add(copyButton2);
        add(changeTheme);
        add(imageTest);
    }

    public void configureButtons() {
        copyButton.setBounds(200, 225, 150, 30);
        copyButton.setText("Select and Copy");
        copyButton.setBorderPainted(false);
        copyButton.setBackground(Color.WHITE);
        
        copyButton2.setBounds(400, 225, 150, 30);
        copyButton2.setText("Select and Copy");
        copyButton2.setBorderPainted(false);
        copyButton2.setBackground(Color.WHITE);
        
        changeTheme.setBounds(10, 10, 150, 30);
        // changeTheme.setOpaque(false);
        // changeTheme.setContentAreaFilled(false);
        // changeTheme.setBorderPainted(false);
    }

    public void configureLabels() {
        englishText.setBounds(10, 50, 150, 30);
        rusText.setBounds(200, 50, 150, 30);
        asciiCode.setBounds(400, 50, 150, 30);
        imageTest.setBounds(200, 10, 150, 30);
    }

    public void configureTextArea() {
        textField0.setBounds(10, 100, 150, 100);
        textField0.setFont(new Font("Arial", Font.PLAIN, 12));
        textField0.setLineWrap(true);
        
        textField1.setBounds(200, 100, 150, 100);
        textField1.setFont(new Font("Arial", Font.PLAIN, 12));
        textField1.setLineWrap(true);
        
        asciiField.setBounds(400, 100, 150, 100);
        asciiField.setFont(new Font("Arial", Font.PLAIN, 12));
        asciiField.setLineWrap(true);
        asciiField.setEditable(false);
    }

    public void addCopyButtonListeners() {
        copyButton.addActionListener(e -> {
            String text = textField1.getText();
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(text), null);
        });

        copyButton2.addActionListener(e -> {
            String text = asciiField.getText();
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(text), null);
        });

        changeTheme.addActionListener(e -> {
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
        textField0.getDocument().addDocumentListener(new DocumentListener() {
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

                String input = textField0.getText();
                textField1.setText(Translate.translateText(input));
                asciiField.setText(AsciiConverter.toAscii(input));

                updating = false;
            }
        });

        textField1.getDocument().addDocumentListener(new DocumentListener() {
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

                String input = textField1.getText();
                asciiField.setText(AsciiConverter.toAscii(input));

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

    int changes = 1;
    public void changeTheme() {
        if (changes % 2 == 1) {
            setDarkTheme();
        } else {
            setLightTheme();
        }
        changes++;
    }

    public void setDarkTheme() {
        getContentPane().setBackground(new Color(0x181818));
        copyButton.setBackground(new Color(0x313232));
        copyButton.setForeground(new Color(0xc3c3c3));

        copyButton2.setBackground(new Color(0x313232));
        copyButton2.setForeground(new Color(0xc3c3c3));

        textField0.setBackground(new Color(0x1f1f1f));
        textField1.setBackground(new Color(0x1f1f1f));
        asciiField.setBackground(new Color(0x1f1f1f));
        textField0.setForeground(new Color(0xc3c3c3));
        textField1.setForeground(new Color(0xc3c3c3));
        asciiField.setForeground(new Color(0xc3c3c3));

        englishText.setForeground(new Color(0xc3c3c3));
        rusText.setForeground(new Color(0xc3c3c3));
        asciiCode.setForeground(new Color(0xc3c3c3));
    }

    public void setLightTheme() {
        getContentPane().setBackground(new Color(0xd3d3d3));

        copyButton.setBackground(Color.WHITE);
        copyButton.setForeground(Color.BLACK);

        copyButton2.setBackground(Color.WHITE);
        copyButton2.setForeground(Color.BLACK);

        textField0.setBackground(Color.WHITE);
        textField1.setBackground(Color.WHITE);
        asciiField.setBackground(Color.WHITE);

        englishText.setForeground(Color.BLACK);
        rusText.setForeground(Color.BLACK);
        asciiCode.setForeground(Color.BLACK);
    }
}
