package main;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class LuckyBlock extends Tile{
    
    double frameSpeed;
    long nextTime;
    int frameNum;
    boolean off;
    
    BufferedImage LUCKY_OFF;
    BufferedImage LUCKY_1;
    BufferedImage LUCKY_2;
    BufferedImage LUCKY_3;
    BufferedImage LUCKY_4;
    
    
    
    public LuckyBlock(int x, int y, GamePanel panel) throws IOException {
        super(x, y, 32, 1, null, panel, true, false);
        tile =  ImageIO.read(getClass().getResourceAsStream("/resources/tiles/lucky_block1.png"));
        frameSpeed = 0.10;
        hitBox = new Rectangle(worldX, worldY, 32,32);
        nextTime = System.nanoTime() + (long) (1000000000 * frameSpeed);
        frameNum=1;
        off=false;
        LUCKY_OFF =  ImageIO.read(getClass().getResourceAsStream("/resources/tiles/lucky_block_off.png"));
        LUCKY_1 =  ImageIO.read(getClass().getResourceAsStream("/resources/tiles/lucky_block1.png"));
        LUCKY_2 =  ImageIO.read(getClass().getResourceAsStream("/resources/tiles/lucky_block2.png"));
        LUCKY_3 =  ImageIO.read(getClass().getResourceAsStream("/resources/tiles/lucky_block3.png"));
        LUCKY_4 =  ImageIO.read(getClass().getResourceAsStream("/resources/tiles/lucky_block4.png"));
    }

    @Override
    public void draw(Graphics2D g2) {
        screenX = panel.CalculateScreenXPosition(worldX);
        screenY = panel.CalculateScreenYPosition(worldY);
        
        if (!off) {

                if (System.nanoTime() >= nextTime) {
                    if (frameNum > 4) {
                        frameNum = 1;
                    }
                    switch (frameNum) {
                        case 1:
                            tile = LUCKY_1;
                            break;
                        case 2:
                            tile = LUCKY_2;
                            break;
                        case 3:
                            tile = LUCKY_3;
                            break;
                        case 4:
                            tile = LUCKY_4;
                            break;
                    }
                    frameNum++;
                    nextTime = System.nanoTime() + (long) (1000000000 * frameSpeed);
                }

            } else{
            tile = LUCKY_OFF;
 
        }
            
        g2.drawImage(tile, screenX,screenY, size*scale, size*scale, null);
    }
    
    
    
    
    
}
