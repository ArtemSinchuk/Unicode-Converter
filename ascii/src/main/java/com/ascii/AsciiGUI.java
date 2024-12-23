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
    private JLabel englishText = new JLabel("Enter Text:");
    private JLabel rusText = new JLabel("Translate:");
    private JLabel asciiCode = new JLabel("ASCII Code:");

    public AsciiGUI() {
        config();
        addCopyButtonListeners();
        addDocListeners();
        addKeyListenerToWindow();
    }

    public void configureFrame() {
        setTitle("ASCII Converter");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(575, 275);
        getContentPane().setBackground(new Color(0xd3d3d3));
        setLocationRelativeTo(null);
        setLayout(null);
        addComponents();
        setVisible(true);
    }

    private void addComponents() {
        englishText.setBounds(10, 10, 150, 30);
        rusText.setBounds(200, 10, 150, 30);
        asciiCode.setBounds(400, 10, 150, 30);

        textField0.setBounds(10, 50, 150, 100);
        textField1.setBounds(200, 50, 150, 100);
        asciiField.setBounds(400, 50, 150, 100);

        copyButton.setBounds(200, 200, 150, 30);
        copyButton2.setBounds(400, 200, 150, 30);

        add(englishText);
        add(rusText);
        add(asciiCode);
        add(textField0);
        add(textField1);
        add(asciiField);
        add(copyButton);
        add(copyButton2);
    }

    public void configureButtons() {
        copyButton.setText("Select and Copy");
        copyButton.setBackground(Color.WHITE);

        copyButton2.setText("Select and Copy");
        copyButton2.setBackground(Color.WHITE);
        }

    public void configureTextArea() {
        textField0.setFont(new Font("Arial", Font.PLAIN, 12));
        textField0.setLineWrap(true);

        textField1.setFont(new Font("Arial", Font.PLAIN, 12));
        textField1.setLineWrap(true);

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
    }

    private void config() {
        configureButtons();
        configureTextArea();
        configureFrame();
    }

    private boolean updating = false;
private void addDocListeners() {
    // Listener for the text field "textField0"
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
            if (updating) return;  // If the update is already in progress, exit
            updating = true;

            String input = textField0.getText();
            textField1.setText(Translate.translateText(input));
            asciiField.setText(AsciiConverter.toAscii(input));

            updating = false;
        }
    });

    // Listener for the text field "textField1"
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
            if (updating) return;
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
}
