package com.unicode;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

/**
 * ONLY TEST
 */
public class RotateAnimation {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RotateAnimation().createAndShowGUI());
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Rotate Icon");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new FlowLayout());

        JButton button = new JButton(new ImageIcon("C:\\Users\\GameTwo\\Desktop\\Projects\\programs\\Unicode coder\\unicode\\src\\main\\resources\\moon.png"));
        button.addActionListener(e -> rotateIcon(button, true));

        frame.add(button);
        frame.setVisible(true);
    }

    private void rotateIcon(JButton button, boolean toSun) {
        Timer timer = new Timer(15, null); // Таймер
        final int[] angle = {0}; // Угол поворота
        ImageIcon moonIcon = new ImageIcon("C:\\Users\\GameTwo\\Desktop\\Projects\\programs\\Unicode coder\\unicode\\src\\main\\resources\\moon.png");
        ImageIcon sunIcon = new ImageIcon("C:\\Users\\GameTwo\\Desktop\\Projects\\programs\\Unicode coder\\unicode\\src\\main\\resources\\sun.png");

        timer.addActionListener(e -> {
            angle[0] += 10; // Увеличиваем угол
            if (angle[0] >= 180) {
                timer.stop();
                button.setIcon(toSun ? sunIcon : moonIcon); // Меняем иконку
            } else {
                button.setIcon(rotateImageIcon(toSun ? moonIcon : sunIcon, angle[0]));
            }
        });

        timer.start();
    }

    private ImageIcon rotateImageIcon(ImageIcon icon, int degrees) {
        BufferedImage img = toBufferedImage(icon.getImage());
        double radians = Math.toRadians(degrees);
        AffineTransform tx = AffineTransform.getRotateInstance(radians, img.getWidth() / 2.0, img.getHeight() / 2.0);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        return new ImageIcon(op.filter(img, null));
    }

    private BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage) return (BufferedImage) img;
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();
        return bimage;
    }
}
