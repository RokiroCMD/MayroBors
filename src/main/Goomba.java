package main;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Goomba {

    int screenX;
    int screenY;

    int worldX;
    int worldY;

    int size;
    int scale;
    boolean derecha;

    long nextTime;

    boolean death;

    BufferedImage frame;
    int frameNum;
    int frameMuerto;

    Rectangle hitbox;
    Rectangle deathbox;

    double xspeed;
    double yspeed;
    double speed;

    double frameSpeed;

    GamePanel panel;
    boolean remove;
    
    BufferedImage goombaCaminar1;
    BufferedImage goombaCaminar2;
    BufferedImage goombaCaminar3;
    BufferedImage goombaCaminar4;
    BufferedImage goombaCaminar5;
    BufferedImage goombaCaminar6;
    BufferedImage goombaCaminar7;
    BufferedImage goombaCaminar8;
    BufferedImage goombaCaminar9;
    BufferedImage goombaCaminar10;
    BufferedImage goombaCaminar11;
    BufferedImage goombaMuerto;

    public Goomba(int worldX, int worldY, GamePanel panel) throws IOException {
        this.worldX = worldX;
        this.worldY = worldY;
        this.panel = panel;
        derecha = true;
        size = 48;
        remove = false;
        frameSpeed = 0.1;
        frameMuerto = 0;
        nextTime = System.nanoTime() + (long) (1000000000 * frameSpeed);
        speed = 1;
        xspeed = 0;
        yspeed = 0;
        scale = 1;
        hitbox = new Rectangle(this.worldX, this.worldY, 48, 48);
        deathbox = new Rectangle(this.worldX + 12, this.worldY + 24, 24, 24);
        frameNum = 1;
        frame = ImageIO.read(getClass().getResourceAsStream("/resources/enemies/goomba_caminar1.png"));
        goombaCaminar1 =  ImageIO.read(getClass().getResourceAsStream("/resources/enemies/goomba_caminar1.png"));
        goombaCaminar2 =  ImageIO.read(getClass().getResourceAsStream("/resources/enemies/goomba_caminar2.png"));
        goombaCaminar3 =  ImageIO.read(getClass().getResourceAsStream("/resources/enemies/goomba_caminar3.png"));
        goombaCaminar4 =  ImageIO.read(getClass().getResourceAsStream("/resources/enemies/goomba_caminar4.png"));
        goombaCaminar5 =  ImageIO.read(getClass().getResourceAsStream("/resources/enemies/goomba_caminar5.png"));
        goombaCaminar6 =  ImageIO.read(getClass().getResourceAsStream("/resources/enemies/goomba_caminar6.png"));
        goombaCaminar7 =  ImageIO.read(getClass().getResourceAsStream("/resources/enemies/goomba_caminar7.png"));
        goombaCaminar8 =  ImageIO.read(getClass().getResourceAsStream("/resources/enemies/goomba_caminar8.png"));
        goombaCaminar9 =  ImageIO.read(getClass().getResourceAsStream("/resources/enemies/goomba_caminar9.png"));
        goombaCaminar10 =  ImageIO.read(getClass().getResourceAsStream("/resources/enemies/goomba_caminar10.png"));
        goombaCaminar11 =  ImageIO.read(getClass().getResourceAsStream("/resources/enemies/goomba_caminar11.png"));
        goombaMuerto =  ImageIO.read(getClass().getResourceAsStream("/resources/enemies/goomba_muerto.png"));

    }

    public void update() throws IOException {
        if (death && !remove) {
            if (System.nanoTime() >= nextTime) {
                if (frameMuerto > 0) {
                    remove = true;
                } else {
                    frame = goombaMuerto;
                    nextTime = System.nanoTime() + (long) (1000000000 * 1);
                    frameMuerto++;
                }

            }
            return;
        }
        deathbox.x = worldX + 12;
        deathbox.y = worldY + 24;
        if (derecha) {

            xspeed += speed;
        }
        if (!derecha) {

            xspeed -= speed;
        }
        if (xspeed > 0 && xspeed < 0.75) {
            xspeed = 0;
        }
        if (xspeed < 0 && xspeed > -0.75) {
            xspeed = 0;
        }

        if (xspeed > speed) {
            xspeed = speed;
        }
        if (xspeed < -speed) {
            xspeed = -speed;
        }
        if (worldX <= 0) {
            xspeed = 0;
            worldX++;
            derecha = !derecha;
        }
        if (worldX >= panel.maxWorldX) {
            xspeed = 0;
            worldX--;
        }

        yspeed += 0.45;

        hitbox.x += xspeed;
        for (Tile tile : panel.tiles) {
            if (tile.canCollide) {
                if (tile.hitBox.intersects(hitbox)) {
                    hitbox.x -= xspeed;
                    while (!tile.hitBox.intersects(hitbox)) {
                        hitbox.x += Math.signum(xspeed);
                    }
                    hitbox.x -= Math.signum(xspeed);
                    worldX = hitbox.x;
                    xspeed = 0;
                    derecha = !derecha;
                }
            }
        }

        hitbox.y += yspeed;
        boolean EnAire = false;
        for (Tile tile : panel.tiles) {
            if (tile.canCollide) {
                if (tile.hitBox.intersects(hitbox)) {
                    hitbox.y -= yspeed;
                    while (!tile.hitBox.intersects(hitbox)) {
                        hitbox.y += Math.signum(yspeed);
                    }
                    hitbox.y -= Math.signum(yspeed);
                    yspeed = 0;
                    worldY = hitbox.y;
                }
            }
        }

        worldX += xspeed;
        worldY += yspeed;

        hitbox.x = worldX;
        hitbox.y = worldY;
    }

    public void draw(Graphics2D g2) throws IOException {
        if (!remove) {

            screenX = panel.CalculateScreenXPosition(worldX);
            screenY = panel.CalculateScreenYPosition(worldY);

            if (!death) {

                if (System.nanoTime() >= nextTime) {
                    if (frameNum > 11) {
                        frameNum = 1;
                    }
                    switch (frameNum) {
                        case 1:
                            frame = goombaCaminar1;
                            break;
                        case 2:
                            frame = goombaCaminar2;
                            break;
                        case 3:
                            frame = goombaCaminar3;
                            break;
                        case 4:
                            frame = goombaCaminar4;
                            break;
                        case 5:
                            frame = goombaCaminar5;
                            break;
                        case 6:
                            frame = goombaCaminar6;
                            break;
                        case 7:
                            frame = goombaCaminar7;
                            break;
                        case 8:
                            frame = goombaCaminar8;
                            break;
                        case 9:
                            frame = goombaCaminar9;
                            break;
                        case 10:
                            frame = goombaCaminar10;
                            break;
                        case 11:
                            frame = goombaCaminar11;
                            break;
                    }
                    frameNum++;
                    nextTime = System.nanoTime() + (long) (1000000000 * frameSpeed);
                }

            }
            if (derecha) {
                g2.drawImage(frame, screenX, screenY, size * scale, size * scale, null);
            } else {
                g2.drawImage(frame, screenX - (-size * scale), screenY, -size * scale, size * scale, null);

            }
        }
    }

}
