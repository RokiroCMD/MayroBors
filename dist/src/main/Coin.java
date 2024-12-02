package main;


import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Coin {

    int screenX;
    int screenY;

    int worldX;
    int worldY;

    int points;
    
    BufferedImage frame;
    int frameNum;
    long nextTime;

    double frameSpeed;

    Rectangle hitBox;
    GamePanel panel;
    
    BufferedImage coin1;
    BufferedImage coin2;
    BufferedImage coin3;
    BufferedImage coin4;
    
    public Coin(int x, int y, GamePanel panel) throws IOException {
        this.panel = panel;
        worldX = x;
        worldY = y;
        frame =ImageIO.read(getClass().getResourceAsStream("/resources/tiles/coin1.png"));
        frameSpeed = 0.1;
        frameNum=1;
        points = 1;
        hitBox = new Rectangle(x, y, 32, 32);
        coin1 = ImageIO.read(getClass().getResourceAsStream("/resources/tiles/coin1.png"));
        coin2 = ImageIO.read(getClass().getResourceAsStream("/resources/tiles/coin2.png"));
        coin3 = ImageIO.read(getClass().getResourceAsStream("/resources/tiles/coin3.png"));
        coin4 = ImageIO.read(getClass().getResourceAsStream("/resources/tiles/coin4.png"));
        nextTime = System.nanoTime() + (long) (1000000000 * frameSpeed);
    }

    public void draw(Graphics2D g2d) throws IOException {

        if (System.nanoTime() >= nextTime) {
            if (frameNum > 4) {
                frameNum = 1;
            }
            
            switch (frameNum) {
                case 1:
                    frame = coin1;
                    break;
                case 2:
                    frame = coin2;
                    break;
                case 3:
                    frame = coin3;
                    break;
                case 4:
                    frame = coin4;                    
                    break;
            }
            
            frameNum++;
            nextTime = System.nanoTime() + (long) (1000000000 * frameSpeed);
        }

        screenX = panel.CalculateScreenXPosition(worldX);
        screenY = panel.CalculateScreenYPosition(worldY);

        g2d.drawImage(frame, screenX, screenY, 32, 32, null);
    }
}
