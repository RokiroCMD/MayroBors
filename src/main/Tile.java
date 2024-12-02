
package main;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;


public class Tile {

int screenX;
int screenY;
    
int worldX;
int worldY;

boolean canCollide;
boolean canPass;

int size;
int scale;
BufferedImage tile;
Rectangle hitBox;

GamePanel panel;

    public Tile(int x, int y, int size, int scale, BufferedImage tile, GamePanel panel, boolean canCollide, boolean canPass) {
        worldX = x;
        worldY = y;
        this.panel = panel;
        this.size = size;
        this.scale = scale;
        this.tile = tile;
        this.hitBox = new Rectangle(worldX,worldY, size*scale, size*scale);
        this.canCollide = canCollide;
        this.canPass = true;
    }
    
    public void draw(Graphics2D g2){
        
        screenX = panel.CalculateScreenXPosition(worldX);
        screenY = panel.CalculateScreenYPosition(worldY);
        
        g2.drawImage(tile, screenX,screenY, size*scale, size*scale, null);
    }    
}
