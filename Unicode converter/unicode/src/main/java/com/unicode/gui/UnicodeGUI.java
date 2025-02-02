package com.unicode.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import com.unicode.core.UnicodeMain;
import com.unicode.gui.event.EventHandling;
import com.unicode.theme.ThemeManager;

public class UnicodeGUI extends JFrame {

    private JTextArea inputTextArea, translatedTextArea, unicodeTextArea;
    private JButton copyTranslationButton, copyUnicodeButton, changeThemeButton;
    private JLabel englishTextLabel, rusTextLabel, unicodeCodeLabel;
    private ImageIcon themeMoonIcon = new ImageIcon(getClass().getResource("/moon.png"));
    private ImageIcon themeSunIcon = new ImageIcon(getClass().getResource("/sun.png"));
    private ImageIcon scaledMoonImage = new ImageIcon(themeMoonIcon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));
    private ImageIcon scaledSunImage = new ImageIcon(themeSunIcon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));
    private EventHandling event = new EventHandling();
    private ThemeManager themeManager = new ThemeManager();
    private List<Component> components = new ArrayList<>();

    public UnicodeGUI() {
        initialize();
        config();
        addDocListeners();
        addKeyListenerToWindow();
        // TODO: Consider dynamically loading theme preference instead of hardcoding.
        checkForTheme();
    }

    private void config() {
        configureButtons();
        configureTextArea();
        configureFrame();
        configureLabels();
        configureImage();
    }

    public void initialize() {
        initializeComponents();
        initializeComponentsList();
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
        add(inputTextArea);
        add(translatedTextArea);
        add(unicodeTextArea);
        add(copyTranslationButton);
        add(copyUnicodeButton);
        add(changeThemeButton);
    }

    public void configureButtons() {
        configureComponents(copyTranslationButton, 200, 225, 150, 30);
        configureComponents(copyUnicodeButton, 400, 225, 150, 30);
        configureComponents(changeThemeButton, 10, 10, 25, 25);
        changeThemeButton.setIcon(scaledMoonImage);
        changeThemeButton.setOpaque(true);
        changeThemeButton.setContentAreaFilled(false);
        event.addCopyButtonListener(copyTranslationButton, translatedTextArea);
        event.addCopyButtonListener(copyUnicodeButton, unicodeTextArea);
        event.addThemeButtonListener(changeThemeButton, this);
    }

    public void configureLabels() {
        englishTextLabel.setBounds(10, 50, 150, 30);
        rusTextLabel.setBounds(200, 50, 150, 30);
        unicodeCodeLabel.setBounds(400, 50, 150, 30);
    }

    public void configureImage() {
        // TODO: Add logic for configuring images if required in the future.
    }

    public void configureTextArea() {
        configureComponents(inputTextArea, 10, 100, 150, 100);
        configureComponents(translatedTextArea, 200, 100, 150, 100);
        configureComponents(unicodeTextArea, 400, 100, 150, 100);
        unicodeTextArea.setEditable(false);
    }

    public void configureComponents(JComponent comp, int x, int y, int width, int height) {
        comp.setBounds(x, y, width, height);

        if (comp instanceof JButton) {
            ((JButton)comp).setBorderPainted(false);
            comp.setBackground(Color.WHITE);
        } else if (comp instanceof JTextArea) {
            comp.setFont(new Font("Arial", Font.PLAIN, 12));
            ((JTextArea)comp).setLineWrap(true);
        } else {
            // TODO: Expand support for additional component types if needed.
            throw new IllegalArgumentException("Unsupported component type: " + comp.getClass().getName());
        }
    }

    private void initializeComponents() {
        inputTextArea = new JTextArea(5, 30);
        translatedTextArea = new JTextArea(5, 30);
        unicodeTextArea = new JTextArea(5, 30);
        copyTranslationButton = new JButton("Select and Copy");
        copyUnicodeButton = new JButton("Select and Copy");
        changeThemeButton = new JButton();
        englishTextLabel = new JLabel("Enter Text");
        rusTextLabel  = new JLabel("Translate");
        unicodeCodeLabel  = new JLabel("Unicode");
        changeThemeButton = new JButton();
    }

    private void initializeComponentsList() {
        // TODO: Ensure the components map is used consistently throughout the class.
        components.add(this);
        components.add(inputTextArea);
        components.add(translatedTextArea);
        components.add(unicodeTextArea);
        components.add(changeThemeButton);
        components.add(copyTranslationButton);
        components.add(copyUnicodeButton);
        components.add(englishTextLabel);
        components.add(rusTextLabel);
        components.add( unicodeCodeLabel);
    }

    public void initializeLabels(JLabel lbl, String text) {
        lbl.setText(text);
    }

    private void addDocListeners() {
        event.addDocListener(inputTextArea, translatedTextArea, true);
        event.addDocListener(translatedTextArea, unicodeTextArea, false);
    }

    private void addKeyListenerToWindow() {
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_F10) {
                    // TODO: Consider adding a confirmation dialog before restarting.
                    UnicodeMain.main(null);
                }
            }
        });
        this.setFocusable(true);
    }

    // TODO: Put theme management in a separate class.
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
        themeManager.configureTheme(components, ThemeManager.NERO, ThemeManager.SILVER, ThemeManager.DARK_NERO, scaledSunImage);
    }

    public void setLightTheme() {
        themeManager.configureTheme(components, ThemeManager.LIGHT_GRAY, Color.BLACK, Color.WHITE, scaledMoonImage);
    }

    private void checkForTheme() {
        isDarkTheme = ThemeManager.loadTheme();
        if (isDarkTheme) {
            setDarkTheme();
        } else {
            setLightTheme();
        }
    }
}
