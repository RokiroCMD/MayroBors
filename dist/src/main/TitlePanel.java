package main;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class TitlePanel extends JPanel {

    Timer gameTimer;

    boolean introMusic;

    MainFrame main;
    JButton startButton;
    JButton exitButton;
    TitlePanel panel;

    public TitlePanel(MainFrame main) throws IOException {

        this.main = main;
        panel = this;
        setLayout(null);
        startButton = new JButton();
        exitButton = new JButton();
        Font font = new Font("resource1", Font.PLAIN, 28);
        startButton.setText("START");
        startButton.setFont(font);
        startButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        startButton.setContentAreaFilled(false);
        startButton.setBorder(null);
        startButton.setBorderPainted(false);
        startButton.setBounds(80, 500, 200, 75);
        startButton.addActionListener(startAction);

        exitButton.setText("EXIT");
        exitButton.setFont(font);
        exitButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        exitButton.setContentAreaFilled(false);
        exitButton.setBorder(null);
        exitButton.setBorderPainted(false);
        exitButton.setBounds(340, 500, 200, 50);
        exitButton.addActionListener(exitAction);

        add(startButton);
        add(exitButton);

        introMusic = false;
        gameTimer = new Timer();
        gameTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!introMusic) {
                    try {
                        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("src/resources/sounds/intro.wav").getAbsoluteFile());
                        Clip clip = AudioSystem.getClip();
                        clip.open(audioInputStream);
                        clip.start();
                    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                        System.out.println("Error al reproducir el sonido.");
                    }
                    introMusic = true;
                }
                repaint();
            }

        }, 0, 34);
    }

    @Override
    public void paint(Graphics g) {
        try {
            super.paint(g);
            BufferedImage imagen = ImageIO.read(getClass().getResourceAsStream("/resources/hud/botones.png"));
            BufferedImage logo = ImageIO.read(getClass().getResourceAsStream("/resources/hud/logo.png"));
            g.drawImage(imagen, 80, 500, 200, 75, null);
            Font font = new Font("resource1", Font.PLAIN, 28);
            g.setColor(Color.white);
            g.setFont(font);
            g.drawString("START", 130, 550);
            g.drawImage(imagen, 340, 500, 200, 75, null);
            g.setColor(Color.white);
            g.setFont(font);
            g.drawString("EXIT", 410, 550);
            g.drawImage(logo, 150, 200, 320, 150, null);
            g.drawString("NO ME DEMANDES NINTENDO", 100, 400);
            g.drawString("PORFAVOR :(", 250, 430);
        } catch (IOException ex) {

        }

    }

    ActionListener startAction = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                main.pantallaJuego();
            } catch (IOException ex) {
                Logger.getLogger(TitlePanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };

    ActionListener exitAction = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    };

}
