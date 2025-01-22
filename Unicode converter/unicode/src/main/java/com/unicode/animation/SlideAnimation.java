package com.unicode.animation;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * ONLY TEST
 */
public class SlideAnimation {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SlideAnimation().createAndShowGUI());
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Slide Icon");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new FlowLayout());

        JButton button = new JButton(new ImageIcon("C:\\Users\\GameTwo\\Desktop\\Projects\\programs\\Unicode coder\\unicode\\src\\main\\resources\\moon.png"));
        button.addActionListener(e -> slideIcon(button, true));

        frame.add(button);
        frame.setVisible(true);
    }

    private void slideIcon(JButton button, boolean toSun) {
        Timer timer = new Timer(15, null); // Таймер
        final int[] x = {0}; // Позиция
        ImageIcon moonIcon = new ImageIcon("C:\\Users\\GameTwo\\Desktop\\Projects\\programs\\Unicode coder\\unicode\\src\\main\\resources\\moon.png");
        ImageIcon sunIcon = new ImageIcon("C:\\Users\\GameTwo\\Desktop\\Projects\\programs\\Unicode coder\\unicode\\src\\main\\resources\\sun.png");

        timer.addActionListener(e -> {
            x[0] += 5; // Сдвиг вправо
            button.setIcon(createSlidingIcon(toSun ? sunIcon : moonIcon, x[0]));
            if (x[0] >= button.getWidth()) {
                timer.stop();
                button.setIcon(toSun ? sunIcon : moonIcon);
            }
        });

        timer.start();
    }

    private ImageIcon createSlidingIcon(ImageIcon icon, int x) {
        BufferedImage img = toBufferedImage(icon.getImage());
        BufferedImage newImg = new BufferedImage(img.getWidth() + x, img.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = newImg.createGraphics();
        g2d.drawImage(img, x, 0, null); // Рисуем иконку со сдвигом
        g2d.dispose();
        return new ImageIcon(newImg);
    }

    private BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage) return (BufferedImage) img;
        BufferedImage bImage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D bGr = bImage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();
        return bImage;
    }
}
