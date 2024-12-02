
package main;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Flag extends Tile{

    boolean win;
    
    public Flag(int x, int y, GamePanel panel) throws IOException {
        super(x, y, 48, 1, null, panel, false, true);
        
       tile = ImageIO.read(getClass().getResourceAsStream("/resources/tiles/flag.png"));
        win = false;
        hitBox = new Rectangle(worldX,worldY-(48*2), 48,48*3);
    }

    @Override
    public void draw(Graphics2D g2) {   
        screenX = panel.CalculateScreenXPosition(worldX);
        screenY = panel.CalculateScreenYPosition(worldY-(48*2));
        if (win == true) {
            panel.win = true;
        }
    g2.drawImage(tile, screenX,screenY, 48, 48*3,null);
    }
    
    
    
}
