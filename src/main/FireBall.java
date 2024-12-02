
package main;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class FireBall {
    
    int worldX;
    int worldY;
    int screenX;
    int screenY;
    
    BufferedImage frame;
    int xSpeed;
    int ySpeed;
    int direction;

    public FireBall(int worldX, int worldY, int direction) {
        this.worldX = worldX;
        this.worldY = worldY;
        this.direction = direction;
        xSpeed = direction * 2;
    }
    
    public void update(){
        
    }
    /*
    public void draw(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        screenX = panel.CalculateScreenXPosition(worldX);
        screenY = panel.CalculateScreenYPosition(worldY);
        
        g2.drawImage(frame, screenX,screenY, 32, 32 , null);
    }
*/
    
}
