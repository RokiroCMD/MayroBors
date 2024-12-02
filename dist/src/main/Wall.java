
package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Wall {

    int x;
    int y;
    int widht;
    int height;
    
    int startX;
    
    Rectangle hitBox;

    public Wall(int x, int y, int widht, int height) {
        this.x = x;
        startX = x;
        this.y = y;
        this.widht = widht;
        this.height = height;
        hitBox = new Rectangle(x,y,widht,height);
    }
    
    public void draw(Graphics2D g2d){
        g2d.setColor(Color.BLACK);
        g2d.drawRect(x, y, widht, height);
        g2d.setColor(Color.WHITE);
        g2d.fillRect(x+1,y+1,widht-2,height-2);
        
        
    }

    public int set(int cameraX) {
        x = startX + cameraX;
        hitBox.x = x;
        return x;
    }
    
    
    
}
